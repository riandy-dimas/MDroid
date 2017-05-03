package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.ForumIdInterface;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleForum;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

public class DiscussionActivity extends BaseNavigationActivity implements
		ForumIdInterface {
	int forumid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		forumid = getIntent().getExtras().getInt("forumid");
		setContentView(R.layout.activity_discussion);
		setUpDrawer();

		// Set title
		SessionSetting session = new SessionSetting(this);
		List<MoodleForum> mForums = MoodleForum.find(MoodleForum.class,
				"forumid = ? and siteid = ?", String.valueOf(forumid),
				String.valueOf(session.getCurrentSiteId()));
        if (!mForums.isEmpty())
			getSupportActionBar().setTitle(mForums.get(0).getName());
		getSupportActionBar().setIcon(R.drawable.icon_forum);
	}

	@Override
	public int getForumId() {
		return this.forumid;
	}

}
