package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 微信主页面
 * Created by xiaochj on 2018/5/22.
 */

public class MainPage {

  private static MainPage instance = null;
  private AccessibilityService abs;

  private MainPage(AccessibilityService abs) {
    this.abs = abs;
  }

  public static MainPage getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (MainPage.class) {
        if (instance == null) {
          instance = new MainPage(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 点击"发现"tab
   */
  public void clickThirdTab(int... mills) {
    if (mills.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, null, abs.getString(R.string.discover),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK, mills[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, null, abs.getString(R.string.discover),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK);
    }
  }
}
