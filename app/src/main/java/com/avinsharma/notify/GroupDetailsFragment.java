package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
                    //TODO: Add fragments for each case
                    case "assignment":
                        Fragment fragment = new assignmentFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.notification_container, new assignmentFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Log.v("GroupDetailFragment","Backstack :"+ getFragmentManager().getBackStackEntryCount());
                        break;
                    case "notification":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.notification_container, new notificationFragment())
                                .commit();
                        break;
                    case "test":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.notification_container, new testFragment())
                                .commit();
                        break;
                }
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));

        if (getActivity().getIntent().getStringExtra("Type").equals("Created")) {
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else if (getActivity().getIntent().getStringExtra("Type").equals("Followed")) {

            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            CoordinatorLayout col = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
            col.removeView(fab);

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
