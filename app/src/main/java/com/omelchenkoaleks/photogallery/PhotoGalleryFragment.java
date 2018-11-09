package com.omelchenkoaleks.photogallery;

import android.os.AsyncTask;
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
import android.widget.TextView;

import com.omelchenkoaleks.photogallery.model.GalleryItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView photoRecyclerView;

    private List<GalleryItem> items = new ArrayList<>();

    // Класс помогает сделать тест сетевому классу в фоновом режиме и вывести
    // в журнал результат запроса по URL.
    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            // Теперь мы возвращаем список элементов GalleryItem.
            return new FlickrFetchr().fetchItems();
        }

        // Этот метод получает список, загруженный в doInBackground(),
        // помещает его в items и обновляет RecyclerView.
        // Этот метод выполняется в основном потоке а не в фоновом,
        // поэтому обновление пользовательского интерфейса в нем безопасно.
        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            items = galleryItems;
            setupAdapter();
        }
    }

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        // Зачем удерживаем фрагмент?
        setRetainInstance(true);
        // Запускаем тестовый метод в фоновом режиме с помощью AsyncTask,
        // чтобы сделать тест классу FlickrFetchr.
        new FetchItemsTask().execute();
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

        // Каждый раз при создании нового объекта RecyclerView
        // этот метод будет связываться с подходящим адаптерм.
        setupAdapter();

        return view;
    }

    // Проверяем текущее состояние модели и настраиваем адаптер.
    private void setupAdapter() {
        // isAdded() проверяет, что фрагмент был присоединен к активности
        // и поэтому результат не будет null.
        // Это важно, мы используем AsyncTask, и некоторые действия инициируются
        // самостоятельно в другом потоке - теперь мы не можем предполагать,
        // что фрагмент присоединен к активности - это нужно проверить!
        if (isAdded()) {
            // Назначаем адаптер RecyclerView.
            photoRecyclerView.setAdapter(new PhotoAdapter(items));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView;
        }

        public void bindGalleryItem(GalleryItem item) {
            titleTextView.setText(item.toString());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> galleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            this.galleryItems = galleryItems;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView((getActivity()));
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = galleryItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return galleryItems.size();
        }
    }
}
