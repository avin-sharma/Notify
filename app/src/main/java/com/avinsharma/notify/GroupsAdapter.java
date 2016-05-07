package com.avinsharma.notify;

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
    static class ViewHolder{
        TextView title;
        TextView description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.groups_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.group_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.group_description);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Groups groups = getItem(position);
        viewHolder.title.setText(groups.getTitle());
        viewHolder.description.setText(groups.getDescription());

        return convertView;
    }
}