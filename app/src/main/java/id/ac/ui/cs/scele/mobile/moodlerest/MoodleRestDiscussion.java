package id.ac.ui.cs.scele.mobile.moodlerest;

import id.ac.ui.cs.scele.mobile.helper.GsonExclude;
import id.ac.ui.cs.scele.mobile.model.MoodleDiscussion;
import id.ac.ui.cs.scele.mobile.model.MoodleDiscussions;

import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MoodleRestDiscussion {
	private final String DEBUG_TAG = "MoodleRestDiscussion";
	private String mUrl;
	private String token;

	public MoodleRestDiscussion(String mUrl, String token) {
		this.mUrl = mUrl;
		this.token = token;
	}

	/**
	 * Get all discussions for the list of forums.
	 * 
	 * @param forumids
	 *            List of forumids
	 * 
	 * @return ArrayList of MoodleDiscussion
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 */
	public ArrayList<MoodleDiscussion> getDiscussions(List<String> forumids) {
		MoodleDiscussions mDiscussions0 = null;
		ArrayList<MoodleDiscussion> mDiscussions = new ArrayList<>();
		String format = MoodleRestOption.RESPONSE_FORMAT;
		String function = MoodleRestOption.FUNCTION_GET_DISCUSSIONS;

		try {
			// Adding all parameters.
			String params = "";

			if (forumids == null)
				forumids = new ArrayList<>();

			for (int i = 0; i < forumids.size(); i++){
				params += "&forumid="
						+ URLEncoder.encode(forumids.get(i), "UTF-8");

				// Build a REST call url to make a call.
				String restUrl = mUrl + "/webservice/rest/server.php" + "?wstoken="
						+ token + "&wsfunction=" + function
						+ "&moodlewsrestformat=" + format;

				// Fetch content now.
				MoodleRestCall mrc = new MoodleRestCall();
				Reader reader = mrc.fetchContent(restUrl, params);
				GsonExclude ex = new GsonExclude();
				Gson gson = new GsonBuilder()
						.addDeserializationExclusionStrategy(ex)
						.addSerializationExclusionStrategy(ex).create();
				mDiscussions0 = gson.fromJson(reader,
						new TypeToken<MoodleDiscussions>() {
						}.getType());
				ArrayList<MoodleDiscussion> discussions =(ArrayList<MoodleDiscussion>) mDiscussions0.getDiscussions();
				for(int j = 0; j < discussions.size(); j++){
					MoodleDiscussion discussion = discussions.get(j);
					discussion.setForumid(Integer.parseInt(forumids.get(i)));
					mDiscussions.add(discussion);
				}

				// kan udah dapet diskusi2 dari suatu forum, terus bakal dimasukkin ke suatu variabel yang berisi diskusi2 yang ada dari forum2 yang ada semangat dimms

			}

		} catch (Exception e) {
			Log.d(DEBUG_TAG, "URL encoding failed");
			e.printStackTrace();
		}

		return mDiscussions;
	}

}
