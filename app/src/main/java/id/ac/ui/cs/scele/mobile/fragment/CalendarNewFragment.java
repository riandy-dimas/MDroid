package id.ac.ui.cs.scele.mobile.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ac.ui.cs.scele.R;

/**
 * Created by USER on 14-Mar-17.
 */

public class CalendarNewFragment extends Fragment {

    public CalendarNewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_calendar, container, false);
        return view;
    }
}
