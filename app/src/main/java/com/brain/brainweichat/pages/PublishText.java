package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 发布文字动态的页面
 * Created by xiaochj on 2018/5/22.
 */
public class PublishText {

  private static PublishText instance = null;
  private AccessibilityService abs;

  private PublishText(AccessibilityService abs){
    this.abs = abs;
  }

  public static PublishText getInstance(AccessibilityService abs){
    if(instance == null){
      synchronized (PublishText.class){
        if(instance == null){
          instance = new PublishText(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 发表文字状态
   * @param millis
   */
  public void inputEditText(int ... millis){
    if(millis.length > 0) {
      OpenTruthUtils.inputAutoDelay(abs, abs.getString(R.string.publish_circle_text_id), null, abs.getString(R.string.edittext), "test !!!", null, abs.getString(R.string.publish_click),
          abs.getString(R.string.textview), millis[0]);
    }else {
      OpenTruthUtils.inputAuto(abs, abs.getString(R.string.publish_circle_text_id), null, abs.getString(R.string.edittext), "test !!!", null, abs.getString(R.string.publish_click),
          abs.getString(R.string.textview));
    }
  }
}















