package id.ac.ui.cs.scele.mobile.activity;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.DonationInterface;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.DrawerStateInterface;
import id.ac.ui.cs.scele.mobile.helper.BottomNavigationViewHelper;
import id.ac.ui.cs.scele.mobile.helper.Param;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

/**
 * Extending this would implement a side navigation and billing capabilities
 * into a activity. <b>Requires:</b><br/>
 * 1. Drawerlayout in the activity xml layout<br/>
 * 2. Call to setUpDrawer() after inflating layout in onCreate()
 * 
 * @author Praveen Kumar Pendyala <praveen@praveenkumar.co.in>
 * 
 */
public abstract class BaseNavigationActivity extends ActionBarActivity
		implements DrawerStateInterface {
	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;
	BillingProcessor billing;

	// Title settings
	private CharSequence MenuTitle = "SCeLE";
	private CharSequence LastTitle = "SCeLE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setElevation(5);

		// Setup billing
//		billing = new BillingProcessor(this, Param.BILLING_LICENSE_KEY,
//				new BillingProcessor.IBillingHandler() {
//					@Override
//					public void onProductPurchased(String productId,
//							TransactionDetails details) {
//						Toast.makeText(getApplicationContext(),
//								R.string.donations_already_purchased,
//								Toast.LENGTH_LONG).show();
//					}
//
//					@Override
//					public void onBillingError(int errorCode, Throwable error) {
//						Toast.makeText(getApplicationContext(),
//								R.string.donations_purchase_failed,
//								Toast.LENGTH_LONG).show();
//					}
//
//					@Override
//					public void onBillingInitialized() {
//					}
//
//					@Override
//					public void onPurchaseHistoryRestored() {
//					}
//				});
	}

	public void setMainDrawer(){

	}

	public void setUpDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {

//                getSupportActionBar().setTitle(LastTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
//				LastTitle = getSupportActionBar().getTitle();
//				getSupportActionBar().setTitle(MenuTitle);
			}
		};
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Overriding menu key press to show left navigation menu. All other menu
	 * related functions like onPrepareOptionsMenuare, onCreateOptionsMenu are
	 * also called once when the Activity is created. So, we are taking this
	 * approach.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!mDrawerLayout.isDrawerOpen(GravityCompat.START))
				mDrawerLayout.openDrawer(Gravity.LEFT);
			else
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Sets the drawer state.
	 * 
	 * @param state
	 *            True: Open drawer if closed. False: Close drawer if open.
	 */
	@Override
	public void setDrawerState(Boolean state) {
		if (state)
			mDrawerLayout.openDrawer(Gravity.LEFT);
		else
			mDrawerLayout.closeDrawer(Gravity.LEFT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!billing.handleActivityResult(requestCode, resultCode, data))
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		if (billing != null)
			billing.release();
		super.onDestroy();
	}

}
