package id.ac.ui.cs.scele.mobile.fragment;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.activity.PostActivity;
import id.ac.ui.cs.scele.mobile.helper.AppInterface.ForumIdInterface;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.helper.TimeFormat;
import id.ac.ui.cs.scele.mobile.helper.Workaround;
import id.ac.ui.cs.scele.mobile.model.MoodleDiscussion;
import id.ac.ui.cs.scele.mobile.task.DiscussionSyncTask;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Any activity that includes this Fragment must implement ForumIdInterface
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class DiscussionFragment extends Fragment implements OnRefreshListener {
	private final String DEBUG_TAG = "DiscussionFragment";
	TopicListAdapter topicListAdapter;
	SessionSetting session;
	int forumid = 0;
	List<MoodleDiscussion> mTopics;
	LinearLayout topicsEmptyLayout;
	SwipeRefreshLayout swipeLayout;

	/**
	 * Don't use this constructor. We need a forumid. Any activity that includes
	 * this Fragment must implement ForumIdInterface
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 */
	public DiscussionFragment() {
	}

	/**
	 * Lists all discussions in a forum. Any activity that includes this
	 * Fragment must implement ForumIdInterface
	 *
	 * @param forumid
	 */
	public void setForumid(int forumid){
		this.forumid = forumid;
	}

	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		try {
			ForumIdInterface forumidInterface = (ForumIdInterface) a;
			this.forumid = forumidInterface.getForumId();
		} catch (ClassCastException e) {
			e.printStackTrace();
			Log.d(DEBUG_TAG, a.toString()
					+ " did not implement ForumIdInterface.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_discussion, container,
				false);
		topicsEmptyLayout = (LinearLayout) rootView
				.findViewById(R.id.discussion_empty_layout);

		if (forumid == 0)
			return rootView;

		session = new SessionSetting(getActivity());
		mTopics = MoodleDiscussion.find(MoodleDiscussion.class,
				"siteid = ? and forumid = ?", session.getCurrentSiteId() + "",
				forumid + "");
		Log.d("dims", "topicsize: "+mTopics.size());


		ListView topicList = (ListView) rootView
				.findViewById(R.id.content_discussion);
		topicListAdapter = new TopicListAdapter(getActivity());
		topicList.setAdapter(topicListAdapter);

		swipeLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_refresh);
		Workaround.linkSwipeRefreshAndListView(swipeLayout, topicList);
		swipeLayout.setOnRefreshListener(this);

		new AsyncTopicsSync(session.getmUrl(), session.getToken(),
				session.getCurrentSiteId()).execute("");
		return rootView;
	}

	public class TopicListAdapter extends BaseAdapter {
		private final Context context;

		public TopicListAdapter(Context context) {
			this.context = context;
			if (!mTopics.isEmpty())
				topicsEmptyLayout.setVisibility(LinearLayout.GONE);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = inflater.inflate(R.layout.list_item_discussion,
						parent, false);

				viewHolder.topicname = (TextView) convertView
						.findViewById(R.id.discussion_name);
				viewHolder.topicreplies = (TextView) convertView
						.findViewById(R.id.discussion_reply_count);
				viewHolder.topicstarttime = (TextView) convertView
						.findViewById(R.id.discussion_startedtime);
				viewHolder.topicstartby = (TextView) convertView
						.findViewById(R.id.discussion_startedby);
				viewHolder.topiclasttime = (TextView) convertView
						.findViewById(R.id.discussion_lastmodifiedtime);
				viewHolder.topiclastby = (TextView) convertView
						.findViewById(R.id.discussion_lastmodifiedby);
				viewHolder.topicauthorpic = (ImageView) convertView
						.findViewById(R.id.discussion_icon);

				// icon caranya gini urlnya https://scele-test.cs.ui.ac.id/webservice/pluginfile.php?file=/2878/user/icon/f1&token=43f44b56d08fe377c626c1a497cca3b6

				// Save the holder with the view
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// Assign values
			viewHolder.topicname.setText(mTopics.get(position).getName());
			viewHolder.topicreplies.setText(mTopics.get(position)
					.getNumreplies());
			viewHolder.topicstartby.setText(mTopics.get(position)
					.getFirstuserfullname());
			viewHolder.topiclastby.setText(mTopics.get(position)
					.getLastuserfullname());


			// Time values
			String timeString = TimeFormat.getNiceTime(mTopics.get(position)
					.getTimestart());
			if (timeString.contentEquals(""))
				viewHolder.topicstarttime.setText("Started by");
			else
				viewHolder.topicstarttime.setText(timeString);
			timeString = TimeFormat.getNiceTime(mTopics.get(position)
					.getTimemodified());
			if (timeString.contentEquals(""))
				viewHolder.topiclasttime.setText("Last replied");
			else
				viewHolder.topiclasttime.setText(timeString);

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, PostActivity.class);
					i.putExtra("discussionid", mTopics.get(position)
							.getDiscussionid());
					context.startActivity(i);
				}
			});

			return convertView;
		}

		@Override
		public int getCount() {
			return mTopics.size();
		}

		@Override
		public Object getItem(int position) {
			return mTopics.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public void notifyDataSetChanged() {
			if (!mTopics.isEmpty())
				topicsEmptyLayout.setVisibility(LinearLayout.GONE);
			super.notifyDataSetChanged();
		}
	}

	static class ViewHolder {
		TextView topicname;
		TextView topicreplies;
		TextView topicstarttime;
		TextView topicstartby;
		TextView topiclasttime;
		TextView topiclastby;
		ImageView topicauthorpic;
	}

	private class AsyncTopicsSync extends AsyncTask<String, Integer, Boolean> {
		Long siteid;
		DiscussionSyncTask dst;

		public AsyncTopicsSync(String mUrl, String token, Long siteid) {
			this.siteid = siteid;
			dst = new DiscussionSyncTask(mUrl, token, siteid);
		}

		@Override
		protected void onPreExecute() {
			swipeLayout.setRefreshing(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			return dst.syncDiscussions(forumid);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result)
				mTopics = MoodleDiscussion
						.find(MoodleDiscussion.class,
								"siteid = ? and forumid = ?", siteid + "",
								forumid + "");
			Log.d("dims", "forumid: "+forumid);

			topicListAdapter.notifyDataSetChanged();
			swipeLayout.setRefreshing(false);
		}
	}

	@Override
	public void onRefresh() {
		new AsyncTopicsSync(session.getmUrl(), session.getToken(),
				session.getCurrentSiteId()).execute("");
	}
}
