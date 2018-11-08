package com.omelchenkoaleks.photogallery;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Класс, который будет поддерживать все
// сетевые взаимоотношения в этом приложении.
public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";

    // Константа хранит некомерческий ключ для доступа на сайт Flickr.
    private static final String API_KEY = "fb6c7b37137c4e7ec8309d42ce7aa1a7";

    // Метод получает низкоуровневые данные по URL и
    // возвращает их в виде массива байтов.
    public byte[] getUrlBytes(String urlSpec) throws IOException {

        // Создаем объект URL на базе строки.
        URL url = new URL(urlSpec);
        // Вызов метода openConnection создает объект подключения к заданному URL-адресу.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage()
                        + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            // Объект InputStream предоставляет байты по мере их доступности.
            // Когда чтение будет завершено, он будет закрыт и выдаст массив байтов
            // из ByteArrayOutputStream
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();

        } finally {
            connection.disconnect();
        }
    }

    // Метод преобразует полученные данные методом getUrlBytes(String) в String.
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // Метод, который строит соответствующий URL-адрес запроса и загружает его содержимое.
    public void fetchItems() {
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }
}
