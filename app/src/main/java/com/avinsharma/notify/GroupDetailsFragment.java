package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment {


    public GroupDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_details, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        Log.v("GroupDetailFragment","Backstack :"+ getFragmentManager().getBackStackEntryCount());

        final ListView listView = (ListView) view.findViewById(R.id.listview_notifications);
        listView.setNestedScrollingEnabled(true);
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), generateData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (((Notifications) parent.getAdapter().getItem(position)).getType()) {
                    case "assignment":
                        Log.v("GDF","working");
                        getFragmentManager().beginTransaction()
                                .replace(R.id.notification_container, new assignmentFragment()).addToBackStack(null)
                                .commit();
                        break;
                    case "notification":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.notification_container, new notificationFragment()).addToBackStack(null)
                                .commit();
                        break;
                    case "test":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.notification_container, new testFragment()).addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));

        if (getActivity().getIntent().getStringExtra("Type").equals("Created")) {
            com.github.clans.fab.FloatingActionButton fab1=(com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab1);
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.notification_container, new createAssignmentFragment()).addToBackStack(null).commit();
                }
            });
            com.github.clans.fab.FloatingActionButton fab2=(com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.notification_container, new createTestFragment()).addToBackStack(null).commit();
                }
            });
            com.github.clans.fab.FloatingActionButton fab3=(com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab3);
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.notification_container, new createNotificationFragment()).addToBackStack(null).commit();
                }
            });
        } else if (getActivity().getIntent().getStringExtra("Type").equals("Followed")) {

            FloatingActionMenu fabm = (FloatingActionMenu) view.findViewById(R.id.fabm);
            CoordinatorLayout col = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
            col.removeView(fabm);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        return view;
    }

    private ArrayList<Notifications> generateData() {
        ArrayList<Notifications> items = new ArrayList<Notifications>();
        items.add(new Notifications("Reminder!", "Testing reminder!!", "notification"));
        items.add(new Notifications("Test!", "Testing test!!", "test"));
        items.add(new Notifications("Assignment!", "Testing assignment!!", "assignment"));
        items.add(new Notifications("Reminder!", "Testing reminder!!", "notification"));
        items.add(new Notifications("Test!", "Testing test!!", "test"));
        items.add(new Notifications("Assignment!", "Testing assignment!!", "assignment"));
        items.add(new Notifications("Reminder!", "Testing reminder!!", "notification"));
        items.add(new Notifications("Test!", "Testing test!!", "test"));
        items.add(new Notifications("Assignment!", "Testing assignment!!", "assignment"));

        return items;
    }
}
