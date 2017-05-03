package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.DiscussionIdInterface;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleDiscussion;

import java.util.List;

import android.os.Bundle;

public class PostActivity extends BaseNavigationActivity implements
		DiscussionIdInterface {
	int discussionid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		discussionid = getIntent().getExtras().getInt("discussionid");
		setContentView(R.layout.activity_post);
		setUpDrawer();

		// Set title
		SessionSetting session = new SessionSetting(this);
		List<MoodleDiscussion> mDiscussions = MoodleDiscussion.find(
				MoodleDiscussion.class, "discussionid = ? and siteid = ?",
				String.valueOf(discussionid), String.valueOf(session.getCurrentSiteId()));
		if (!mDiscussions.isEmpty())
			getSupportActionBar().setTitle(mDiscussions.get(0).getName());
		getSupportActionBar().setIcon(R.drawable.icon_forum);
	}

	@Override
	public int getDiscussionId() {
		return this.discussionid;
	}
}
