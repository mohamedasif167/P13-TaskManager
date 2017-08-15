package com.myapplicationdev.android.p06_taskmanager;

import android.content.Intent;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {

    DBHelper dbh=new DBHelper(ReplyActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null,name;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
        }

        if(reply != null){
            if(reply.toString().equalsIgnoreCase("Completed")){
                int id = intent.getIntExtra("id", -1);
                Log.d("Value of ID",id+"");
                dbh.deleteNote(id);

            }else{

            }
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
                    Toast.LENGTH_SHORT).show();
        }
    }
}