package com.example.ravneet.chatapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ravneet on 20/7/17.
 */

public class MessageAdapter extends ArrayAdapter<Message> {


    public MessageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView != null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_item_message,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_imageMessage);
        TextView tv_message = (TextView) convertView.findViewById(R.id.tv_message);
        TextView tv_sender = (TextView) convertView.findViewById(R.id.tv_name);

        Message thismessage = getItem(position);

        if (thismessage.getPhotoURL() != null){

            tv_message.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(imageView.getContext()).load(thismessage.getPhotoURL()).into(imageView);
            // For Glide
           // Glide.with(photoImageView.getContext()).load(message.getPhotoUrl()).into(photoImageView);
        }else {
            tv_message.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            tv_message.setText(thismessage.getMessage());
        }

        tv_sender.setText(thismessage.getName());

        return convertView;
    }
}
