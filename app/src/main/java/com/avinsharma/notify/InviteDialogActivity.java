package com.avinsharma.notify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteDialogActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    TextView title;
    TextView description;
    Button accept;
    Button decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_dialog);

        final String Uid = user.getUid();

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        accept = (Button) findViewById(R.id.accept);
        decline = (Button) findViewById(R.id.decline);

        title.setText(getIntent().getStringExtra("Title"));
        description.setText(getIntent().getStringExtra("Description"));

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("invitations").child(Uid).child(getIntent().getStringExtra("NKey")).setValue(null);
                finish();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("groups-followed").child(Uid).child(getIntent().getStringExtra("Key")).setValue(true);
                mDatabase.child("invitations").child(Uid).child(getIntent().getStringExtra("NKey")).setValue(null);
                finish();
            }
        });
    }
}
