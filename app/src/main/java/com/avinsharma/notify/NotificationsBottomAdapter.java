package com.avinsharma.notify;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsBottomAdapter extends ArrayAdapter<Notifications> {


    Context context;
    ArrayList<Notifications> notificationsArrayList;

    public NotificationsBottomAdapter(Context context, ArrayList<Notifications> notificationsArrayList){
        super(context,R.layout.notification_listview, notificationsArrayList);

        this.context = context;
        this.notificationsArrayList = notificationsArrayList;
    }

    static class ViewHolder{
        TextView title;
        TextView description;
        ImageView icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.notification_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.notification_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.notification_description);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.notification_icon);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Notifications notifications = getItem(position);
        viewHolder.title.setText(notifications.getGroupName());
        switch (notifications.getType()){
            case "assignment": viewHolder.icon.setImageResource(R.drawable.assignment_1x);
                String string = "Assignment: "+notifications.getTitle();
                viewHolder.description.setText(string);
                break;
            case "notification": viewHolder.icon.setImageResource(R.drawable.reminder_1x);
                string = "Notification: "+notifications.getTitle();
                viewHolder.description.setText(string);
                break;
            case "test": viewHolder.icon.setImageResource(R.drawable.test_1x);
                string = "Test: "+notifications.getTitle();
                viewHolder.description.setText(string);
                break;
            case "invite":viewHolder.icon.setImageResource(R.drawable.com_facebook_button_like_icon_selected);
                string = "Invite from " +notifications.getGroupName();
                viewHolder.title.setText(string);
                viewHolder.description.setText(notifications.getDescription());
                break;
            //TODO: add case for invite
        }

        return convertView;
    }


}
