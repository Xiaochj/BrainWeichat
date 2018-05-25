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
   * 点击"微信"tab
   */
  public void clickFirstTab(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... mills) {
    if (mills.length > 0) {
      clickTab(null, abs.getString(R.string.weixin), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener, mills[0]);
    } else {
      clickTab(null, abs.getString(R.string.weixin), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener);
    }
  }

  /**
   * 点击"通讯录"tab
   */
  public void clickSecondTab(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... mills) {
    if (mills.length > 0) {
      clickTab(null, abs.getString(R.string.contact), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener, mills[0]);
    } else {
      clickTab(null, abs.getString(R.string.contact), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener);
    }
  }

  /**
   * 点击"发现"tab
   */
  public void clickThirdTab(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... mills) {
    if (mills.length > 0) {
      clickTab(null, abs.getString(R.string.discover), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener, mills[0]);
    } else {
      clickTab(null, abs.getString(R.string.discover), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener);
    }
  }

  /**
   * 点击"我"tab
   */
  public void clickForthTab(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... mills) {
    if (mills.length > 0) {
      clickTab(null, abs.getString(R.string.me), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener, mills[0]);
    } else {
      clickTab(null, abs.getString(R.string.me), abs.getString(R.string.textview),
          onAfterOpenTheTruthListener);
    }
  }

  private void clickTab(String id, String text, String clazz,
      OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, int... mills) {
    if (mills.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, id, text, clazz, AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener, mills[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, id, text, clazz, AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener);
    }
  }
}
