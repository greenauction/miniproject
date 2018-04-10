package com.example.shravanram.greenauction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shravanram.greenauction.FarmerSide.FarmerSideViewBidsInAuction;
import com.example.shravanram.greenauction.firebase_models.PersonInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class EnterBidForm extends AppCompatActivity {
    private FirebaseAuth fire;

    private DatabaseReference mDatabase;
    private EditText loc;
    private EditText price;
    private Button bidEnterbut;
    String nameOfFarmer,ratingOfFarmer;
    String e1[];
    String e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bid_form);
        fire=FirebaseAuth.getInstance();

        loc = (EditText) findViewById(R.id.location);
        //time = (EditText) findViewById(R.id.timeSlot);
        price = (EditText) findViewById(R.id.price);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        e1=fire.getCurrentUser().getEmail().toString().split("\\.");
         e2=e1[0];

        bidEnterbut = (Button) findViewById(R.id.bidEnter);
        bidEnterbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String location = loc.getText().toString().trim();
                //nothing is being done about location
                String init_pri = price.getText().toString().trim();


                Intent i = getIntent();
                final String auctionId= i.getStringExtra("auctionSelected");
                mDatabase.child("Bids").child(auctionId).child(e2).child("fname").setValue(nameOfFarmer);
                mDatabase.child("Bids").child(auctionId).child(e2).child("price").setValue(init_pri);
                mDatabase.child("Bids").child(auctionId).child(e2).child("rating").setValue(ratingOfFarmer);
                mDatabase.child("Farmer").child(e2).child("BidID").push().setValue(auctionId);
                //mDatabase.child("Consumer").child(e2).child("AuctionID").push().setValue(c);

                startActivity(new Intent(getApplicationContext(), FarmerSideViewBidsInAuction.class));
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this is to give auction id
                nameOfFarmer=dataSnapshot.child("Farmer").child(e2).child("name").getValue().toString();
                ratingOfFarmer=dataSnapshot.child("Farmer").child(e2).child("rating").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}



