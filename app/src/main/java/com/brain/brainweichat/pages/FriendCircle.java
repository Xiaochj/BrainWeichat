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

  private FriendCircle(AccessibilityService abs){
    this.abs = abs;
  }

  public static FriendCircle getInstance(AccessibilityService abs){
    if(instance == null){
      synchronized (FriendCircle.class){
        if(instance == null){
          instance = new FriendCircle(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 发布状态
   */
  public void clickPublishBtn(int ...millis){
    if(millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, abs.getString(R.string.publish_circle_image_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_LONG_CLICK, millis[0]);
    }else {
      OpenTruthUtils.openTheTruth(abs, abs.getString(R.string.publish_circle_image_id), null,
          abs.getString(R.string.imageview), AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }
  }

  public void clickBackBtn(int ...millis){
    if(millis.length > 0) {
      OpenTruthUtils.openTheTruthDelay(abs, abs.getString(R.string.back_circle_id), null,
          abs.getString(R.string.linearlayout), AccessibilityNodeInfo.ACTION_CLICK, millis[0]);
    }else {
      OpenTruthUtils.openTheTruth(abs, abs.getString(R.string.back_circle_id), null,
          abs.getString(R.string.linearlayout), AccessibilityNodeInfo.ACTION_CLICK);
    }
  }
}



















