package com.avinsharma.notify;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class assignmentFragment extends Fragment {

    TextView title;
    TextView date;
    TextView description;

    public assignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        //Trial
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.d("assignmentFragment", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName()+"   Package Name :  "+componentInfo.getPackageName());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_assignment);

        if(taskInfo.get(0).topActivity.getClassName().equals("com.avinsharma.notify.MainActivity")) {

            Notifications notification =((MainActivity)getActivity()).getCurrentNotification();
            title =(TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            description =(TextView) view.findViewById(R.id.description);
            title.setText(notification.getTitle());
            description.setText(notification.getDescription());
            date.setText(notification.getDate());

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_assignment);
            linearLayout.removeView(toolbar);
        }
        else if(taskInfo.get(0).topActivity.getClassName().equals("com.avinsharma.notify.GroupsActivity")) {

            Notifications notification =((GroupsActivity)getActivity()).getCurrentNotification();

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            toolbar.setTitle("Assignment");
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            title =(TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            description =(TextView) view.findViewById(R.id.description);
            title.setText(notification.getTitle());
            description.setText(notification.getDescription());
            date.setText(notification.getDate());

            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));

            final Drawable upArrow = getResources().getDrawable();
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }
        // Inflate the layout for this fragment
        return view;
    }

}
