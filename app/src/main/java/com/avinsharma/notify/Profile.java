package com.avinsharma.notify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.profile_notifications);
        NotificationsBottomAdapter adapter = new NotificationsBottomAdapter(getContext(), generateData());
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
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
    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
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
}
