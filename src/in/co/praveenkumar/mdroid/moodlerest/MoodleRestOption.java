package in.co.praveenkumar.mdroid.moodlerest;

public class MoodleRestOption {
	// Supported web services
	public static final String SERVICE_MOODLE_MOBILE = "moodle_mobile";
	public static final String SERVICE_MOODY = "moody_service";
	public static final String SERVICE_MDROID = "mdroid_service";

	public static final String RESPONSE_FORMAT = "json";

	// Function names for getting contents
	public static final String FUNCTION_GET_ALL_COURSES = "moodle_course_get_courses";
	public static final String FUNCTION_GET_ENROLLED_COURSES = "core_enrol_get_users_courses";
	public static final String FUNCTION_GET_COURSE_CONTENTS = "core_course_get_contents";
	public static final String FUNCTION_GET_FORUMS = "mod_forum_get_forums_by_courses";
	public static final String FUNCTION_GET_DISCUSSIONS = "mod_forum_get_forum_discussions";
	public static final String FUNCTION_GET_SITE_INFO = "core_webservice_get_site_info";
	public static final String FUNCTION_GET_CONTACTS = "core_message_get_contacts";
}
