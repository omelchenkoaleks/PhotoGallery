package com.omelchenkoaleks.photogallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Класс, который будет поддерживать все
// сетевые взаимоотношения в этом приложении.
public class FlickrFetchr {

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

                int bytesRead = 0;
                byte[] buffer = new byte[1024];
            }
        }
    }
}
