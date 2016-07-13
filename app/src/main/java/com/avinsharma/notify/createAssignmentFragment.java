package com.avinsharma.notify;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class createAssignmentFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    View copy;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public createAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_assignment, container, false);
        copy = view;
        final String groupId = getActivity().getIntent().getStringExtra("Key"); //groupId from intent

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_assignment);

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Assignment");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        final EditText date = (EditText) view.findViewById(R.id.date_editText_assignment);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFragment();
            }
        });
        final EditText title = (EditText) view.findViewById(R.id.assignment_title);
        final EditText description = (EditText) view.findViewById(R.id.assignment_description);
        Button add =(Button) view.findViewById(R.id.add_assignment);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(date.getText()) && !TextUtils.isEmpty(description.getText())){

                    Log.v("createAssignment","In add button");
                    String key = mDatabase.child("notifications").child(groupId).push().getKey();
                    Notifications notification = new Notifications(title.getText().toString(),description.getText().toString(),"assignment",groupId,date.getText().toString(),getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT),key);
                    Log.v("createAssignment","title :"+title.getText().toString()+" Description: "+description.getText().toString()+" GroupId: "+ groupId+ " date: "+date.getText().toString());
                    mDatabase.child("notifications").child(groupId).child(key).setValue(notification);
                    Toast.makeText(getContext(), "Assignment created", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
                else {
                    Toast.makeText(getContext(), "Fill all of the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    /*public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        EditText editText =(EditText) view.findViewById(R.id.date_editText_assignment);
        String date = day+"/"+month+"/"+year;
        editText.setText(date);
    }*/

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        EditText editText = (EditText) copy.findViewById(R.id.date_editText_assignment);
        editText.setText(date);
    }
    public void showDatePickerFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(ft, "datePicker");
    }

}
