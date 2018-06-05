package com.brain.brainweichat.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.brain.brainweichat.R;
import com.brain.brainweichat.pages.FriendCircle;
import com.brain.brainweichat.pages.MainPage;
import com.brain.brainweichat.pages.MainThirdTab;
import com.brain.brainweichat.pages.PublishText;
import com.brain.brainweichat.utils.OpenTruthUtils;

public class BrainShotService extends AccessibilityService {
  private boolean isDiscoverOpen = false;
  private boolean isFriendCircleOpen = false;
  private boolean isPublishCircleOpen = false;
  private boolean isPublishTextOpen = false;
  private boolean isBackCircleOpen = false;

  int i = 0;

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
          MainPage.getInstance(this).clickThirdTab(null);
          isDiscoverOpen = true;
        }
      } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        if (!isFriendCircleOpen) {
          //打开朋友圈
          MainThirdTab.getInstance(this).clickFriendCircleLine(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isFriendCircleOpen = true;
            }
          },1500);
        } else if (!isPublishCircleOpen && isFriendCircleOpen) {
          //发动态
          FriendCircle.getInstance(this).clickPublishBtn(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isPublishCircleOpen = true;
            }
          },2000);
        } else if (!isPublishTextOpen && isPublishCircleOpen) {
          //输入动态内容并发表
          PublishText.getInstance(this).inputEditText(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isPublishTextOpen = true;
            }
          },2500, 1000);
        }
        //else if(isPublishTextOpen && getRootInActiveWindow().getChild(0).getChild(0).getClassName()
        //    .equals(getString(R.string.listview))){
        //  FriendCircle.getInstance(this).scrollFriendCircleList(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
        //    @Override public void onAfterOpenEvent() {
        //      Handler handler = new Handler();
        //      handler.postDelayed(new Runnable() {
        //        @Override public void run() {
        //          Toast.makeText(getApplicationContext(),"hungrySword"+i,Toast.LENGTH_LONG).show();
        //          i++;
        //        }
        //      },1000);
        //    }
        //  },3000);
        //}
        else if (!isBackCircleOpen && isPublishTextOpen) {
          //退出朋友圈
          FriendCircle.getInstance(this).clickBackBtn(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isBackCircleOpen = true;
            }
          },4000);
        }
      }
    }
  }

  @Override public void onInterrupt() {

  }
}
