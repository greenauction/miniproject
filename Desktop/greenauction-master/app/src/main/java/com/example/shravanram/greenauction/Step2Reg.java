package com.example.shravanram.greenauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Step2Reg extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private EditText city;
    private Button step2But;

    private DatabaseReference mData;

    String str;
    String c="";
    int count_of_farmers;
    int count_of_consumers;

    private FirebaseAuth fire;
    String e1[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2_reg);
        name=(EditText)findViewById(R.id.name);
        city = (EditText) findViewById(R.id.city);
        phone = (EditText) findViewById(R.id.phone);

        //to get the previous value if its a farmer or consumer
        Bundle obj=getIntent().getExtras();
        str=obj.getString("farmerOrConsumer");

        fire=FirebaseAuth.getInstance();

        mData = FirebaseDatabase.getInstance().getReference();



        step2But = (Button) findViewById(R.id.step2Button);
        step2But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = name.getText().toString().trim();
                String fcity=city.getText().toString().trim();
                String fphone=phone.getText().toString().trim();

                //to check if the required fields are entered
                if(TextUtils.isEmpty(fname)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(fcity)){
                    Toast.makeText(getApplicationContext(),"Please enter city",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(fphone)){
                    Toast.makeText(getApplicationContext(),"Please enter phone number",Toast.LENGTH_LONG).show();
                    return;
                }

                e1=fire.getCurrentUser().getEmail().toString().split("\\.");
                String e2=e1[0];
                //Log.d("abe?",str);
                if(str.contains("Consumer")) {
                    c = "" + count_of_consumers;
                    //Log.d("hey whatsup",c);
                    mData.child("ConsumerId").setValue(c);
                    mData.child("Consumer").child(e2).child("name").setValue(fname);
                    mData.child("Consumer").child(e2).child("phone").setValue(fphone);
                    mData.child("Consumer").child(e2).child("city").setValue(fcity);
                    mData.child("Consumer").child(e2).child("consumerid").setValue(c);
                    // Log.d("namu",c);


                }
                else{
                    c = "" + count_of_farmers;

                    mData.child("FarmerId").setValue(c);
                    mData.child("Farmer").child(e2).child("name").setValue(fname);
                    mData.child("Farmer").child(e2).child("phone").setValue(fphone);
                    mData.child("Farmer").child(e2).child("city").setValue(fcity);
                    mData.child("Farmer").child(e2).child("farmerid").setValue(c);

                }
            }
        });
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(str.contains("Consumer")) {
                    c = dataSnapshot.child("ConsumerId").getValue().toString();

                    count_of_consumers = Integer.parseInt(c);

                    count_of_consumers++;

                }
                else{
                    c = dataSnapshot.child("FarmerId").getValue().toString();

                    count_of_farmers = Integer.parseInt(c);

                    count_of_farmers++;


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
