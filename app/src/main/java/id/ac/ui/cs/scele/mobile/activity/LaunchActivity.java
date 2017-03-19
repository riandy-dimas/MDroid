package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LaunchActivity extends Activity {

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		final SessionSetting session = new SessionSetting(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Skip to courses if logged in
                if (session.getCurrentSiteId() != SessionSetting.NO_SITE_ID) {
                    Intent i = new Intent(LaunchActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    LaunchActivity.this.startActivity(i);
                    return;
                }

                // Skip to login if tutorial done before
                if (session.isTutored()) {
                    Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
                    LaunchActivity.this.startActivity(i);
                    return;
                }

                // Start from Tutorial otherwise
                Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
                LaunchActivity.this.startActivity(i);
            }
        }, 3000);



	}

}
