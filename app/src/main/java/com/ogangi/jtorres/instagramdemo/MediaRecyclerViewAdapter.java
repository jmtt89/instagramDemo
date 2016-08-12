package com.ogangi.jtorres.instagramdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instagram.instagramapi.objects.IGMedia;
import com.ogangi.jtorres.instagramdemo.MediaFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IGMedia} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MediaRecyclerViewAdapter extends RecyclerView.Adapter<MediaRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<IGMedia> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MediaRecyclerViewAdapter(ArrayList<IGMedia> items, Context context ,OnListFragmentInteractionListener listener) {
        this.mValues = items;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IGMedia media = mValues.get(position);
        holder.mItem = media;
        holder.mTitleView.setText(media.getId());
        Picasso.with(context).load(media.getImages().getStandardResolution().getUrl()).into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final ImageView mImageView;
        public IGMedia mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.media_image);
            mTitleView = (TextView) view.findViewById(R.id.media_title);
        }

    }
}
