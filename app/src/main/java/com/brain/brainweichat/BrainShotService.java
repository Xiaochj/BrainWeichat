package com.brain.brainweichat;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.List;

public class BrainShotService extends AccessibilityService {

  private static final int PICTURE_PICK_NUMBER = 3;

  private boolean isDiscoverOpen = false;
  private boolean isFriendCircleOpen = false;
  private boolean isPublishCircleOpen = false;
  private boolean isPublishFromAlarmOpen = false;
  private boolean isChoosePicturesOpen = false;
  private boolean isPublishTextOpen = false;

  public BrainShotService() {
    super();
  }

  @Override protected void onServiceConnected() {
    super.onServiceConnected();
  }

  @Override public void onAccessibilityEvent(AccessibilityEvent event) {
    int eventType = event.getEventType();
    Log.d("EventType", eventType + "");
    String pkgName = event.getPackageName().toString();
    if (pkgName.equalsIgnoreCase(getString(R.string.pkg_name))) {
      if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
        if (!isDiscoverOpen) {
          //打开客户端
          openTheTruth(null, getString(R.string.discover), getString(R.string.textview),
              AccessibilityNodeInfo.ACTION_CLICK);
          isDiscoverOpen = true;
        }
      } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        if (!isFriendCircleOpen) {
          //打开朋友圈
          openTheTruthDelay(null, getString(R.string.friend_circle), getString(R.string.textview),
              AccessibilityNodeInfo.ACTION_CLICK, 1000);
          isFriendCircleOpen = true;
        } else if (!isPublishCircleOpen && isFriendCircleOpen) {
          //发动态
          openTheTruthDelay(getString(R.string.publish_circle_image_id), null,
              getString(R.string.imageview), AccessibilityNodeInfo.ACTION_LONG_CLICK, 1500);
          isPublishCircleOpen = true;
        } else if (!isPublishTextOpen && isPublishCircleOpen) {
          //输入动态内容并发表
          inputAutoDelay(getString(R.string.publish_circle_text_id), null,
              getString(R.string.edittext), "test !!!", null, getString(R.string.publish_click),
              getString(R.string.textview), 2000);
          isPublishTextOpen = true;
        }
      }
    }
  }

  private void inputAutoDelay(final String id, final String text, final String clazz,
      final String inputContent, final String publishId, final String publishText,
      final String publishClazz, final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        inputAuto(id, text, clazz, inputContent, publishId, publishText, publishClazz);
      }
    }, millis);
  }

  private void inputAuto(String id, String text, String className, String inputContent,
      String publishId, String publishText, String publishClazz) {
    if (getRootInActiveWindow() != null) {
      List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
      if (id == null && !"".equals(text)) {
        //通过text寻找
        nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
      } else if (!"".equals(id) && text == null) {
        //通过view id寻找
        nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByViewId(id);
      }
      int size = nodeInfos.size();
      if (size == 0) return;
      if (size >= 1) {
        int temp = 0;
        while (temp < size) {
          if (nodeInfos.get(temp).getClassName().equals(className)) {
            ClipboardManager clipboard =
                (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", inputContent);
            clipboard.setPrimaryClip(clip);
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            //粘贴进入内容
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_PASTE);

            /**
             * 输入完内容，1s之后点击发表
             */
            openTheTruthDelay(publishId, publishText, publishClazz,
                AccessibilityNodeInfo.ACTION_CLICK, 1000);
            break;
          }
          temp++;
        }
        return;
      }
    }
  }

  /**
   * 根据text或者id来查找nodeInfos并且click
   */
  private void openTheTruth(String id, String text, String className, int action) {
    if (getRootInActiveWindow() != null) {
      List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
      if (id == null && !"".equals(text)) {
        //通过text寻找
        nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
      } else if (!"".equals(id) && text == null) {
        //通过view id寻找
        nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByViewId(id);
      }
      int size = nodeInfos.size();
      if (size == 0) return;
      if (size >= 1) {
        int temp = 0;
        while (temp < size) {
          if (nodeInfos.get(temp).getClassName().equals(className)) {
            /**
             * 一般wx的textview没有赋予click能力，而是他的parentView赋予了click，故getParent（）来传递action click
             *
             */
            nodeInfos.get(temp).performAction(action);
            if (nodeInfos.get(temp).getParent() != null) {
              nodeInfos.get(temp).getParent().performAction(action);
            }
            break;
          }
          temp++;
        }
        return;
      }
    }
  }

  /**
   * 延迟mills时间，再根据text或者id来查找nodeInfos并且click
   */
  private void openTheTruthDelay(final String id, final String text, final String clazz,
      final int action, final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        openTheTruth(id, text, clazz, action);
      }
    }, millis);
  }

  @Override public void onInterrupt() {

  }
}
