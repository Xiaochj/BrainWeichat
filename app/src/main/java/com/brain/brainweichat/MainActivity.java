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

public class MainActivity extends Activity {

  private static final String SERVICE =
      "com.brain.brainweichat/com.brain.brainweichat.BrainShotService";
  private Switch aSwitch;
  private boolean isOn = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LinearLayout ll = new LinearLayout(this);
    ll.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    setContentView(ll);
    aSwitch = new Switch(this);
    ll.addView(aSwitch);
  }

  @Override protected void onResume() {
    super.onResume();
    if (Utils.isAccessibilitySettingsOn(this, SERVICE)) {
      aSwitch.setChecked(true);
      isOn = true;
      Intent intent = getPackageManager().getLaunchIntentForPackage(getString(R.string.pkg_name));
      startActivity(intent);
    } else {
      aSwitch.setChecked(false);
      isOn = false;
    }
    aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && !isOn || !isChecked && isOn) {
          Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
          startActivityForResult(intent, 0);
        }
      }
    });
  }
}
