package id.ac.ui.cs.scele.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.AppInterface;

/**
 * Created by USER on 14-Mar-17.
 */

public class MessagingNewFragment extends Fragment implements
        AppInterface.UserIdInterface, AppInterface.FragmentChanger {
    public static final int FRAG_MESSAGE_LIST = 1;
    public static final int FRAG_MESSAGING = 2;
    int userid;

    public MessagingNewFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_messaging, container, false);
        changeFragment(FRAG_MESSAGE_LIST, false);
        return view;
    }

    @Override
    public int getUserId() {
        return this.userid;
    }

    @Override
    public void setUserId(int userid) {
        this.userid = userid;
    }

    @Override
    public void changeFragment(int FragmentId, Boolean animations) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Enable animations if required
        if(animations)
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit,
                    R.anim.pop_enter, R.anim.pop_exit);

        switch(FragmentId){
            case FRAG_MESSAGE_LIST:
                transaction.replace(R.id.messaging_layout, new MessageListingFragment());
                break;
            case FRAG_MESSAGING:
                transaction.replace(R.id.messaging_layout, new MessagingFragment());
                break;
            default:
        }
        transaction.addToBackStack("null").commit();
    }
}
