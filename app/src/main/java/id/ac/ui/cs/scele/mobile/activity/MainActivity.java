package id.ac.ui.cs.scele.mobile.activity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.dialog.RateDialog;
import id.ac.ui.cs.scele.mobile.fragment.CalendarNewFragment;
import id.ac.ui.cs.scele.mobile.fragment.CalenderFragment;
import id.ac.ui.cs.scele.mobile.fragment.CourseFragment;
import id.ac.ui.cs.scele.mobile.fragment.CourseNewFragment;
import id.ac.ui.cs.scele.mobile.fragment.ForumFragment;
import id.ac.ui.cs.scele.mobile.fragment.MessagingFragment;
import id.ac.ui.cs.scele.mobile.fragment.MessagingNewFragment;
import id.ac.ui.cs.scele.mobile.fragment.NotificationFragment;
import id.ac.ui.cs.scele.mobile.helper.ApplicationClass;
import id.ac.ui.cs.scele.mobile.helper.BottomNavigationViewHelper;
import id.ac.ui.cs.scele.mobile.helper.Param;
import id.ac.ui.cs.scele.mobile.view.SlidingTabLayout;

/**
 * Created by USER on 14-Mar-17.
 */

public class MainActivity extends BaseNavigationActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setUpDrawer();
        setUpBotNav();

        fragmentManager = getSupportFragmentManager();
        fragment = new CourseNewFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment).commit();

        getSupportActionBar().setTitle("Scele Fasilkom UI");

    }

    public void setUpBotNav(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_dashboard:
                            Toast.makeText(getBaseContext(), "COURSE",
                                    Toast.LENGTH_LONG).show();
                            fragment = new CourseNewFragment();
                            break;
                        case R.id.action_calendar:
                            Toast.makeText(getBaseContext(), "KALENDER",
                                    Toast.LENGTH_LONG).show();
                            fragment = new CalendarNewFragment();
                            break;
                        case R.id.action_messages:
                            Toast.makeText(getBaseContext(), "PESAN",
                                    Toast.LENGTH_LONG).show();
                            fragment = new MessagingNewFragment();
                            break;
                        case R.id.action_forum:
                            Toast.makeText(getBaseContext(), "FORUM",
                                    Toast.LENGTH_LONG).show();
                            fragment = new ForumFragment();
                            break;
                        case R.id.action_more:
                            Toast.makeText(getBaseContext(), "NOTIF",
                                    Toast.LENGTH_LONG).show();
                            fragment = new NotificationFragment();
                            break;
                    }
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                    return true;
                }
            });
    }
}
