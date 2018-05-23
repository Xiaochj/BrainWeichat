package com.brain.brainweichat;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.brain.brainweichat.pages.FriendCircle;
import com.brain.brainweichat.pages.MainPage;
import com.brain.brainweichat.pages.MainThirdTab;
import com.brain.brainweichat.pages.PublishText;

public class BrainShotService extends AccessibilityService {
  private boolean isDiscoverOpen = false;
  private boolean isFriendCircleOpen = false;
  private boolean isPublishCircleOpen = false;
  private boolean isPublishTextOpen = false;
  private boolean isBackCircleOpen = false;

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
          //打开客户端并进入发现tab
          MainPage.getInstance(this).clickThirdTab();
          isDiscoverOpen = true;
        }
      } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        if (!isFriendCircleOpen) {
          //打开朋友圈
          MainThirdTab.getInstance(this).clickFriendCircleLine(1000);
          isFriendCircleOpen = true;
        } else if (!isPublishCircleOpen && isFriendCircleOpen) {
          //发动态
          FriendCircle.getInstance(this).clickPublishBtn(1500);
          isPublishCircleOpen = true;
        } else if (!isPublishTextOpen && isPublishCircleOpen) {
          //输入动态内容并发表
          PublishText.getInstance(this).inputEditText(2000, 1000);
          isPublishTextOpen = true;
        } else if (!isBackCircleOpen && isPublishTextOpen) {
          //退出朋友圈
          FriendCircle.getInstance(this).clickBackBtn(5000);
          isBackCircleOpen = true;
        }
      }
    }
  }

  @Override public void onInterrupt() {

  }
}
