package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String link="https://football.ua/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Info1","Hello world");
        DownloadTask downloadTask=new DownloadTask();
        try {
            String result=downloadTask.execute(link).get();
            Log.i("Info3",result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    private class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
           StringBuilder builder=new StringBuilder("Start");
           URL url=null;
           HttpURLConnection httpURLConnection=null;
            Log.i("Info1","In doinBack");
            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream stream=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(stream);
                BufferedReader bufferedReader=new BufferedReader(reader);
                String line=bufferedReader.readLine();
                while (line!=null){
                    builder.append(line);
                    line=bufferedReader.readLine();
                }

            } catch (MalformedURLException e) {
                Log.i("Info1","Malf");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("Info1","IOE");
                e.printStackTrace();
            }
            return builder.toString();
        }
    }
}
