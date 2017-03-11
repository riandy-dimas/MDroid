package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.fragment.NormalLoginFragment;
import id.ac.ui.cs.scele.mobile.fragment.ParanoidLoginFragment;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.Param;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LoginActivity extends FragmentActivity {
	LoginFragmentAdapter mAdapter;
	ViewPager mPager;
	private String[] tabs = { "Normal", "Paranoid" };

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Send a tracker
		((ApplicationClass) getApplication()).sendScreen(Param.GA_SCREEN_LOGIN);
		mAdapter = new LoginFragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(2);
	}

	class LoginFragmentAdapter extends FragmentPagerAdapter {

		public LoginFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0)
				return new NormalLoginFragment();
			else
				return new ParanoidLoginFragment();
		}

		@Override
		public int getCount() {
			return tabs.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabs[position];
		}
	}

	public void changePage(View v) {
		if (mPager.getCurrentItem() == 0)
			mPager.setCurrentItem(1);
		else
			mPager.setCurrentItem(0);
	}

}
