package com.example.shravanram.greenauction.FarmerSide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shravanram.greenauction.R;
import com.example.shravanram.greenauction.firebase_models.FarmerInfo;

import java.util.List;

/**
 * Created by Shravan ram on 3/27/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder>{
    List<FarmerInfo>  list;
    Context context;
    public RecyclerAdapter(){

    }
    public RecyclerAdapter(List<FarmerInfo> list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.blog_row_bid,parent,false);
        MyHoder myHoder=new MyHoder(view);
        return myHoder;
    }
    @Override
    public int getItemCount(){
        int arr=0;
        try{
            if(list.size()==0)
            {
                arr=0;
            }
            else
            {
                arr=list.size();
            }
        }
        catch (Exception e )
        {

        }
        Log.d("count",""+arr);
        return arr;
    }
    @Override
    public void onBindViewHolder(MyHoder holder,int position)
    {
        FarmerInfo mylist=list.get(position);
        holder.name.setText(mylist.getFname());

        holder.price.setText("Rating "+ mylist.getRating());
        holder.rating.setText(mylist.getPrice());
    }
    class MyHoder extends RecyclerView.ViewHolder{
        TextView name,price,rating;
        public MyHoder(View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.fname);
            price=(TextView)itemView.findViewById(R.id.price);
            rating=(TextView)itemView.findViewById(R.id.rating);
        }
    }
}