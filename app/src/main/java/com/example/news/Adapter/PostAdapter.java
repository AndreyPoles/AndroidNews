package com.example.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.API.Models.Post;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    Context context;
    List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        super(context, R.layout.list_item, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_item, parent, false);

        Post post = postList.get(position);

        TextView textTitle = (TextView) convertView.findViewById(R.id.text_title);
        textTitle.setText(String.valueOf(post.getTitle()));

        TextView textData = (TextView) convertView.findViewById(R.id.text_data);
        textData.setText(String.valueOf(post.getTime()));

        TextView textURL = (TextView) convertView.findViewById(R.id.text_url);
        textURL.setText(String.valueOf(post.getClickUrl()));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        Picasso.get().load(String.valueOf(post.getImg())).into(imageView);

        return convertView;
    }
}
