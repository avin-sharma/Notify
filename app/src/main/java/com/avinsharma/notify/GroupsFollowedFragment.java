package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
public class GroupsFollowedFragment extends Fragment {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public GroupsFollowedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups_followed, container, false);
        ArrayList<Groups> groupsdata = new ArrayList<>();
        GroupsAdapter adapterTwo = new GroupsAdapter(view.getContext(), groupsdata);
        ListView groupsFollowed = (ListView) view.findViewById(R.id.listview_groups_following);
        groupsFollowed.setAdapter(adapterTwo);
        groupsFollowed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("Type","Followed");
                intent.putExtra(Intent.EXTRA_TEXT,((TextView)(view.findViewById(R.id.group_title))).getText().toString());
                startActivity(intent);
            }
        });

        groupsFollowed.setAdapter(generateData(adapterTwo));
        // Inflate the layout for this fragment
        return view;
    }

    private GroupsAdapter generateData(final GroupsAdapter adapter) {
        if(user != null) {
            String Uid = user.getUid();

            Query query = mDatabase.child("groups-followed").child(Uid);
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
