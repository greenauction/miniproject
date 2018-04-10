package com.example.shravanram.greenauction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shravanram.greenauction.FarmerSide.AllOngoingFarmerSide;
import com.google.firebase.auth.FirebaseAuth;

public class FarmerProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private ImageButton createPortal;
    private ImageButton viewPortal;

    private Button ongoingAuctions,myBids;
    private Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_farmer_profile);

        buttonLogout = (Button) findViewById(R.id.ButtonLogout);
        textViewUserEmail=(TextView) findViewById(R.id.userText);
        buttonLogout.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();
        ongoingAuctions=(Button) findViewById(R.id.ongoingAuctions);
        myBids=(Button) findViewById(R.id.myBids);
        ongoingAuctions.setOnClickListener(this);
        myBids.setOnClickListener(this);
       /* if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));


        }*/
        //FirebaseUser user=firebaseAuth.getCurrentUser();
        //textViewUserEmail.setText("Welcome user" +user.getEmail());

    }

    @Override
    public void onClick(View view) {
        if(view==ongoingAuctions)
        {
            // finish();
            startActivity(new Intent(this,AllOngoingFarmerSide.class));

        }
        if(view==myBids)
        {
            // finish();
           // startActivity(new Intent(this,FarmerOngoing.class));
        }
        if(view==buttonLogout) {
            // finish();
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
