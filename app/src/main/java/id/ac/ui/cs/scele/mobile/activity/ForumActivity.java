package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import android.os.Bundle;

public class ForumActivity extends BaseNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum);
		setUpDrawer();

		getSupportActionBar().setTitle("Forums");
		getSupportActionBar().setIcon(R.drawable.icon_forum);
	}

}
