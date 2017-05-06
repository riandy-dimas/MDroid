package id.ac.ui.cs.scele.mobile.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Date;
import java.util.List;

import id.ac.ui.cs.scele.R;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by USER on 14-Mar-17.
 */

public class CalendarNewFragment extends Fragment {

    CompactCalendarView calendarView;
    TextView calendarMonth;
    TextView textJadwal;
    SlidingUpPanelLayout slidingUpPanelLayout;

    public CalendarNewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_calendar, container, false);

        calendarView = (CompactCalendarView) view.findViewById(R.id.calendar_view);
        calendarMonth = (TextView) view.findViewById(R.id.calender_month);
        slidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        textJadwal = (TextView) view.findViewById(R.id.text_jadwal);


        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState.name().equals("EXPANDED")){
                    slidingUpPanelLayout.setEnabled(true);
                    slidingUpPanelLayout.setTouchEnabled(false);
                    textJadwal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }
                    });
                } else if(newState.name().equals("COLLAPSED")) {
                    slidingUpPanelLayout.setTouchEnabled(true);
                    textJadwal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        }
                    });
                }
            }
        });


        Event event1 = new Event(Color.GRAY, 1493981969000L, "Some extra data that I want to store.");
        Event event2 = new Event(Color.GRAY, 1493981969000L, "yeyd");
        calendarView.addEvent(event1,false);
        calendarView.addEvent(event2,false);
        calendarMonth.setText(DateFormat.format("MMMM",calendarView.getFirstDayOfCurrentMonth()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendarMonth.setText(DateFormat.format("MMMM",calendarView.getFirstDayOfCurrentMonth()));
            }
        });

        return view;
    }


}
