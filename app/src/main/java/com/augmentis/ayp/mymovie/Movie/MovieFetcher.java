package com.augmentis.ayp.mymovie.Movie;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Waraporn on 10/6/2016.
 */

public class MovieFetcher {

    private static final String TAG = "MovieFetcher";

    /**
     * Open connection.
     * Read and write data from stream.
     *
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Authorization", "Basic bWFqb3Jtb3ZpZTp3Z2w4bjhnb3lybG5wMjk0MHJqbDFxYzdjcDBsZ2dpaDg");

        Log.d(TAG, "getUrlBytes: ");
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //read data from Stream
            InputStream in = connection.getInputStream();

            Log.d(TAG, "Connection OK");

            //if connection is not OK throw new IOException
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;

            byte[] buffer = new byte[2048];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();

        } finally {
            connection.disconnect();
        }
    }
}
