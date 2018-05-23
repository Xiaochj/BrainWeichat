package com.brain.brainweichat.pages;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 发布文字动态的页面
 * Created by xiaochj on 2018/5/22.
 */
public class PublishText {

  private static PublishText instance = null;
  private AccessibilityService abs;

  private PublishText(AccessibilityService abs) {
    this.abs = abs;
  }

  public static PublishText getInstance(AccessibilityService abs) {
    if (instance == null) {
      synchronized (PublishText.class) {
        if (instance == null) {
          instance = new PublishText(abs);
        }
      }
    }
    return instance;
  }

  /**
   * 发表文字状态
   */
  public void inputEditText(final int... millis) {
    if (millis.length == 2) {
      OpenTruthUtils.inputAutoDelay(abs, abs.getString(R.string.publish_circle_text_id), null,
          abs.getString(R.string.edittext), "test !!!",
          new OpenTruthUtils.OnAfterInputAutoListener() {
            @Override public void onAfterInputEvent() {
              /**
               * 输入完内容，1s之后点击发表
               */
              OpenTruthUtils.openTheTruthDelay(abs, null, abs.getString(R.string.publish_click),
                  abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK, millis[1]);
            }
          }, millis[0]);
    } else if (millis.length == 1) {
      OpenTruthUtils.inputAuto(abs, abs.getString(R.string.publish_circle_text_id), null,
          abs.getString(R.string.edittext), "test !!!",
          new OpenTruthUtils.OnAfterInputAutoListener() {
            @Override public void onAfterInputEvent() {
              /**
               * 输入完内容，1s之后点击发表
               */
              OpenTruthUtils.openTheTruthDelay(abs, null, abs.getString(R.string.publish_click),
                  abs.getString(R.string.textview), AccessibilityNodeInfo.ACTION_CLICK, millis[0]);
            }
          });
    }
  }
}















