package com.brain.brainweichat;

import android.accessibilityservice.AccessibilityService;
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
          openTheTruth(null, getString(R.string.discover), getString(R.string.textview));
          isDiscoverOpen = true;
        }
      } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        if (!isFriendCircleOpen) {
          //打开朋友圈
          openTheTruthDelay(null, getString(R.string.friend_circle), getString(R.string.textview),
              1000);
          isFriendCircleOpen = true;
        } else if (!isPublishCircleOpen && isFriendCircleOpen) {
          //发动态
          openTheTruthDelay(getString(R.string.publish_circle_image_id), null,
              getString(R.string.imageview), 1500);
          isPublishCircleOpen = true;
        } else if (!isPublishFromAlarmOpen && isPublishCircleOpen) {
          //打开相册
          openTheTruthDelay(null, getString(R.string.publish_alarm), getString(R.string.textview),
              2000);
          isPublishFromAlarmOpen = true;
        } else if (!isChoosePicturesOpen && isPublishFromAlarmOpen) {
          choosePictureDelay(getString(R.string.picture_pick_id), getString(R.string.view), 2500);
          isChoosePicturesOpen = true;
        }
      }
    }
  }

  /**
   * 根据text或者id来查找nodeInfos并且click
   */
  private void openTheTruth(String id, String text, String className) {
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
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (nodeInfos.get(temp).getParent() != null) {
              nodeInfos.get(temp).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
      final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        openTheTruth(id, text, clazz);
      }
    }, millis);
  }

  private void choosePictureDelay(final String id, final String clazz, final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        choosePictures(id, clazz);
      }
    }, millis);
  }

  private void choosePictures(String id, String clazz) {
    if (getRootInActiveWindow() != null) {
      List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
      if (!"".equals(id)) {
        //通过view id寻找
        nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByViewId(id);
      }
      int size = nodeInfos.size();
      if (size == 0) return;
      if (size >= PICTURE_PICK_NUMBER) {
        int temp = 0;
        while (temp < PICTURE_PICK_NUMBER) {
          if (nodeInfos.get(temp).getClassName().equals(clazz)) {
            /**
             * 一般wx的textview没有赋予click能力，而是他的parentView赋予了click，故getParent（）来传递action click
             *
             */
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            nodeInfos.get(temp).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            break;
          }
          temp++;
        }
        return;
      }
    }
  }

  @Override public void onInterrupt() {

  }
}
