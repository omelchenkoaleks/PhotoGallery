package com.omelchenkoaleks.photogallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoGalleryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private RecyclerView photoRecyclerView;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        // Зачем удерживаем фрагмент?
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_photo_gallery,
                container, false);

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }
}
