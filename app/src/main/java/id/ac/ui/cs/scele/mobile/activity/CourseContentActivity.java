package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.fragment.CalenderFragment;
import id.ac.ui.cs.scele.mobile.fragment.ContentFragment;
import id.ac.ui.cs.scele.mobile.fragment.ForumFragment;
import id.ac.ui.cs.scele.mobile.fragment.ParticipantFragment;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleCourse;
import id.ac.ui.cs.scele.mobile.view.SlidingTabLayout;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class CourseContentActivity extends BaseNavigationActivity {
	private int courseid;
	private ViewPager viewPager;
	private static final String[] TABS = { "Contents", "Forums", "Calendar",
			"Participants" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_contents);
		setUpDrawer();

		// Send a tracker
		((ApplicationClass) getApplication())
				.sendScreen(Param.GA_SCREEN_CONTENT);

		Bundle extras = getIntent().getExtras();
		courseid = extras.getInt("courseid");

		// Get course details
		SessionSetting session = new SessionSetting(this);
		List<MoodleCourse> dbCourses = MoodleCourse.find(MoodleCourse.class,
				"siteid = ? and courseid = ?", String.valueOf(session.getCurrentSiteId()),
				String.valueOf(courseid));
		if (dbCourses == null || dbCourses.isEmpty()) {
			Toast.makeText(this, "Course not found in database!",
					Toast.LENGTH_LONG).show();
			return;
		}
		MoodleCourse mCourse = dbCourses.get(0);

		getSupportActionBar().setTitle(mCourse.getFullname());
		getSupportActionBar().setIcon(R.drawable.icon_school);

		FragmentPagerAdapter mAdapter = new CourseContentTabsAdapter(
				getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.course_content_pager);
		viewPager.setOffscreenPageLimit(TABS.length);
		viewPager.setAdapter(mAdapter);

		SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(viewPager);
	}

	class CourseContentTabsAdapter extends FragmentPagerAdapter {
		public CourseContentTabsAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				// Course Content
                ContentFragment contentFragment = new ContentFragment();
                contentFragment.setCourseid(courseid);
				return contentFragment;
			case 1:
				// Course Forum
                ForumFragment forumFragment = new ForumFragment();
                forumFragment.setCourseid(courseid);
				return forumFragment;
			case 2:
				// Course Calendar
				CalenderFragment calenderFragment = new CalenderFragment();
				calenderFragment.setCourseid(courseid);
				return calenderFragment;
			case 3:
				// Course Participants
                ParticipantFragment participantFragment = new ParticipantFragment();
                participantFragment.setCourseid(courseid);
				return participantFragment;
			}
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TABS[position];
		}

		@Override
		public int getCount() {
			return TABS.length;
		}
	}

}