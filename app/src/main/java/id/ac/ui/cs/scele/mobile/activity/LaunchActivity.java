package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchActivity extends Activity {

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		SessionSetting session = new SessionSetting(this);

		// Skip to courses if logged in
		if (session.getCurrentSiteId() != SessionSetting.NO_SITE_ID) {
			Intent i = new Intent(this, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			this.startActivity(i);
			return;
		}

		// Skip to login if tutorial done before
		if (session.isTutored()) {
			Intent i = new Intent(this, LoginActivity.class);
			this.startActivity(i);
			return;
		}

		// Start from Tutorial otherwise
		Intent i = new Intent(this, MainActivity.class);
		this.startActivity(i);

	}

}
