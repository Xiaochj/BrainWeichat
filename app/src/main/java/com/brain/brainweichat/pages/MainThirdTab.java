package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * "发现"tab
 * Created by xiaochj on 2018/5/22.
 */

public class MainThirdTab {
  private static MainThirdTab instance = null;
  private AccessibilityService abs;

  private MainThirdTab(AccessibilityService abs) {
    this.abs = abs;
  }

  public static MainThirdTab getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (MainThirdTab.class) {
        if (instance == null) {
          instance = new MainThirdTab(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 点击朋友圈
   */
  public void clickFriendCircleLine(
      OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, null, abs.getString(R.string.friend_circle),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, null, abs.getString(R.string.friend_circle),
          abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener);
    }
  }
}
