package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
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
public class GroupsCreatedFragment extends Fragment {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public GroupsCreatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_created, container, false);
        ArrayList<Groups> groupsdata = new ArrayList<>();
        GroupsAdapter adapter = new GroupsAdapter(view.getContext(), groupsdata);
        final ListView groupsCreated = (ListView) view.findViewById(R.id.listview_groups_created);
        groupsCreated.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class).putExtra(Intent.EXTRA_TEXT, ((TextView) (view.findViewById(R.id.group_title))).getText().toString());
                intent.putExtra("Type", "Created");
                Groups selectedGroup = (Groups) parent.getAdapter().getItem(position);
                Log.v("GroupCreatedFragment",selectedGroup.getKey());
                intent.putExtra("Key",selectedGroup.getKey());
                intent.putExtra("Description",selectedGroup.getDescription());
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.groups_created_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("GroupCreatedFragment","onclick!!");
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
            }
        });

        //adding items to listview
        groupsCreated.setAdapter(generateData(adapter));
        // Inflate the layout for this fragment
        return view;
    }

    private GroupsAdapter generateData(final GroupsAdapter adapter) {
        if(user != null) {
            String Uid = user.getUid();

            Query query = mDatabase.child("groups-created").child(Uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        //Log.v("GroupDetailsFragment", i.getKey());
                        mDatabase.child("groups").child(i.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Groups groups = dataSnapshot.getValue(Groups.class);
                                adapter.add(groups);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return adapter;

    }
}
