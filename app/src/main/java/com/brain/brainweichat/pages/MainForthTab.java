package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * "我"tab
 * Created by xiaochj on 2018/5/24.
 */

public class MainForthTab {

  private static MainForthTab instance = null;
  private AccessibilityService abs;

  private MainForthTab(AccessibilityService abs) {
    this.abs = abs;
  }

  public static MainForthTab getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (MainForthTab.class) {
        if (instance == null) {
          instance = new MainForthTab(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 点击设置
   */
  public void clickSettingBtn(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, null, abs.getString(R.string.setting),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK,onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, null, abs.getString(R.string.setting),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK,onAfterOpenTheTruthListener);
    }
  }
}
















