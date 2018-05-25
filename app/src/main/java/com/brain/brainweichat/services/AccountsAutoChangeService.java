package com.brain.brainweichat.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.brain.brainweichat.R;
import com.brain.brainweichat.pages.MainForthTab;
import com.brain.brainweichat.pages.MainPage;
import com.brain.brainweichat.pages.Setting;
import com.brain.brainweichat.utils.OpenTruthUtils;

/**
 * 多账号自动切换service
 * Created by xiaochj on 2018/5/24.
 */

public class AccountsAutoChangeService extends AccessibilityService {

  private boolean isMeOpen = false;
  private boolean isSettingOpen = false;
  private boolean isChangeAccountOpen = false;

  public AccountsAutoChangeService() {
    super();
  }

  @Override public void onAccessibilityEvent(AccessibilityEvent event) {
    int eventType = event.getEventType();
    Log.d("EventType", eventType + "");
    String pkgName = event.getPackageName().toString();
    if (pkgName.equalsIgnoreCase(getString(R.string.pkg_name))) {
      if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
        if (!isMeOpen) {
          //打开客户端并进入我 tab
          MainPage.getInstance(this).clickForthTab(null);
          isMeOpen = true;
        }
      } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
        if (!isSettingOpen) {
          //打开设置
          MainForthTab.getInstance(this).clickSettingBtn(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isSettingOpen = true;
            }
          },1500);
        }else if(!isChangeAccountOpen && isSettingOpen){
          //切换帐号
          Setting.getInstance(this).clickChangeAccountBtn(new OpenTruthUtils.OnAfterOpenTheTruthListener() {
            @Override public void onAfterOpenEvent() {
              isChangeAccountOpen = true;
            }
          },2000);
        }
      }
    }
  }

  @Override public void onInterrupt() {

  }

  @Override protected void onServiceConnected() {
    super.onServiceConnected();
  }

  @Override protected boolean onKeyEvent(KeyEvent event) {
    return super.onKeyEvent(event);
  }

  @Override public AccessibilityNodeInfo getRootInActiveWindow() {
    return super.getRootInActiveWindow();
  }
}
