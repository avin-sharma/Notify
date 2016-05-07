package com.avinsharma.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avin on 02-05-2016.
 */
public class NotificationsAdapter extends ArrayAdapter<Notifications>{
    Context context;
    ArrayList<Notifications> notificationsArrayList;

    public NotificationsAdapter(Context context, ArrayList<Notifications> notificationsArrayList){
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
        viewHolder.title.setText(notifications.getTitle());
        viewHolder.description.setText(notifications.getDescription());
        //TODO: Add notification icons (resource ids) blow
        switch (notifications.getType()){
            case "assignment": viewHolder.icon.setImageResource(R.drawable.assignment_1x);
                break;
            case "notification": viewHolder.icon.setImageResource(R.drawable.reminder_1x);
                break;
            case "test": viewHolder.icon.setImageResource(R.drawable.test_1x);
                break;
        }

        return convertView;
    }
}
