package net.zhuoweizhang.notifytooth;

import java.util.*;

import android.app.*;
import android.content.*;
import android.accessibilityservice.*;
import android.util.*;
import android.view.*;
import android.view.accessibility.*;
import android.widget.*;

public class NotifyService extends AccessibilityService {

	public final static String ACTION_INSERT_OR_UPDATE = "com.google.glass.timeline.INSERT_OR_UPDATE";

	public static final List<Integer> hiddenViewIds = new ArrayList<Integer>();

	public static final String TAG = "NotifyService";

	public void onAccessibilityEvent(AccessibilityEvent e) {
		Log.i(TAG, e.toString());
		int eventType = e.getEventType();
		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			Notification notification = (Notification) e.getParcelableData();
//			Log.i(TAG, notification.toString());
			try {
				blastNotification(notification);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void blastNotification(Notification notification) {
		String text = getDescription(notification);
		if (text == null) return;
		Log.i(TAG, text);
		Intent intent = buildMirrorApiIntent(text);
		sendBroadcast(intent);
	}

	public String getDescription(Notification notification) {
		if (notification.contentView == null) return null;
		ViewGroup v = (ViewGroup) notification.contentView.apply(this, null);
		Log.i(TAG, v.toString());
		StringBuilder textBuilder = new StringBuilder();
		buildDescString(v, textBuilder);
		return textBuilder.toString().replace("\n", " ");
	}

	private void buildDescString(ViewGroup v, StringBuilder textBuilder) {
		for (int i = 0; i < v.getChildCount(); i++) {
			View subView = v.getChildAt(i);
			//Log.i(TAG, subView.toString());
			if (hiddenViewIds.contains(subView.getId())) continue;
			if (subView instanceof TextView) {
				textBuilder.append(((TextView) subView).getText());
				textBuilder.append(' ');
			} else if (subView instanceof ViewGroup) {
				buildDescString((ViewGroup) subView, textBuilder);
			}
		}
	}

	public Intent buildMirrorApiIntent(String text) {
		Intent intent = new Intent(ACTION_INSERT_OR_UPDATE);
		intent.putExtra("timeline_item_local_id", "notifytooth_1234"); //we only have one item; in a real app this is probably more complicated
		intent.putExtra("project_id", "12345678"); //Glass doesn't verify if this is an actual project, but you should probably use a real ID just to be safe
		intent.putExtra("token", "12345678");
		intent.putExtra("package_name", "net.zhuoweizhang.notifytooth");
		intent.putExtra("timeline_item_text", text);
		return intent;
	}

	public void onInterrupt() {
		Log.i(TAG, "BAWK!");
	}

	static {
		//thanks, GTalkSMS! https://code.google.com/p/gtalksms/source/browse/src/com/googlecode/gtalksms/AccessibilityService.java
		hiddenViewIds.add(16908388); // Time of the notification
		hiddenViewIds.add(16909096); // Number of items
	}
}
