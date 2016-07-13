package com.avinsharma.notify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGroupActivity extends AppCompatActivity {

    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
    final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        final EditText title = (EditText) findViewById(R.id.edit_group_title);
        final EditText description = (EditText) findViewById(R.id.edit_group_description);
        Button add = (Button) findViewById(R.id.create_group_add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(description.getText())){
                    String key = mdatabase.child("groups").push().getKey();
                    Groups group = new Groups(title.getText().toString(), description.getText().toString(),key);
                    mdatabase.child("groups").child(key).setValue(group);
                    mdatabase.child("groups-created").child(Uid).child(key).setValue(true);
                    mdatabase.child("group-members").child(key).child(Uid).setValue(true);
                    Toast.makeText(getBaseContext(),"Group Created!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (TextUtils.isEmpty(title.getText())){
                    Toast.makeText(getBaseContext(),"Enter Title",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(),"Enter description",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
