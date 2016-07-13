package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public GroupDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_details, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        TextView groupMembers = (TextView) view.findViewById(R.id.group_members);
        groupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.notification_container, new GroupMembersFragment()).addToBackStack(null).commit();
            }
        });

        final ListView listView = (ListView) view.findViewById(R.id.listview_notifications);
        listView.setNestedScrollingEnabled(true);
        ArrayList<Notifications> items = new ArrayList<>();
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), items);
        listView.setAdapter(generateAdapter(adapter));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notifications notification =(Notifications) listView.getAdapter().getItem(position);
                ((GroupsActivity)getActivity()).setCurrentNotification(notification);

                switch (((Notifications) parent.getAdapter().getItem(position)).getType()) {
                    case "assignment":
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

    private NotificationsAdapter generateAdapter(final NotificationsAdapter adapter) {
        String groupId = getActivity().getIntent().getStringExtra("Key");

        Query query = mDatabase.child("notifications").child(groupId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notifications notification;
                for (DataSnapshot i :dataSnapshot.getChildren()){
                    notification = i.getValue(Notifications.class);
                    adapter.add(notification);
                    Log.v("GroupDetails",notification.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return adapter;
    }
}
