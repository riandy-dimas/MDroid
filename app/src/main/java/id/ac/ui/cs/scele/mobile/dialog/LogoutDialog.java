package id.ac.ui.cs.scele.mobile.dialog;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.activity.CourseActivity;
import id.ac.ui.cs.scele.mobile.activity.LoginActivity;
import id.ac.ui.cs.scele.mobile.helper.ImageDecoder;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleContact;
import id.ac.ui.cs.scele.mobile.model.MoodleCourse;
import id.ac.ui.cs.scele.mobile.model.MoodleDiscussion;
import id.ac.ui.cs.scele.mobile.model.MoodleEvent;
import id.ac.ui.cs.scele.mobile.model.MoodleForum;
import id.ac.ui.cs.scele.mobile.model.MoodleMessage;
import id.ac.ui.cs.scele.mobile.model.MoodleModule;
import id.ac.ui.cs.scele.mobile.model.MoodleModuleContent;
import id.ac.ui.cs.scele.mobile.model.MoodlePost;
import id.ac.ui.cs.scele.mobile.model.MoodleSection;
import id.ac.ui.cs.scele.mobile.model.MoodleSiteInfo;
import id.ac.ui.cs.scele.mobile.model.MoodleUser;
import id.ac.ui.cs.scele.mobile.model.MoodleUserCourse;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LogoutDialog extends Dialog implements
		android.view.View.OnClickListener {
	Context context;
	MoodleSiteInfo siteinfo;

	public LogoutDialog(Context context, long siteid) {
		super(context);
		this.context = context;
		siteinfo = MoodleSiteInfo.findById(MoodleSiteInfo.class, siteid);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_logout);

		// Get account info for dialog
		TextView userfullname = (TextView) findViewById(R.id.dialog_logout_user_fullname);
		TextView sitename = (TextView) findViewById(R.id.dialog_logout_sitename);
		ImageView userimage = (ImageView) findViewById(R.id.dialog_logout_user_image);
		Button confirmbutton = (Button) findViewById(R.id.dialog_logout_confirm);
		Button cancelbutton = (Button) findViewById(R.id.dialog_logout_cancel);

		// Set values
		userfullname.setText(siteinfo.getFullname());
		String npm = siteinfo.getFirstname().substring(siteinfo.getFirstname().length()-10);
		sitename.setText(npm);
		Bitmap userImage = ImageDecoder
				.decodeImage(new File(Environment.getExternalStorageDirectory()
						+ "/MDroid/." + siteinfo.getId()));
		if (userImage != null)
			userimage.setImageBitmap(userImage);
		confirmbutton.setOnClickListener(this);
		cancelbutton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_logout_confirm:
			performLogout(siteinfo.getId());
			break;
		case R.id.dialog_logout_cancel:
			break;
		}
		dismiss();
	}

	public void performLogout(long siteid) {
		// Delete siteinfo
		MoodleSiteInfo.deleteAll(MoodleSiteInfo.class, "id = ?", String.valueOf(siteid));

		// set a new site from db as current site. The below call will pick a
		// new site from db as current site
		SessionSetting session = new SessionSetting(context);

		// Now delete all other info related to that site
		MoodleContact.deleteAll(MoodleContact.class, "siteid = ?", String.valueOf(siteid));
		MoodleCourse.deleteAll(MoodleCourse.class, "siteid = ?", String.valueOf(siteid));
		MoodleDiscussion.deleteAll(MoodleDiscussion.class, "siteid = ?", String.valueOf(siteid));
		MoodleEvent.deleteAll(MoodleEvent.class, "siteid = ?", String.valueOf(siteid));
		MoodleForum.deleteAll(MoodleForum.class, "siteid = ?", String.valueOf(siteid));
		MoodleMessage.deleteAll(MoodleMessage.class, "siteid = ?", String.valueOf(siteid));
		MoodleModule.deleteAll(MoodleModule.class, "siteid = ?", String.valueOf(siteid));
		MoodleModuleContent.deleteAll(MoodleModuleContent.class, "siteid = ?",
				String.valueOf(siteid));
		MoodlePost.deleteAll(MoodlePost.class, "siteid = ?", String.valueOf(siteid));
		MoodleSection.deleteAll(MoodleSection.class, "siteid = ?", String.valueOf(siteid));
		MoodleUser.deleteAll(MoodleUser.class, "siteid = ?", String.valueOf(siteid));
		MoodleUserCourse.deleteAll(MoodleUserCourse.class, "siteid = ?", String.valueOf(siteid));

		Intent i;
		if (session.getCurrentSiteId() == SessionSetting.NO_SITE_ID)
			i = new Intent(context, LoginActivity.class); // No more sites in db
		else
			i = new Intent(context, CourseActivity.class); // New site from db

		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity(i);
	}

}
