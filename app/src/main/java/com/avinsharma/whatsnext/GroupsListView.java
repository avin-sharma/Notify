package com.avinsharma.whatsnext;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsListView extends Fragment {


    public GroupsListView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        GroupsAdapter adapter = new GroupsAdapter(view.getContext(), generateData());
        GroupsAdapter adapterTwo = new GroupsAdapter(view.getContext(), generateDatatwo());
        ListView groupsCreated = (ListView) view.findViewById(R.id.listview_groups_created);
        ListView groupsFollowed = (ListView) view.findViewById(R.id.listview_groups_following);

        groupsFollowed.setAdapter(adapterTwo);
        groupsCreated.setAdapter(adapter);

        return view;
    }

    private ArrayList<Groups> generateData(){
        ArrayList<Groups> items = new ArrayList<Groups>();
        items.add(new Groups("Item 1","First Item on the list"));
        items.add(new Groups("Item 2","Second Item on the list"));
        items.add(new Groups("Item 3","Third Item on the list"));
        items.add(new Groups("Item 1","First Item on the list"));
        items.add(new Groups("Item 2","Second Item on the list"));
        items.add(new Groups("Item 3","Third Item on the list"));
        items.add(new Groups("Item 1","First Item on the list"));
        items.add(new Groups("Item 2","Second Item on the list"));
        items.add(new Groups("Item 3","Third Item on the list"));

        return items;
        }

    private ArrayList<Groups> generateDatatwo(){
        ArrayList<Groups> items = new ArrayList<Groups>();
        items.add(new Groups("List 2 Item 1","First Item on the list"));
        items.add(new Groups("List 2 Item 2","Second Item on the list"));
        items.add(new Groups("List 2 Item 3","Third Item on the list"));
        items.add(new Groups("List 2 Item 1","First Item on the list"));
        items.add(new Groups("List 2 Item 2","Second Item on the list"));
        items.add(new Groups("List 2 Item 3","Third Item on the list"));items.add(new Groups("List 2 Item 1","First Item on the list"));
        items.add(new Groups("List 2 Item 2","Second Item on the list"));
        items.add(new Groups("List 2 Item 3","Third Item on the list"));

        return items;
    }
}
