package com.avinsharma.notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class AddDialog extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String groupId;
    String description;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        groupId = getIntent().getStringExtra("Key");
        email = (EditText) findViewById(R.id.invite_email_addresses_edittext);
        Button cancel = (Button) findViewById(R.id.cancel_member_activity_button);
        Button add = (Button) findViewById(R.id.add_member_activity_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailsString = email.getText().toString();
                final List<String> emails = Arrays.asList(emailsString.split(",[ ]*"));
                final List<String> confirmEmails;
                for (Object object : emails) {
                    String email = (String) object;
                    Log.v("AddMember", emailToKey(email));
                    mDatabase.child("emails").child(emailToKey(email)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.v("AddMember",String.valueOf(dataSnapshot.exists()));
                            if (dataSnapshot.exists()) {
                                Log.v("AddMember",dataSnapshot.getKey().toString());
                                String key = mDatabase.child("notifications").child(dataSnapshot.getValue().toString()).push().getKey();
                                Notifications invite = new Notifications("invite", groupId, getIntent().getStringExtra(Intent.EXTRA_TEXT), getIntent().getStringExtra("Description"),key);
                                mDatabase.child("invitations").child(dataSnapshot.getValue().toString()).child(key).setValue(invite);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(AddDialog.this, "Invitation failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finish();
            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String emailToKey(String email) {
        return email.replace('.', '!');
    }


}
