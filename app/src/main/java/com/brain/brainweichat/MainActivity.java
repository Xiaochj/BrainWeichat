package com.brain.brainweichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.brain.brainweichat.utils.Utils;

/**
 * app的首页
 */
public class MainActivity extends Activity {

  private static final String SERVICE =
      "com.brain.brainweichat/com.brain.brainweichat.services.BrainShotService";
  //多账号自动切换
  private static final String ACCOUNTS_SERVICE =
      "com.brain.brainweichat/com.brain.brainweichat.services.AccountsAutoChangeService";
  private Switch aSwitch0, aSwitch1;
  private boolean isOn = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LinearLayout ll = new LinearLayout(this);
    ll.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    ll.setOrientation(LinearLayout.VERTICAL);
    setContentView(ll);
    aSwitch0 = new Switch(this);
    aSwitch0.setText(R.string.app_name);
    aSwitch1 = new Switch(this);
    aSwitch1.setText(R.string.accounts_service_name);
    ll.addView(aSwitch0);
    ll.addView(aSwitch1);
  }

  @Override protected void onResume() {
    super.onResume();

    isAccessibilitySettingOn(SERVICE, aSwitch0);

    isAccessibilitySettingOn(ACCOUNTS_SERVICE, aSwitch1);

    aSwitch0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        onCheckedChange(isChecked);
      }
    });
    aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        onCheckedChange(isChecked);
      }
    });
  }

  private void onCheckedChange(boolean isChecked) {
    if (isChecked && !isOn || !isChecked && isOn) {
      Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
      startActivityForResult(intent, 0);
    }
  }

  private void isAccessibilitySettingOn(String service, Switch aSwitch) {
    if (Utils.isAccessibilitySettingsOn(this, service)) {
      aSwitch.setChecked(true);
      isOn = true;
      Intent intent = getPackageManager().getLaunchIntentForPackage(getString(R.string.pkg_name));
      startActivity(intent);
    } else {
      aSwitch.setChecked(false);
      isOn = false;
    }
  }
}
