package in.co.praveenkumar.mdroid.fragment;

import in.co.praveenkumar.mdroid.apis.R;
import in.co.praveenkumar.mdroid.helper.SessionSetting;
import in.co.praveenkumar.mdroid.helper.TimeFormat;
import in.co.praveenkumar.mdroid.moodlemodel.MoodlePost;
import in.co.praveenkumar.mdroid.task.PostSyncTask;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PostFragment extends Fragment {
	PostListAdapter postListAdapter;
	SessionSetting session;
	int discussionid = 18;
	List<MoodlePost> mPosts;
	LinearLayout postsEmptyLayout;

	/**
	 * This constructor lists all forums in the site. Don't use this
	 * constructor.
	 */
	public PostFragment() {
	}

	/**
	 * If you want to list all forums, use courseid = 0
	 * 
	 * @param courseid
	 */
	public PostFragment(int discussionid) {
		this.discussionid = discussionid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_post, container,
				false);
		postsEmptyLayout = (LinearLayout) rootView
				.findViewById(R.id.post_empty_layout);

		if (discussionid == 0)
			return rootView;

		session = new SessionSetting(getActivity());
		mPosts = MoodlePost.find(MoodlePost.class,
				"siteid = ? and discussionid = ?", session.getCurrentSiteId()
						+ "", discussionid + "");

		ListView postList = (ListView) rootView.findViewById(R.id.content_post);
		postListAdapter = new PostListAdapter(getActivity());

		postList.setAdapter(postListAdapter);
		new AsyncPostsSync(session.getmUrl(), session.getToken(),
				session.getCurrentSiteId()).execute("");
		return rootView;
	}

	public class PostListAdapter extends BaseAdapter {
		private final Context context;

		public PostListAdapter(Context context) {
			this.context = context;
			if (mPosts.size() != 0)
				postsEmptyLayout.setVisibility(LinearLayout.GONE);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = inflater.inflate(R.layout.list_item_post,
						parent, false);

				viewHolder.postauthorimage = (TextView) convertView
						.findViewById(R.id.post_authorimage);
				viewHolder.postsubject = (TextView) convertView
						.findViewById(R.id.post_subject);
				viewHolder.postauthor = (TextView) convertView
						.findViewById(R.id.post_author);
				viewHolder.postlastmodified = (TextView) convertView
						.findViewById(R.id.post_lastmodifiedtime);
				viewHolder.postcontent = (TextView) convertView
						.findViewById(R.id.post_content);

				// Save the holder with the view
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// Assign values
			viewHolder.postsubject.setText(mPosts.get(position).getSubject());
			viewHolder.postauthor.setText(mPosts.get(position)
					.getUserfullname());
			viewHolder.postlastmodified.setText(TimeFormat.getNiceTime(mPosts
					.get(position).getModified()));
			viewHolder.postcontent.setText(mPosts.get(position).getMessage());

			// Author image color and value
			String authorLetter = mPosts.get(position).getUserfullname();
			if (authorLetter.length() != 0)
				authorLetter = authorLetter.charAt(0) + "";
			viewHolder.postauthorimage.setText(authorLetter);
			viewHolder.postauthorimage.setBackgroundColor(getResources()
					.getColor(R.color.beautiful_green));

			return convertView;
		}

		@Override
		public int getCount() {
			return mPosts.size();
		}

		@Override
		public Object getItem(int position) {
			return mPosts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	static class ViewHolder {
		TextView postauthorimage;
		TextView postsubject;
		TextView postauthor;
		TextView postlastmodified;
		TextView postcontent;
	}

	private class AsyncPostsSync extends AsyncTask<String, Integer, Boolean> {
		Long siteid;
		PostSyncTask pst;
		Boolean syncStatus;

		public AsyncPostsSync(String mUrl, String token, Long siteid) {
			this.siteid = siteid;
			pst = new PostSyncTask(mUrl, token, siteid);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			syncStatus = pst.syncPosts(discussionid);

			if (syncStatus) {
				mPosts = MoodlePost.find(MoodlePost.class,
						"siteid = ? and discussionid = ?", siteid + "",
						discussionid + "");
				return true;
			} else
				return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			postListAdapter.notifyDataSetChanged();
			if (mPosts.size() != 0)
				postsEmptyLayout.setVisibility(LinearLayout.GONE);
		}

	}
}