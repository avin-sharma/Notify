package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
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

//TODO: Correct the add member button
public class GroupMembersFragment extends Fragment {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public GroupMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_members, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_group_members);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final ListView listView =(ListView) view.findViewById(R.id.listview_group_members);
        listView.setNestedScrollingEnabled(true);
        ArrayList<User> users = new ArrayList<>();
        GroupMembersAdapter adapter = new GroupMembersAdapter(getContext(),users);
        listView.setAdapter(generateData(adapter));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout_group_members);
        collapsingToolbarLayout.setTitle(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));

        Button addMemberButton = (Button) view.findViewById(R.id.add_member_button);

        if (getActivity().getIntent().getStringExtra("Type").equals("Created")) {

            addMemberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddDialog.class);
                    intent.putExtra(Intent.EXTRA_TEXT,getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));
                    intent.putExtra("Key",getActivity().getIntent().getStringExtra("Key"));
                    intent.putExtra("Description",getActivity().getIntent().getStringExtra("Description"));
                    startActivity(intent);
                }
            });

        } else if (getActivity().getIntent().getStringExtra("Type").equals("Followed")) {

            CoordinatorLayout col = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
            col.removeView(addMemberButton);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    private GroupMembersAdapter generateData(final GroupMembersAdapter adapter){

        Query query = mDatabase.child("group-members").child(getActivity().getIntent().getStringExtra("Key"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.getChildren()){
                    mDatabase.child("users").child(i.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            user.email = keyToEmail(user.getEmail());
                            adapter.add(user);
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
        return adapter;
    }

    private String keyToEmail(String key){
        return key.replace('!','.');
    }


}
