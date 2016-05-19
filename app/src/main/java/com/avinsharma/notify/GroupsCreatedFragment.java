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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsCreatedFragment extends Fragment {


    public GroupsCreatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_created, container, false);
        GroupsAdapter adapter = new GroupsAdapter(view.getContext(), generateData());
        ListView groupsCreated = (ListView) view.findViewById(R.id.listview_groups_created);
        groupsCreated.setAdapter(adapter);
        groupsCreated.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class).putExtra(Intent.EXTRA_TEXT, ((TextView) (view.findViewById(R.id.group_title))).getText().toString());
                intent.putExtra("Type", "Created");
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private ArrayList<Groups> generateData() {
        ArrayList<Groups> items = new ArrayList<Groups>();
        items.add(new Groups("Physics", "First Item on the list"));
        items.add(new Groups("Chemistry", "Second Item on the list"));
        items.add(new Groups("Maths", "Third Item on the list"));
        items.add(new Groups("Item 1", "First Item on the list"));
        items.add(new Groups("Item 2", "Second Item on the list"));
        items.add(new Groups("Item 3", "Third Item on the list"));
        items.add(new Groups("Item 1", "First Item on the list"));
        items.add(new Groups("Item 2", "Second Item on the list"));
        items.add(new Groups("Item 3", "Third Item on the list"));

        return items;
    }
}
