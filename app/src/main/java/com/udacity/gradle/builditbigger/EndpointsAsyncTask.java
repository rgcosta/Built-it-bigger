package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.os.Handler;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.DoneCallback, Void, String> {
    private static final long DELAY_MILLIS = 3000;
    private DoneCallback doneCallback;
    private static MyApi myApiService = null;
    private String ROOT_URL = "http://192.168.1.35:8080/_ah/api/";



    @Override
    protected String doInBackground(DoneCallback... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        doneCallback = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String result) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (doneCallback != null)
                    doneCallback.onDone(result);
            }
        }, DELAY_MILLIS);
    }

    interface DoneCallback {
        void onDone(String result);
    }
}


