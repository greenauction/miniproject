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

public class PortalCreation extends AppCompatActivity {
    private FirebaseAuth fire;
    private Spinner dropDown,dropDownCategory;
    private DatabaseReference mDatabase;
    private EditText produce;
    private EditText location;
    private EditText qty;
    private EditText time;
    private EditText iniprice;
    private Button createbut;
    //private DatabaseReference mImg;
    FirebaseUser current;
    String e1[];


    String c="";
    int count;



    Map<String, String> imgmap = new HashMap<String, String>();
    public  PortalCreation() {

        imgmap.put("potato","https://tse1.mm.bing.net/th?id=OIP.oDSW7MrHpJpKcgIt4W3N4QHaE8&pid=15.1&P=0&w=261&h=175");
        imgmap.put("tomato","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmXQk9i0iTphknHQySqdvcXmxOz5K1kFn2fGVeRNX_gJItQ7lY");

        imgmap.put("orange","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkid2LgT8k6OKuTPz4kQwX2Dnp3YK3Pq0mayTCFLfZbTmFZhgX-A");
        imgmap.put("coriander","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBeF892J11FhWAJw-E4secl1vlyNTM1KSMZtVQiiJtGS04T7dn");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_creation);
        fire=FirebaseAuth.getInstance();
        dropDown = (Spinner) findViewById(R.id.quantity1);
        String[] items = new String[]{"kg", "quintal", "litre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropDown.setAdapter(adapter);

        dropDownCategory = (Spinner) findViewById(R.id.category);
        String[] items1 = new String[]{"fruit", "vegetables", "pulses"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropDownCategory.setAdapter(adapter1);
        produce = (EditText) findViewById(R.id.produce);
        location = (EditText) findViewById(R.id.location);
        //  count1=(TextView) findViewById(R.id.count);
        qty = (EditText) findViewById(R.id.qty);
        time = (EditText) findViewById(R.id.timeSlot);
        iniprice = (EditText) findViewById(R.id.iniPrice);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        final ArrayList<PersonInfo> person=new ArrayList<>();
        createbut = (Button) findViewById(R.id.create);
        createbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prod = produce.getText().toString().trim();
                String loc = location.getText().toString().trim();
                String qq = qty.getText().toString().trim();
                int q = Integer.parseInt(qq);
                String units = dropDown.getSelectedItem().toString();
                String ts = time.getText().toString().trim();
                String init_pri = iniprice.getText().toString().trim();


                c = "" + count;
                //setting count of auctions
               mDatabase.child("count").setValue(c);
               Log.d("count",c);
                PersonInfo info=new PersonInfo();
                info.qty=q;
                info.deadline=ts;
                info.initialbid=Float.parseFloat(init_pri);
                info.loc=loc;
                info.prod=prod;
                info.weight=units;
                info.email=fire.getCurrentUser().getEmail().toString();
                info.category=dropDownCategory.getSelectedItem().toString();
                //get the produce name's value which is the image's address.
                /*when we write "imgaddr" it gets stored as "\"imgaddr\"" in firebase
                hence we replace " in link with blank space because firebase puts the link automatically
                in quotes.*/
                info.image=imgmap.get(prod.toLowerCase().replace("\"",""));
                e1=fire.getCurrentUser().getEmail().toString().split("\\.");
                String e2=e1[0];
                mDatabase.child("auction").child(c).setValue(info);

               mDatabase.child("Consumer").child(e2).child("AuctionID").push().setValue(c);

                startActivity(new Intent(getApplicationContext(),FarmerBid.class));
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this is to give auction id
                    c= dataSnapshot.child("count").getValue().toString();
                    count=Integer.parseInt(c);
                    count++;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}



