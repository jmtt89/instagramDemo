package com.ogangi.jtorres.instagramdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.engine.InstagramKitConstants;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPagInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MediaFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT  = "column-count";
    private static final String ARG_SEARCH = "search";

    private int mColumnCount = 2;
    private String mSearch = "";
    private OnListFragmentInteractionListener mListener;

    private ArrayList<IGMedia> mediaList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MediaFragment() {
    }

    @SuppressWarnings("unused")
    public static MediaFragment newInstance(int columnCount, String search) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_SEARCH, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mSearch = getArguments().getString(ARG_SEARCH);
        }
        mediaList = new ArrayList<>();
        InstagramEngine.getInstance(getContext()).getMediaWithTagName(mediaWithTagApiResponseCallback, mSearch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MediaRecyclerViewAdapter(mediaList,getContext(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    InstagramAPIResponseCallback<ArrayList<IGMedia>> mediaWithTagApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            mediaList.addAll(responseArray);

            if (responseArray.size() > 0) {

                for (IGMedia media : responseArray) {

                    Toast.makeText(getContext(), "Media Caption: " + media.getCaption().getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getCaption().getText());
                    Log.v("SampleActivity", "Media Type: " + media.getType());
                    if (media.getType().equals(InstagramKitConstants.kMediaTypeImage)) {
                        Log.v("SampleActivity", "Media Photo: " + media.getImages().getStandardResolution().getUrl() + "\n");
                    }
                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(getContext()).getMediaWithTagName(mediaWithTagApiResponseCallback, mSearch , 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(IGMedia media);
    }
}
