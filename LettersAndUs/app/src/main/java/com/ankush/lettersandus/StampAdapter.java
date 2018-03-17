package com.ankush.lettersandus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by nEW u on 9/19/2017.
 */

public class StampAdapter extends RecyclerView.Adapter<StampAdapter.ViewHolder>  {
    ArrayList<String> thumbUrls;
    Context context;
    AdapterListener dispatcher;
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView stampImage;
        public Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            stampImage = (ImageView)itemView.findViewById(R.id.stampThumbnail);
            context = itemView.getContext();
        }
    }
    StampAdapter(ArrayList<String> urls,Context context,AdapterListener dispatcher){
        thumbUrls = urls;
        this.context = context;
        this.dispatcher = dispatcher;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.stamp_item,parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String tUrl = thumbUrls.get(position);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference str = firebaseStorage.getReference();
        StorageReference load = str.child(tUrl);
        Glide.with(context /* context */)
                .using(new FirebaseImageLoader())
                .load(load)
                .into(holder.stampImage);
        holder.stampImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatcher.sendIntent(tUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thumbUrls.size();
    }


}
