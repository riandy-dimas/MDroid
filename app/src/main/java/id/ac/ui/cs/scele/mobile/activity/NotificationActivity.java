package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import android.os.Bundle;

public class NotificationActivity extends BaseNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Update current siteid based on notification clicked
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			new SessionSetting(this, extras.getLong("siteid", 1));

		setContentView(R.layout.activity_notification);
		setUpDrawer();

		// Send a tracker
		((ApplicationClass) getApplication())
				.sendScreen(Param.GA_SCREEN_NOTIFICATION);

		getSupportActionBar().setTitle("Notifications");
		getSupportActionBar().setIcon(R.drawable.icon_notifications_white);
	}
}
