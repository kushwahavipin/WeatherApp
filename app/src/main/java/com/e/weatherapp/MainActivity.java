package com.e.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
 TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        new MyData().execute();

    }

    class MyData extends AsyncTask<String,String,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading.... Wait");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try
            {
                JSONObject jo = new JSONObject(s);
                JSONObject main=jo.getJSONObject("main");
                tv.setText("Min. Temp : "+main.getString("temp_min")+
                        "\nMax. Temp : "+main.getString("temp_max")+
                        "\ncity : "+jo.getString("name"));
            }
            catch (Exception e)
            {

            }
        }

        protected String doInBackground(String... strings) {
            try {


                OkHttpClient client = new OkHttpClient();


                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?q=Hyderabad&appid=7d7ea766c12c6277fecbdb62e8f8d1c3")
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch (Exception e)
            {

               // Toast.makeText(MainActivity.this, "caught"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

}