package com.avinsharma.notify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomNotificationFragment extends Fragment {


    public BottomNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_notification, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.bottom_notifications);
        NotificationsBottomAdapter adapter = new NotificationsBottomAdapter(getContext(), generateData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        // Inflate the layout for this fragment
        return view;
    }

    private ArrayList<Notifications> generateData() {
        ArrayList<Notifications> items = new ArrayList<Notifications>();
        items.add(new Notifications("Reminder!", "Testing reminder!!", "notification", "A"));
        items.add(new Notifications("Test!", "Testing test!!", "test","B"));
        items.add(new Notifications("Assignment!", "Testing assignment!!", "assignment", "C"));
        items.add(new Notifications("Reminder!", "Testing reminder!!", "notification", "D"));

        return items;
    }
}
