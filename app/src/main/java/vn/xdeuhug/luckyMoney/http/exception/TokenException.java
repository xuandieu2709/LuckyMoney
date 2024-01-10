package vn.xdeuhug.luckyMoney.http.exception;

import com.hjq.http.exception.HttpException;

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
public final class TokenException extends HttpException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}