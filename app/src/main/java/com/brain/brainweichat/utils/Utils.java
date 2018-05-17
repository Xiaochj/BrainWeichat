package com.brain.brainweichat.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class Utils {

  private static final String TAG = "utils";

  /**
   * log日志
   */
  public static void LogUtil(String logType, String tag, String str) {
    if (logType.equalsIgnoreCase("v")) {

    } else if (logType.equalsIgnoreCase("d")) {
      Log.d(tag, str);
    } else if (logType.equalsIgnoreCase("e")) {

    } else if (logType.equalsIgnoreCase("i")) {

    }
  }

  /**
   * toast提示
   */
  public static void ToastUtil(Context ctx, String text) {
    Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
  }

  /**
   * 判断某个服务是否开启
   */
  public static boolean isAccessibilitySettingsOn(Context ctx, String service) {
    int accessibilityEnabled = 0;
    //        final String service = "com.test.package.name/com.test.package.name.YOURAccessibilityService";
    boolean accessibilityFound = false;
    try {
      accessibilityEnabled =
          Settings.Secure.getInt(ctx.getApplicationContext().getContentResolver(),
              android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
    } catch (Exception e) {
      Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
    }
    TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

    if (accessibilityEnabled == 1) {
      Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
      String settingValue =
          Settings.Secure.getString(ctx.getApplicationContext().getContentResolver(),
              Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
      if (settingValue != null) {
        TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
        splitter.setString(settingValue);
        while (splitter.hasNext()) {
          String accessabilityService = splitter.next();

          Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
          if (accessabilityService.equalsIgnoreCase(service)) {
            Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
            return true;
          }
        }
      }
    } else {
      Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
    }

    return accessibilityFound;
  }
}
