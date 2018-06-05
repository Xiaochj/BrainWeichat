package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 朋友圈页面
 * Created by xiaochj on 2018/5/22.
 */

public class FriendCircle {

  private static FriendCircle instance = null;
  private AccessibilityService abs;

  private FriendCircle(AccessibilityService abs) {
    this.abs = abs;
  }

  public static FriendCircle getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (FriendCircle.class) {
        if (instance == null) {
          instance = new FriendCircle(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 滑动朋友圈
   */
  public void scrollFriendCircleList(
      OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.scrollListViewTruthDelay(abs,
          abs.getRootInActiveWindow().getChild(0).getChild(0),  abs.getString(R.string.commit_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.scrollListViewTruth(abs, abs.getRootInActiveWindow().getChild(0).getChild(0),
          abs.getString(R.string.commit_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener);
    }
  }

  /**
   * 发布动态
   */
  public void clickPublishBtn(
      OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, abs.getString(R.string.publish_circle_image_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_LONG_CLICK,
          onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, abs.getString(R.string.publish_circle_image_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_LONG_CLICK,
          onAfterOpenTheTruthListener);
    }
  }

  public void clickBackBtn(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, abs.getString(R.string.back_circle_id), null,
          abs.getString(R.string.linearlayout), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, abs.getString(R.string.back_circle_id), null,
          abs.getString(R.string.linearlayout), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener);
    }
  }

  /**
   * 发表评论
   * @param onAfterOpenTheTruthListener
   * @param millis
   */
  public void clickCommitBtn(OpenTruthUtils.OnAfterOpenTheTruthListener onAfterOpenTheTruthListener,
      int... millis) {
    if (millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, abs.getString(R.string.commit_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener, millis[0]);
    } else {
      OpenTruthUtils.openTheTruth(abs, abs.getString(R.string.commit_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_CLICK,
          onAfterOpenTheTruthListener);
    }
  }
}



















