package vn.xdeuhug.luckyMoney;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vn.xdeuhug.luckyMoney.databinding.CrashActivityBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogConfirmBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogLuckyBoxBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogLuckyMoneyBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogNotHaveMoneyBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogNotificationBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogSettingMoneyBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.DialogSoundBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.SplashActivityBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.SuccessDialogBindingImpl;
import vn.xdeuhug.luckyMoney.databinding.WaitDialogBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_CRASHACTIVITY = 1;

  private static final int LAYOUT_DIALOGCONFIRM = 2;

  private static final int LAYOUT_DIALOGLUCKYBOX = 3;

  private static final int LAYOUT_DIALOGLUCKYMONEY = 4;

  private static final int LAYOUT_DIALOGNOTHAVEMONEY = 5;

  private static final int LAYOUT_DIALOGNOTIFICATION = 6;

  private static final int LAYOUT_DIALOGSETTINGMONEY = 7;

  private static final int LAYOUT_DIALOGSOUND = 8;

  private static final int LAYOUT_SPLASHACTIVITY = 9;

  private static final int LAYOUT_SUCCESSDIALOG = 10;

  private static final int LAYOUT_WAITDIALOG = 11;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(11);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.crash_activity, LAYOUT_CRASHACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_confirm, LAYOUT_DIALOGCONFIRM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_lucky_box, LAYOUT_DIALOGLUCKYBOX);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_lucky_money, LAYOUT_DIALOGLUCKYMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_not_have_money, LAYOUT_DIALOGNOTHAVEMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_notification, LAYOUT_DIALOGNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_setting_money, LAYOUT_DIALOGSETTINGMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.dialog_sound, LAYOUT_DIALOGSOUND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.splash_activity, LAYOUT_SPLASHACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.success_dialog, LAYOUT_SUCCESSDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(vn.xdeuhug.luckyMoney.R.layout.wait_dialog, LAYOUT_WAITDIALOG);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_CRASHACTIVITY: {
          if ("layout/crash_activity_0".equals(tag)) {
            return new CrashActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for crash_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGCONFIRM: {
          if ("layout/dialog_confirm_0".equals(tag)) {
            return new DialogConfirmBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_confirm is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGLUCKYBOX: {
          if ("layout/dialog_lucky_box_0".equals(tag)) {
            return new DialogLuckyBoxBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_lucky_box is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGLUCKYMONEY: {
          if ("layout/dialog_lucky_money_0".equals(tag)) {
            return new DialogLuckyMoneyBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_lucky_money is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGNOTHAVEMONEY: {
          if ("layout/dialog_not_have_money_0".equals(tag)) {
            return new DialogNotHaveMoneyBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_not_have_money is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGNOTIFICATION: {
          if ("layout/dialog_notification_0".equals(tag)) {
            return new DialogNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGSETTINGMONEY: {
          if ("layout/dialog_setting_money_0".equals(tag)) {
            return new DialogSettingMoneyBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_setting_money is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGSOUND: {
          if ("layout/dialog_sound_0".equals(tag)) {
            return new DialogSoundBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_sound is invalid. Received: " + tag);
        }
        case  LAYOUT_SPLASHACTIVITY: {
          if ("layout/splash_activity_0".equals(tag)) {
            return new SplashActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for splash_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_SUCCESSDIALOG: {
          if ("layout/success_dialog_0".equals(tag)) {
            return new SuccessDialogBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for success_dialog is invalid. Received: " + tag);
        }
        case  LAYOUT_WAITDIALOG: {
          if ("layout/wait_dialog_0".equals(tag)) {
            return new WaitDialogBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for wait_dialog is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(11);

    static {
      sKeys.put("layout/crash_activity_0", vn.xdeuhug.luckyMoney.R.layout.crash_activity);
      sKeys.put("layout/dialog_confirm_0", vn.xdeuhug.luckyMoney.R.layout.dialog_confirm);
      sKeys.put("layout/dialog_lucky_box_0", vn.xdeuhug.luckyMoney.R.layout.dialog_lucky_box);
      sKeys.put("layout/dialog_lucky_money_0", vn.xdeuhug.luckyMoney.R.layout.dialog_lucky_money);
      sKeys.put("layout/dialog_not_have_money_0", vn.xdeuhug.luckyMoney.R.layout.dialog_not_have_money);
      sKeys.put("layout/dialog_notification_0", vn.xdeuhug.luckyMoney.R.layout.dialog_notification);
      sKeys.put("layout/dialog_setting_money_0", vn.xdeuhug.luckyMoney.R.layout.dialog_setting_money);
      sKeys.put("layout/dialog_sound_0", vn.xdeuhug.luckyMoney.R.layout.dialog_sound);
      sKeys.put("layout/splash_activity_0", vn.xdeuhug.luckyMoney.R.layout.splash_activity);
      sKeys.put("layout/success_dialog_0", vn.xdeuhug.luckyMoney.R.layout.success_dialog);
      sKeys.put("layout/wait_dialog_0", vn.xdeuhug.luckyMoney.R.layout.wait_dialog);
    }
  }
}
