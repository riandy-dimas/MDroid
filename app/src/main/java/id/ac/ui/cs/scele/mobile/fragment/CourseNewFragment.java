package id.ac.ui.cs.scele.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.helper.Workaround;
import id.ac.ui.cs.scele.mobile.model.MoodleCourse;
import id.ac.ui.cs.scele.mobile.view.SlidingTabLayout;

/**
 * Created by USER on 14-Mar-17.
 */

public class CourseNewFragment extends Fragment {
    private ViewPager viewPager;
    private static final String[] TABS = { "My Courses", "Archived Courses" };

    public CourseNewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_course, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.course_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CourseTabsAdapter(getChildFragmentManager()));

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    class CourseTabsAdapter extends FragmentPagerAdapter {
        public CourseTabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
			/*
			 * We use bundle to pass course listing type because, by using other
			 * methods we will lose the listing type information in the fragment
			 * on onResume (this calls empty constructor). For the same reason
			 * interface may not work. Bundles are passed again on onResume
			 */
            switch (position) {
                case 0:
                    CourseFragment userCourses = new CourseFragment();

                    // Set the listing type to only user courses in bundle.
                    Bundle bundle = new Bundle();
                    bundle.putInt("coursesType", CourseFragment.TYPE_USER_COURSES);
                    userCourses.setArguments(bundle);

                    return userCourses;
                case 1:
                    CourseFragment favCourses = new CourseFragment();

                    // Set the listing type to only user courses in bundle.
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("coursesType", CourseFragment.TYPE_FAV_COURSES);
                    favCourses.setArguments(bundle1);

                    return favCourses;
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
