package com.brain.brainweichat.utils;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心工具类，自动化处理的一些工具方法
 * Created by xiaochj on 2018/5/22.
 */

public class OpenTruthUtils {

  /**
   * 延迟输入
   */
  public static void inputAutoDelay(final AccessibilityService accessibilityService,
      final String id, final String text, final String clazz, final String inputContent,
      final OnAfterInputAutoListener onAfterInputAutoListener, final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        inputAuto(accessibilityService, id, text, clazz, inputContent, onAfterInputAutoListener);
      }
    }, millis);
  }

  /**
   * 自动输入内容
   */
  public static void inputAuto(AccessibilityService accessibilityService, String id, String text,
      String className, String inputContent, OnAfterInputAutoListener onAfterInputAutoListener) {
    if (accessibilityService.getRootInActiveWindow() != null) {
      List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
      if (id == null && !"".equals(text)) {
        //通过text寻找
        nodeInfos =
            accessibilityService.getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
      } else if (!"".equals(id) && text == null) {
        //通过view id寻找
        nodeInfos =
            accessibilityService.getRootInActiveWindow().findAccessibilityNodeInfosByViewId(id);
      }
      int size = nodeInfos.size();
      if (size == 0) return;
      if (size >= 1) {
        int temp = 0;
        while (temp < size) {
          if (nodeInfos.get(temp).getClassName().equals(className)) {
            ClipboardManager clipboard =
                (ClipboardManager) accessibilityService.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", inputContent);
            clipboard.setPrimaryClip(clip);
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            //粘贴进入内容
            nodeInfos.get(temp).performAction(AccessibilityNodeInfo.ACTION_PASTE);

            //内容写完之后的后续操作，比如点击发表
            onAfterInputAutoListener.onAfterInputEvent();

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
  public static void openTheTruth(AccessibilityService accessibilityService, String id, String text,
      String className, int action, OnAfterOpenTheTruthListener onAfterOpenTheTruthListener) {
    if (accessibilityService.getRootInActiveWindow() != null) {
      AccessibilityNodeInfo rootInActiveWIndow = accessibilityService.getRootInActiveWindow();
      List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
      if (id == null && !"".equals(text)) {
        //通过text寻找
        nodeInfos = rootInActiveWIndow.findAccessibilityNodeInfosByText(text);
      } else if (!"".equals(id) && text == null) {
        //通过view id寻找
        nodeInfos = rootInActiveWIndow.findAccessibilityNodeInfosByViewId(id);
      }
      int size = nodeInfos.size();
      //如果当前页面没有找到
      if (size == 0) {
        return;
      }
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
        if (onAfterOpenTheTruthListener != null) {
          onAfterOpenTheTruthListener.onAfterOpenEvent();
        }
        return;
      }
    }
  }

  /**
   * 延迟mills时间，再根据text或者id来查找nodeInfos并且click
   */
  public static void openTheTruthDelay(final AccessibilityService accessibilityService,
      final String id, final String text, final String clazz, final int action,
      final OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, final int millis) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        openTheTruth(accessibilityService, id, text, clazz, action, onAfterOpenTheTruthListener);
      }
    }, millis);
  }

  /**
   * 延迟滑动当前屏幕
   */
  public static void scrollListViewTruthDelay(final AccessibilityService accessibilityService,
      final AccessibilityNodeInfo accessibilityNodeInfo, final String id, final String text,
      final String clazz, final int action,
      final OnAfterOpenTheTruthListener onAfterOpenTheTruthListener, final int mills) {
    if (accessibilityService.getRootInActiveWindow() != null) {
      if (accessibilityNodeInfo.getClassName()
          .equals(accessibilityService.getString(R.string.listview))) {
        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override public void run() {
            openTheTruthDelay(accessibilityService, id, text, clazz, action,
                onAfterOpenTheTruthListener, mills);
          }
        }, 1000);
      }
    }
  }

  /**
   * 滑动当前屏幕操作
   */
  public static void scrollListViewTruth(final AccessibilityService accessibilityService,
      final AccessibilityNodeInfo accessibilityNodeInfo, final String id, final String text,
      final String clazz, final int action,
      final OnAfterOpenTheTruthListener onAfterOpenTheTruthListener) {
    if (accessibilityService.getRootInActiveWindow() != null) {
      if (accessibilityNodeInfo.getClassName()
          .equals(accessibilityService.getString(R.string.listview))) {
        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override public void run() {
            openTheTruth(accessibilityService, id, text, clazz, action,
                onAfterOpenTheTruthListener);
          }
        }, 1000);
      }
    }
  }

  /**
   * 输入完内容之后可以做的事情，写在这里
   */
  public interface OnAfterInputAutoListener {
    void onAfterInputEvent();
  }

  /**
   * 点击了一些地方打开新的页面之后可以做的事情，写在这里
   */
  public interface OnAfterOpenTheTruthListener {
    void onAfterOpenEvent();
  }
}
