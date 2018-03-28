package com.example.shravanram.greenauction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.shravanram.greenauction.firebase_models.AuctionCardView1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Boolean.FALSE;

public class Ongoing extends AppCompatActivity {

    private DatabaseReference tRef;
    private DatabaseReference mRef;
    private FirebaseAuth fire=FirebaseAuth.getInstance();

    String emailno[];
    Calendar c = Calendar.getInstance();
    Date d1,d2;
    String t;

    private ArrayList<String> auctionsSelect=new ArrayList<String>();
    private ArrayList<String> auctionsOngoing=new ArrayList<String>();
    private RecyclerView ourlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing);
        mRef= FirebaseDatabase.getInstance().getReference().child("auction");
        mRef.keepSynced(true);

        ourlist=(RecyclerView)findViewById(R.id.auctions);
        ourlist.setHasFixedSize(true);
        //change this line if you make changes in chikoo.xml
        ourlist.setLayoutManager(new LinearLayoutManager(this));
        tRef = FirebaseDatabase.getInstance().getReference();
        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //gives emailno as an array  as beta1@gmail and com as second element
                emailno=fire.getCurrentUser().getEmail().toString().split("\\.");
                String e2=emailno[0];//contains beta1@gmail
                DataSnapshot AuctionID=dataSnapshot.child("Customer").child(e2).child("AuctionID");
                Iterable<DataSnapshot> AllAuctions=AuctionID.getChildren();
                for(DataSnapshot var1:AllAuctions)
                {
                    String id=var1.getValue().toString();
                    Log.d("heyyy my id",""+id);
                    t = dataSnapshot.child("auction").child(id).child("deadline").getValue().toString();
                    Log.d("deadline",""+t);
                    //selects all auctions of the current user ,can be ongoing or past
                    auctionsSelect.add(id);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                    String getCurrentDateTime = sdf.format(c.getTime());
                    try {
                        d1 = sdf.parse(getCurrentDateTime);
                        d2 = sdf.parse(t);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (d2.after(d1))

                    {

                        auctionsOngoing.add(id);
                        //ongoing auctions

                    }


                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<AuctionCardView1,BlogviewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AuctionCardView1,
                BlogviewHolder>(AuctionCardView1.class,R.layout.blog_row,BlogviewHolder.class,mRef){
          @Override
            protected void populateViewHolder(BlogviewHolder viewHolder,AuctionCardView1 model,int position){

             if(auctionsOngoing.contains(""+(position+1)))
              {
                  //Log.d("pos",""+position);
                  viewHolder.setProd(model.getProd());
                  viewHolder.setQty(model.getQty());
                  viewHolder.setDeadline(model.getDeadline());
                  viewHolder.setWeight(model.getWeight());
                  viewHolder.setLoc(model.getLoc());
                  viewHolder.setInitialBid(model.getInitialbid());
                  viewHolder.setCategory(model.getCategory());

              }
              else
             {
              viewHolder.setVisibility(FALSE);
             }
          }
            @Override
            public BlogviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                BlogviewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new BlogviewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        ourlist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class BlogviewHolder extends RecyclerView.ViewHolder {
        View mView;
        public  BlogviewHolder(View itemView){
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });
        }
        private BlogviewHolder.ClickListener mClickListener;

        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }

        public void setOnClickListener(BlogviewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        public void setProd(String prod){
            TextView produce=(TextView)mView.findViewById(R.id.t1);
            produce.setText(prod);
        }

        public void setQty(int qty){
            TextView quantity=(TextView)mView.findViewById(R.id.t2);
            quantity.setText(""+qty);
        }
        public void setDeadline(String ded){
            TextView dead=(TextView)mView.findViewById(R.id.t3);
            dead.setText(ded);
        }

        public void setWeight(String wt){
            TextView location=(TextView)mView.findViewById(R.id.t4);
            location.setText(wt);
        }
        public void setLoc(String loc){
            TextView location=(TextView)mView.findViewById(R.id.t5);
            location.setText(loc);
        }

        public void setInitialBid(float initialBid){
            TextView bid=(TextView)mView.findViewById(R.id.t6);
            bid.setText(""+initialBid);
        }
        public void setCategory(String cat){
            TextView cate=(TextView)mView.findViewById(R.id.t7);
            cate.setText(cat);
        }
        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();

                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;

            itemView.setLayoutParams(param);
        }


    }
}