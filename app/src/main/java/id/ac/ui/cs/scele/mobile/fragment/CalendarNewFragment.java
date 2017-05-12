package id.ac.ui.cs.scele.mobile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Date;
import java.util.List;

import id.ac.ui.cs.scele.R;
import id.ac.ui.cs.scele.mobile.helper.SessionSetting;
import id.ac.ui.cs.scele.mobile.model.MoodleEvent;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by USER on 14-Mar-17.
 */

public class CalendarNewFragment extends Fragment {
    Context context;
    SessionSetting session;


    CompactCalendarView calendarView;
    TextView calendarMonth;
    TextView textJadwal;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout dragView;
    List<MoodleEvent> mEvents;


    public CalendarNewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity();


        View view = inflater.inflate(R.layout.frag_new_calendar, container, false);

        calendarView = (CompactCalendarView) view.findViewById(R.id.calendar_view);
        calendarMonth = (TextView) view.findViewById(R.id.calender_month);
        slidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        textJadwal = (TextView) view.findViewById(R.id.text_jadwal);
        dragView = (LinearLayout) view.findViewById(R.id.dragView);


        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState.name().equals("EXPANDED")){
                    slidingUpPanelLayout.setTouchEnabled(false);
                    dragView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        }
                    });
                } else {
                    slidingUpPanelLayout.setTouchEnabled(true);
                    dragView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        }
                    });
                }
            }
        });

        session = new SessionSetting(context);

        mEvents = MoodleEvent.find(MoodleEvent.class, "siteid = ?",
                String.valueOf(session.getCurrentSiteId()));

        for(MoodleEvent mEvent: mEvents){
            Event event = new Event(getResources().getColor(R.color.section_title_color), Long.parseLong(mEvent.getTimestart()+"000"), mEvent.getDescription());
            Log.d("calendaradd", mEvent.getCoursename()+"");
            calendarView.addEvent(event,false);
        }

//        Event event1 = new Event(Color.GRAY, 1493981969000L, "Some extra data that I want to store.");
//        Event event2 = new Event(Color.GRAY, 1493981969000L, "yeyd");
//        calendarView.addEvent(event1,false);
//        calendarView.addEvent(event2,false);
        calendarMonth.setText(DateFormat.format("MMMM",calendarView.getFirstDayOfCurrentMonth()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendarView.getEvents(dateClicked);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                if(events.size()>0)
                    Toast.makeText(context, events.size()+" event(s)", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                calendarMonth.setText(DateFormat.format("MMMM",calendarView.getFirstDayOfCurrentMonth()));
            }
        });

        return view;
    }


}
