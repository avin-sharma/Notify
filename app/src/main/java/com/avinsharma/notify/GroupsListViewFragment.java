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
public class GroupsListViewFragment extends Fragment {


    public GroupsListViewFragment() {
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

        groupsCreated.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class).putExtra(Intent.EXTRA_TEXT,((TextView)(view.findViewById(R.id.group_title))).getText().toString());
                intent.putExtra("Type","Created");
                startActivity(intent);
            }
        });

        groupsFollowed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("Type","Followed");
                intent.putExtra(Intent.EXTRA_TEXT,((TextView)(view.findViewById(R.id.group_title))).getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }

    private ArrayList<Groups> generateData(){
        ArrayList<Groups> items = new ArrayList<Groups>();
        items.add(new Groups("Physics","First Item on the list",1));
        items.add(new Groups("Chemistry","Second Item on the list",1));
        items.add(new Groups("Maths","Third Item on the list",1));
        items.add(new Groups("Item 1","First Item on the list",1));
        items.add(new Groups("Item 2","Second Item on the list",1));
        items.add(new Groups("Item 3","Third Item on the list",1));
        items.add(new Groups("Item 1","First Item on the list",1));
        items.add(new Groups("Item 2","Second Item on the list",1));
        items.add(new Groups("Item 3","Third Item on the list",1));

        return items;
        }

    private ArrayList<Groups> generateDatatwo(){
        ArrayList<Groups> items = new ArrayList<Groups>();
        items.add(new Groups("Photography","First Item on the list",1));
        items.add(new Groups("Maths Tuition","Second Item on the list",1));
        items.add(new Groups("Yolo","Third Item on the list",1));
        items.add(new Groups("List 2 Item 1","First Item on the list",1));
        items.add(new Groups("List 2 Item 2","Second Item on the list",1));
        items.add(new Groups("List 2 Item 3","Third Item on the list",1));items.add(new Groups("List 2 Item 1","First Item on the list",1));
        items.add(new Groups("List 2 Item 2","Second Item on the list",1));
        items.add(new Groups("List 2 Item 3","Third Item on the list",1));

        return items;
    }
}
