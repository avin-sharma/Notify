package com.avinsharma.notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomNotificationFragment extends Fragment {


    public BottomNotificationFragment() {
        // Required empty public constructor
    }

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_notification, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.bottom_notifications);
        ArrayList<Notifications> items = new ArrayList<Notifications>();
        NotificationsBottomAdapter adapter = new NotificationsBottomAdapter(getContext(), items);
        listView.setAdapter(generateData(adapter));
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
                    case "invite":
                        Intent intent = new Intent(getActivity(),InviteDialogActivity.class);
                        intent.putExtra("Title",notification.getTitle());
                        intent.putExtra("Key",notification.getGroupId());
                        intent.putExtra("Description",notification.getDescription());
                        intent.putExtra("NKey",notification.getNotificationId());
                        startActivity(intent);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private NotificationsBottomAdapter generateData(final NotificationsBottomAdapter adapter) {

        mDatabase.child("invitations").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren()){
                    Notifications invite = i.getValue(Notifications.class);
                    adapter.add(invite);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return adapter;
    }

}
