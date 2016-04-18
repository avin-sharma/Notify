package com.avinsharma.whatsnext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avin on 18-04-2016.
 */
public class GroupsAdapter extends ArrayAdapter<Groups> {

    Context context;
    ArrayList<Groups> groupsArrayList;


    public GroupsAdapter(Context context, ArrayList<Groups> groupsArrayList){
        super(context,R.layout.groups_listview, groupsArrayList);

        this.context = context;
        this.groupsArrayList = groupsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View rowview = inflater.inflate(R.layout.groups_listview, parent, false);

        TextView title = (TextView) rowview.findViewById(R.id.group_title);
        TextView description = (TextView) rowview.findViewById(R.id.group_description);

        title.setText(groupsArrayList.get(position).getTitle());
        description.setText(groupsArrayList.get(position).getDescription());

        return rowview;
    }
}
