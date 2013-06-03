package net.zhuoweizhang.notifytooth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onOpenAccessibilitySettingsClick(View v) {
		Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
		startActivity(intent);
	}
}
