package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 设置页
 * Created by xiaochj on 2018/5/24.
 */

public class Setting {
  private static Setting instance = null;
  private AccessibilityService abs;

  private Setting(AccessibilityService abs) {
    this.abs = abs;
  }

  public static Setting getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (Setting.class) {
        if (instance == null) {
          instance = new Setting(abs);
        }
      }
    }
    return instance;
  }

  public void clickChangeAccountBtn(
      OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.scrollListViewTruthDelay(abs, abs.getRootInActiveWindow().getChild(0).getChild(2),
          null, abs.getString(R.string.change_account), abs.getString(R.string.textview),
          AccessibilityNodeInfo.ACTION_CLICK, onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.scrollListViewTruth(abs, abs.getRootInActiveWindow().getChild(0).getChild(2),
          null, abs.getString(R.string.change_account), abs.getString(R.string.textview),
          AccessibilityNodeInfo.ACTION_CLICK, onAfterOpenTheTruthListener);
    }
  }
}
