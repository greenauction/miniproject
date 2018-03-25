package com.example.shravanram.greenauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class portalView extends AppCompatActivity {
    private DatabaseReference tRef;
    private DatabaseReference cRef;
    private TextView time1;
    String emailno[];
    private FirebaseAuth fire=FirebaseAuth.getInstance();
    Calendar c = Calendar.getInstance();
    Date d1,d2;
    String t;
    ListView myList;
    private ArrayList<String> auctions=new ArrayList<>();
    private ArrayList<String> auctionsSelect=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_view);
        myList=findViewById((R.id.list1));

        time1=(TextView)findViewById(R.id.time);
        final ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,auctions);
        myList.setAdapter(arrayAdapter);
        tRef = FirebaseDatabase.getInstance().getReference();

       /* tRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(int i=0;i<auctionsSelect.size();i++) {
                    String auc = dataSnapshot.child("auction").child(""+auctionsSelect.get(i)).child("deadline").getValue().toString();
                    auctions.add(auc);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  tRef =
      */
        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailno=fire.getCurrentUser().getEmail().toString().split("\\.");
                String e2=emailno[0];
                DataSnapshot AuctionID=dataSnapshot.child("Customer").child(e2).child("AuctionID");
                Iterable<DataSnapshot> AllAuctions=AuctionID.getChildren();
                for(DataSnapshot var1:AllAuctions)
                {
                   String id=var1.getValue().toString();
                    t = dataSnapshot.child("auction").child(id).child("deadline").getValue().toString();
                    auctionsSelect.add(id);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                    String getCurrentDateTime = sdf.format(c.getTime());
                    try {
                        d1 = sdf.parse(getCurrentDateTime);
                        d2 = sdf.parse(t);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



// getCurrentDateTime: 05/23/2016 18:49 PM

                    if (d2.after(d1))

                    {

                        Log.i("getCurrentDateTime",t);
                    } else {
//                        Log.i("getCurrentDateTime", ""+i);
                    }


                }
                Log.i("hey bitch",auctionsSelect.get(1).toString());


               /* String count = dataSnapshot.child("count").getValue().toString().trim();
                for (int i = 1; i <= Integer.parseInt(count); i++) {
                    t = dataSnapshot.child("auction").child("" + i).child("deadline").getValue().toString();
//                    Log.d("deadline", dead);


                    //             String t= dataSnapshot.getValue().toString().trim();

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                    String getCurrentDateTime = sdf.format(c.getTime());
                    try {
                        d1 = sdf.parse(getCurrentDateTime);
                        d2 = sdf.parse(t);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ;


// getCurrentDateTime: 05/23/2016 18:49 PM

                    if (d1.after(d2))

                    {

                        Log.i("getCurrentDateTime", ""+i);
                    } else {
                        Log.i("getCurrentDateTime", ""+i);
                    }

                }
*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
