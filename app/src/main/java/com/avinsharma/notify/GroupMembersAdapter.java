package com.avinsharma.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avin on 16-06-2016.
 */
public class GroupMembersAdapter extends ArrayAdapter<User> {

    Context context;
    ArrayList<User> membersArrayList;

    public GroupMembersAdapter(Context context, ArrayList<User> membersArrayList){
        super(context,R.layout.group_members_listview,membersArrayList);

        this.context = context;
        this.membersArrayList = membersArrayList;
    }
    public static class ViewHolder{
        TextView userName;
        TextView email;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.group_members_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.member_user_name);
            viewHolder.email = (TextView) convertView.findViewById(R.id.member_email);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        viewHolder.userName.setText(user.getUsername());
        viewHolder.email.setText(user.getEmail());
        return convertView;
    }
}
