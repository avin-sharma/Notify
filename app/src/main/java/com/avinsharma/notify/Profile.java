package com.avinsharma.notify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    TextView userName;
    TextView emailId;
    int q;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        q=0;
        listView = (ListView) view.findViewById(R.id.profile_notifications);
        final ArrayList<Notifications> items = new ArrayList<>();
        NotificationsBottomAdapter adapter;
        adapter = new NotificationsBottomAdapter(getContext(), items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notifications notification = (Notifications) listView.getAdapter().getItem(position);
                ((MainActivity) getActivity()).setCurrentNotification(notification);
                switch (((Notifications) parent.getAdapter().getItem(position)).getType()) {
                    case "assignment":
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new assignmentFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case "notification":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new notificationFragment()).addToBackStack(null)
                                .commit();
                        break;
                    case "test":
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new testFragment()).addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });

        final CircleImageView profilePic = (CircleImageView) view.findViewById(R.id.profile_pic);
        Picasso.with(getActivity()).load(((MainActivity) getActivity()).getProfilePicUrl()).into(profilePic);

        userName = (TextView) view.findViewById(R.id.username);
        mDatabase.child("users").child(user.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        emailId = (TextView) view.findViewById(R.id.email_id);
        mDatabase.child("users").child(user.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailId.setText(keyToEmail(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(generateData(adapter));
        listView.setNestedScrollingEnabled(true);

        return view;
    }
//TODO: Make this work
    private NotificationsBottomAdapter generateData(final NotificationsBottomAdapter adapter) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String Uid = user.getUid();

            Query query = mDatabase.child("groups-created").child(Uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        Log.v("Profile", i.getKey());
                        mDatabase.child("notifications").child(i.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot i : dataSnapshot.getChildren()) {
                                    final Notifications notification = i.getValue(Notifications.class);
                                    Log.v("Profile", notification.getTitle() + " " + notification.getGroupName() + " This should be first");
                                    adapter.add(notification);
                                    q++;
                                }
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
        Log.v("Profile", "This should be second");
        return adapter;
    }

    /****
     * Method for Setting the Height of the ListView dynamically.
     * *** Hack to fix the issue of not showing all the items of the ListView
     * *** when placed inside a ScrollView
     ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);


    }

    private String keyToEmail(String key){
        return key.replace('!','.');
    }

}
