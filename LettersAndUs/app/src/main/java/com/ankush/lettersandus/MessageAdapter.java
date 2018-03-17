package com.ankush.lettersandus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nEW u on 9/17/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    ArrayList<Message> items;
    Typeface font;
    class MessageHolder extends RecyclerView.ViewHolder {
        public final TextView largeText;
        public final TextView smallText;
        public final CardView inboxCard;
        public final Context context;
        public final TextView fromText;
        public final ImageView stampImage;
        public MessageHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            largeText = (TextView) itemView.findViewById(R.id.bigText);
            smallText = (TextView) itemView.findViewById(R.id.smallText);
            inboxCard = (CardView) itemView.findViewById(R.id.inbox_card);
            stampImage = (ImageView)itemView.findViewById(R.id.stampImage);
            fromText = (TextView)itemView.findViewById(R.id.from_text);
        }

    }
    MessageAdapter(ArrayList<Message> i,Context context){
        items = i;
        font = Typeface.createFromAsset(context.getAssets(), "IndieFlower.ttf");
    }
    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.inbox_item,parent, false);
        MessageHolder vh = new MessageHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, int position) {
        final Message data = items.get(position);
        String l = data.getLetterText();
        if(data.getLetterText().length()>=13)
            l = l.substring(0,12)+" ... ";
        holder.largeText.setText(l);
        holder.smallText.setText(data.getFrom());
        holder.smallText.setTypeface(font);
        holder.largeText.setTypeface(font);
        holder.fromText.setTypeface(font);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference str = firebaseStorage.getReference();
        StorageReference load = str.child(data.getStampUrl());
        Glide.with(holder.context /* context */)
                .using(new FirebaseImageLoader())
                .load(load)
                .into(holder.stampImage);
        holder.inboxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showIntent = new Intent(holder.context,ShowActivity.class);
                showIntent.putExtra("LETTER_TEXT",data.getLetterText());
                holder.context.startActivity(showIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
