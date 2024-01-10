package vn.xdeuhug.luckyMoney.http.model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonSyntaxException;
import com.hjq.gson.factory.GsonFactory;
import com.hjq.http.EasyLog;
import com.hjq.http.config.IRequestHandler;
import com.hjq.http.exception.CancelException;
import com.hjq.http.exception.DataException;
import com.hjq.http.exception.FileMd5Exception;
import com.hjq.http.exception.HttpException;
import com.hjq.http.exception.NetworkException;
import com.hjq.http.exception.NullBodyException;
import com.hjq.http.exception.ResponseException;
import com.hjq.http.exception.ServerException;
import com.hjq.http.exception.TimeoutException;
import com.hjq.http.request.HttpRequest;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;
import vn.xdeuhug.luckyMoney.model.eventbus.EventBusTokenExpired;
import vn.xdeuhug.luckyMoney.R;
import vn.xdeuhug.luckyMoney.http.exception.TokenException;

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
public final class RequestHandler implements IRequestHandler {

    private final Application mApplication;

    public RequestHandler(Application application) {
        mApplication = application;
    }

    @SuppressLint("StringFormatInvalid")
    @NonNull
    @Override
    public Object requestSuccess(@NonNull HttpRequest<?> httpRequest, @NonNull Response response, @NonNull Type type) throws Exception {
        if (Response.class.equals(type)) {
            return response;
        }

        if (!response.isSuccessful()) {
            if (response.code() == 401 || response.code() == 410) {
                //Phiên đăng nhập hết hạn
                EventBus.getDefault().post(new EventBusTokenExpired(""));
            } else
                ToastUtils.show(String.format(mApplication.getString(R.string.http_response_error)) + " " + response.code());

            throw new ResponseException(String.format(mApplication.getString(R.string.http_response_error) + " %s", response.code(), response.message()), response);
        } else {
            Timber.d(response.toString());
        }

        if (Headers.class.equals(type)) {
            return response.headers();
        }

        ResponseBody body = response.body();
        if (body == null) {
            throw new NullBodyException(mApplication.getString(R.string.http_response_null_body));
        }

        if (ResponseBody.class.equals(type)) {
            return body;
        }

        // Nếu nó được nhận với một mảng, hãy đánh giá xem nó có được nhận với kiểu byte [] hay không
        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (byte.class.equals(genericComponentType)) {
                return body.bytes();
            }
        }

        if (InputStream.class.equals(type)) {
            return body.byteStream();
        }

        if (Bitmap.class.equals(type)) {
            return BitmapFactory.decodeStream(body.byteStream());
        }

        String text;
        try {
            text = body.string();
        } catch (IOException e) {
            // trả về kết quả đọc ngoại lệ
            throw new DataException(mApplication.getString(R.string.http_data_explain_error), e);
        }

        // In Json này hoặc văn bản
        EasyLog.printJson(httpRequest, text);

        if (String.class.equals(type)) {
            return text;
        }

        final Object result;

        try {
            result = GsonFactory.getSingletonGson().fromJson(text, type);
        } catch (JsonSyntaxException e) {
            // Trả về kết quả đọc ngoại lệ
            throw new DataException(mApplication.getString(R.string.http_data_explain_error), e);
        }

        if (result instanceof HttpData) {
            HttpData<?> model = (HttpData<?>) result;

            if (model.isRequestSucceed()) {
                // Thay mặt thực hiện thành công
                return result;
            } else if (model.isTokenFailure() || model.isRequestUnauthorized()) {
                // Cho biết thông tin đăng nhập không hợp lệ và cần đăng nhập lại
                return result;
            } else {
                //throw new ResultException(model.getMessage(), model);
                return result;
            }
        }
        return result;
    }

    @NonNull
    @Override
    public Exception requestFail(@NonNull HttpRequest<?> httpRequest, @NonNull Exception e) {
        if (e instanceof HttpException) {
            if (e instanceof TokenException) {
                // Thông tin đăng nhập không hợp lệ, hãy chuyển đến trang đăng nhập
                EventBus.getDefault().post(new EventBusTokenExpired(""));
            }
            return e;
        }

        if (e instanceof SocketTimeoutException) {
            return new TimeoutException(mApplication.getString(R.string.http_server_out_time), e);
        }

        if (e instanceof UnknownHostException) {
            NetworkInfo info = ((ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            // Xác định xem mạng đã được kết nối chưa
            if (info != null && info.isConnected()) {
                // Kết nối là vấn đề với máy chủ
                return new ServerException(mApplication.getString(R.string.http_server_error), e);
            }
            // Không có kết nối là lỗi mạng
            return new NetworkException(mApplication.getString(R.string.http_network_error), e);
        }

        if (e instanceof IOException) {
            return new CancelException(mApplication.getString(R.string.http_request_cancel), e);
        }

        return new HttpException(e.getMessage(), e);
    }

    @SuppressLint("StringFormatInvalid")
    @NonNull
    @Override
    public Exception downloadFail(@NonNull HttpRequest<?> httpRequest, @NonNull Exception e) {
        if (e instanceof ResponseException) {
            ResponseException responseException = ((ResponseException) e);
            Response response = responseException.getResponse();
            responseException.setMessage(String.format(mApplication.getString(R.string.http_response_error), response.code(), response.message()));
            return responseException;
        } else if (e instanceof NullBodyException) {
            NullBodyException nullBodyException = ((NullBodyException) e);
            nullBodyException.setMessage(mApplication.getString(R.string.http_response_null_body));
            return nullBodyException;
        } else if (e instanceof FileMd5Exception) {
            FileMd5Exception fileMd5Exception = ((FileMd5Exception) e);
            fileMd5Exception.setMessage(mApplication.getString(R.string.http_response_md5_error));
            return fileMd5Exception;
        }
        return requestFail(httpRequest, e);
    }

    @Nullable
    @Override
    public Object readCache(@NonNull HttpRequest<?> httpRequest, @NonNull Type type, long cacheTime) {
        String cacheKey = HttpCacheManager.generateCacheKey(httpRequest);
        String cacheValue = HttpCacheManager.getMmkv().getString(cacheKey, null);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return null;
        }
        EasyLog.printLog(httpRequest, "----- readCache cacheKey -----");
        EasyLog.printJson(httpRequest, cacheKey);
        EasyLog.printLog(httpRequest, "----- readCache cacheValue -----");
        EasyLog.printJson(httpRequest, cacheValue);
        return GsonFactory.getSingletonGson().fromJson(cacheValue, type);
    }

    @Override
    public boolean writeCache(@NonNull HttpRequest<?> httpRequest, @NonNull Response response, @NonNull Object result) {
        String cacheKey = HttpCacheManager.generateCacheKey(httpRequest);
        String cacheValue = GsonFactory.getSingletonGson().toJson(result);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return false;
        }
        EasyLog.printLog(httpRequest, "----- writeCache cacheKey -----");
        EasyLog.printJson(httpRequest, cacheKey);
        EasyLog.printLog(httpRequest, "----- writeCache cacheValue -----");
        EasyLog.printJson(httpRequest, cacheValue);
        return HttpCacheManager.getMmkv().putString(cacheKey, cacheValue).commit();
    }

    @Override
    public void clearCache() {
        HttpCacheManager.getMmkv().clearMemoryCache();
        HttpCacheManager.getMmkv().clearAll();
    }
}