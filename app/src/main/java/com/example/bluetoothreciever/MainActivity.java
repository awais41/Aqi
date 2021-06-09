package com.example.bluetoothreciever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetoothreciever.objects.CityAqi;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    String url = "ws://city-ws.herokuapp.com/";
    Gson gson = new Gson();
    ArrayList<CityAqi> mumbai = new ArrayList<>();
    ArrayList<CityAqi> bengaluru = new ArrayList<>();
    ArrayList<CityAqi> delhi = new ArrayList<>();

    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    TextView aqi;
    TextView city;
    TextView lastDate;
    ConstraintLayout cs;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aqi = findViewById(R.id.aqi);
        city = findViewById(R.id.city);
        lastDate = findViewById(R.id.lastUpdateTV);
        cs = findViewById(R.id.cs);
        createWebSocketClient();

        cs.setOnClickListener(v -> {
        LoadChart();
        });




    }

    private void LoadChart() {
        Intent myIntent = new Intent(MainActivity.this, LineChartActivity.class);
        myIntent.putExtra("mylist",mumbai);
        startActivity(myIntent);
    }

    private void createWebSocketClient() {
            URI uri;
            try {
                uri = new URI(url);
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                return;
            }

            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen() {
                }

                @Override
                public void onTextReceived(String message) {
                   // Log.d("check123",message);
                    String json = message;
                  //  ArrayList<CityAqi> cityAqi = gson.fromJson(json,CityAqi.class);

                    List<CityAqi> list = Arrays.asList(new GsonBuilder().create().fromJson(json, CityAqi[].class));

                    if(list.get(0).city.equals("Mumbai")){
                        Date currentTime = Calendar.getInstance().getTime();
                        mumbai.add(new CityAqi(list.get(0).aqi,list.get(0).city,
                                dateFormat.format(new Date())));

                        runOnUiThread(() -> {
                        updateUi();

                        });

                        Log.d("check123",String.valueOf(mumbai.size()));
                    }

                    if(list.get(0).city.equals("Delhi")){
                        delhi.add(new CityAqi(list.get(0).aqi,list.get(0).city,
                                dateFormat.format(new Date())));
                        Log.d("check123",String.valueOf(delhi.size()));
                    }


                    //Log.d("check123",list.get(0).city);

                }

                @Override
                public void onBinaryReceived(byte[] data) {
                    System.out.println("onBinaryReceived");
                }

                @Override
                public void onPingReceived(byte[] data) {
                    System.out.println("onPingReceived");
                }

                @Override
                public void onPongReceived(byte[] data) {
                    System.out.println("onPongReceived");
                }

                @Override
                public void onException(Exception e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onCloseReceived() {
                    System.out.println("onCloseReceived");
                }
            };

            webSocketClient.setConnectTimeout(10000);
            webSocketClient.setReadTimeout(60000);
            webSocketClient.addHeader("Origin", "http://developer.example.com");
            webSocketClient.enableAutomaticReconnection(5000);
            webSocketClient.connect();
    }

    void updateUi(){




    city.setText("Mumbai");
    aqi.setText(String.valueOf(mumbai.get(mumbai.size()-1).aqi));
    lastDate.setText(String.valueOf(mumbai.get(mumbai.size()-1).getSavedTime()));

   int colorParser= (int) Double.parseDouble(aqi.getText().toString());

       if(colorParser > 0 && colorParser < 50){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.good));
    }
    else if(colorParser > 51 && colorParser < 100){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.satisfactory));
    }
    else if(colorParser > 101 && colorParser < 200){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.moderate));
    }
    else if(colorParser > 201 && colorParser < 300){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.poor));
    }

    else if(colorParser > 301 && colorParser < 400){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.very_poor));
    }
    else if(colorParser > 401 && colorParser < 500){
        aqi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.severe));
    }


    }

}