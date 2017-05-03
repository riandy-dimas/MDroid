package id.ac.ui.cs.scele.mobile.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by USER on 02-May-17.
 */

public class MoodleDiscussions extends SugarRecord<MoodleDiscussions> {

    @SerializedName("discussions")
    List<MoodleDiscussion> discussions;

    public List<MoodleDiscussion> getDiscussions() {return discussions;}
}
