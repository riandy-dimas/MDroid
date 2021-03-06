package id.ac.ui.cs.scele.mobile.fragment;

import de.hdodenhof.circleimageview.CircleImageView;
import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.activity.CalendarActivity;
import id.ac.ui.cs.scele.mobile.activity.ContactActivity;
import id.ac.ui.cs.scele.mobile.activity.CourseActivity;
import id.ac.ui.cs.scele.mobile.activity.DonationActivity;
import id.ac.ui.cs.scele.mobile.activity.ForumActivity;
import id.ac.ui.cs.scele.mobile.activity.LoginActivity;
import id.ac.ui.cs.scele.mobile.activity.MessagingActivity;
import id.ac.ui.cs.scele.mobile.activity.NotificationActivity;
import id.ac.ui.cs.scele.mobile.activity.SettingsActivity;
import id.ac.ui.cs.scele.mobile.dialog.LogoutDialog;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.DrawerStateInterface;
import id.ac.ui.cs.scele.mobile.helper.ImageDecoder;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleSiteInfo;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("InlinedApi")
public class LeftNavigationFragment extends Fragment {
	DrawerStateInterface drawerState;
	Context context;
	final String DEBUG_TAG = "Left Navigation Fragment";
	ListView navListView;
	List<MoodleSiteInfo> sites;
	SessionSetting session;

	String[] moodleMenuItems = new String[] {
            "Setting",
     //       "Messaging",
	//		"Contacts",
     //       "Calender",
     //       "Forums",
     //       "Notifications"
	};
	String[] appMenuItems = new String[] { "Logout" };

	int[] moodleMenuIcons = new int[] {
            R.drawable.ic_settings_black_24dp,
	//		R.drawable.icon_message_greyscale,
	//		R.drawable.icon_people_greyscale2,
	//		R.drawable.icon_today_greyscale,
	//		R.drawable.icon_forum_greyscale,
	//		R.drawable.icon_notifications_greyscale
	};
	int[] appMenuIcons = new int[] { R.drawable.icon_extension_greyscale,
			R.drawable.icon_settings_greyscale, R.drawable.icon_plus_greyscale };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_left_navigation,
				container, false);
		navListView = (ListView) rootView.findViewById(R.id.left_nav_list);
		this.context = getActivity();

		// Get sites info
		session = new SessionSetting(getActivity());
		sites = MoodleSiteInfo.listAll(MoodleSiteInfo.class);

		final LeftNavListAdapter adapter = new LeftNavListAdapter(getActivity());
		navListView.setAdapter(adapter);

		navListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                boolean nextDrawerState = false;
				switch (adapter.getItemViewType(position)) {
				case LeftNavListAdapter.TYPE_ACCOUNT:
                    nextDrawerState = true;
					break;
				case LeftNavListAdapter.TYPE_MOODLE_MENUITEM:
					switch (position - sites.size() - 1) {
					case 0:
						context.startActivity(new Intent(context,
								SettingsActivity.class));
						break;
//					case 1:
//						context.startActivity(new Intent(context,
//								MessagingActivity.class));
//						break;
					case 1:
						context.startActivity(new Intent(context,
								ContactActivity.class));
						break;
//					case 3:
//						context.startActivity(new Intent(context,
//								CalendarActivity.class));
//						break;
//					case 4:
//						context.startActivity(new Intent(context,
//								ForumActivity.class));
//						break;
//					case 5:
//						// NOTIFICATIONS HERE
//						context.startActivity(new Intent(context,
//								NotificationActivity.class));
//						break;
					}
					break;
				case LeftNavListAdapter.TYPE_APP_MENUITEM:
					switch (position - sites.size() - moodleMenuItems.length
							- 2) {
					case 0:
						LogoutDialog lod = new LogoutDialog(getContext(),
								session.getCurrentSiteId());
						lod.show();
						break;
					case 1:
						context.startActivity(new Intent(context,
								SettingsActivity.class));
						break;
					case 2:
						Intent j = new Intent(context, LoginActivity.class);
						j.putExtra("explicitCall", true);
						context.startActivity(j);
						break;
					}
					break;
				}
				drawerState.setDrawerState(nextDrawerState);
			}
		});

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			drawerState = (DrawerStateInterface) activity;
		} catch (ClassCastException castException) {
			castException.printStackTrace();
		}
	}

	public class LeftNavListAdapter extends BaseAdapter {
		private static final int TYPE_ACCOUNT = 0;
		private static final int TYPE_MOODLE_MENUITEM = 1;
		private static final int TYPE_APP_MENUITEM = 2;
		private static final int TYPE_SEPERATOR = 3;
		private static final int TYPE_COUNT = 4;

		private final Context context;

		public LeftNavListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getViewTypeCount() {
			return TYPE_COUNT;
		}

		@Override
		public int getItemViewType(int position) {
			if (position == sites.size()
					|| position == sites.size() + moodleMenuItems.length + 1)
				return TYPE_SEPERATOR;
			if (position >= sites.size() + moodleMenuItems.length + 2)
				return TYPE_APP_MENUITEM;
			if (position >= sites.size() + 1)
				return TYPE_MOODLE_MENUITEM;
			return TYPE_ACCOUNT;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			int type = getItemViewType(position);

			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				// Choose layout
				switch (type) {
				case TYPE_ACCOUNT:
					convertView = inflater.inflate(R.layout.list_item_account,
							parent, false);

					viewHolder.userfullname = (TextView) convertView
							.findViewById(R.id.nav_user_fullname);
					viewHolder.sitename = (TextView) convertView
							.findViewById(R.id.nav_sitename);
					viewHolder.userimage = (CircleImageView) convertView
							.findViewById(R.id.nav_user_image);
					viewHolder.userselected = (ImageView) convertView
							.findViewById(R.id.nav_user_selected);
					break;

				case TYPE_MOODLE_MENUITEM:
					convertView = inflater.inflate(
							R.layout.list_item_moodle_menu, parent, false);

					viewHolder.menuItemName = (TextView) convertView
							.findViewById(R.id.nav_menuitem);
					viewHolder.menuItemIcon = (ImageView) convertView
							.findViewById(R.id.nav_menuicon);
					break;
				case TYPE_APP_MENUITEM:
					convertView = inflater.inflate(R.layout.list_item_app_menu,
							parent, false);

					viewHolder.menuItemName = (TextView) convertView
							.findViewById(R.id.nav_menuitem);
					viewHolder.menuItemIcon = (ImageView) convertView
							.findViewById(R.id.nav_menuicon);
					break;
				case TYPE_SEPERATOR:
					convertView = inflater.inflate(
							R.layout.list_item_menu_seperator, parent, false);
					break;
				}
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// Assign values
			switch (type) {
			case TYPE_ACCOUNT:
				viewHolder.userfullname.setText(sites.get(position)
						.getFullname());
				String npm = sites.get(position).getFirstname().substring(sites.get(position).getFirstname().length()-10);
				viewHolder.sitename.setText(npm);
				Bitmap userImage = ImageDecoder.decodeImage(new File(
						Environment.getExternalStorageDirectory() + "/MDroid/."
								+ sites.get(position).getId()));


				if (userImage != null)
					viewHolder.userimage.setImageBitmap(userImage);

				// Show this as current account if it is
//				if (session.getCurrentSiteId() == sites.get(position).getId())
//					viewHolder.userselected.setVisibility(ImageView.VISIBLE);
//				else
//					viewHolder.userselected.setVisibility(ImageView.GONE);
				break;

			case TYPE_MOODLE_MENUITEM:
				viewHolder.menuItemName.setText(moodleMenuItems[position
						- sites.size() - 1]);
				viewHolder.menuItemIcon
						.setImageResource(moodleMenuIcons[position
								- sites.size() - 1]);
				break;
			case TYPE_APP_MENUITEM:
				viewHolder.menuItemName.setText(appMenuItems[position
						- sites.size() - moodleMenuItems.length - 2]);
				// viewHolder.menuItemIcon.setImageResource(appMenuIcons[position
				// - sites.size() - moodleMenuItems.length - 2]);
				break;
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return sites.size() + moodleMenuItems.length + appMenuItems.length
					+ 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	static class ViewHolder {
		TextView userfullname;
		TextView sitename;
		ImageView userselected;
		ImageView userimage;
		TextView menuItemName;
		ImageView menuItemIcon;
	}

}
