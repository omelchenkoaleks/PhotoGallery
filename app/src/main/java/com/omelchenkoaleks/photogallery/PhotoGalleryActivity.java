package com.omelchenkoaleks.photogallery;

import android.support.v4.app.Fragment;
import android.util.Log;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    private static final String TAG = "PhotoGalleryActivity";

    @Override
    protected Fragment createFragment() {

        Log.i(TAG, "отработал метод createFragment");

        return PhotoGalleryFragment.newInstance();
    }
}
