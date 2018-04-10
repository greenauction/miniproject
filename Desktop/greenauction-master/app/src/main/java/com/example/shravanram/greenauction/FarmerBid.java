package com.example.shravanram.greenauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shravanram.greenauction.firebase_models.FarmerInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class FarmerBid extends AppCompatActivity {

    private DatabaseReference tRef;
    private DatabaseReference mRef;
    private FirebaseAuth fire=FirebaseAuth.getInstance();

  //  String emailno[];
    Calendar c = Calendar.getInstance();
    Date d1,d2;
    String t;

   // private ArrayList<String> auctionsSelect=new ArrayList<String>();
    //private ArrayList<String> auctionsOngoing=new ArrayList<String>();
    private RecyclerView ourlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_bid);
        Log.d("count",""+1);
        mRef= FirebaseDatabase.getInstance().getReference().child("Bids").child("1").child("Fid");
        Log.d("count",""+2);
        mRef.keepSynced(true);
        Log.d("count",""+3);
        ourlist=(RecyclerView)findViewById(R.id.bids);
        ourlist.setHasFixedSize(true);
        //change this line if you make changes in chikoo.xml
        ourlist.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("count",""+4);
        FirebaseRecyclerAdapter<FarmerInfo,Blogview>firebaseRecyclerAdapter1=new FirebaseRecyclerAdapter<FarmerInfo,
                Blogview>(FarmerInfo.class,R.layout.blog_row_bid,Blogview.class,mRef){

            @Override
            protected void populateViewHolder(Blogview viewHolder,FarmerInfo model,int position){


                    viewHolder.setFname(model.getFname());
                    viewHolder.setPrice(model.getPrice());
                    viewHolder.setRating(model.getRating());

                }


        };
        ourlist.setAdapter(firebaseRecyclerAdapter1);
    }
    public static class Blogview extends RecyclerView.ViewHolder {
        View mView;
        public Blogview(View itemView){
            super(itemView);
            mView=itemView;
        }
        public void setFname(String fname){
            TextView fname1=(TextView)mView.findViewById(R.id.t1);
            fname1.setText(fname);
        }

        public void setPrice(String price){
            TextView price1=(TextView)mView.findViewById(R.id.t2);
            price1.setText(price);
        }
        public void setRating(String rating){
            TextView rating1=(TextView)mView.findViewById(R.id.t3);
            rating1.setText(rating);
        }



    }
}