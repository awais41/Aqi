package com.example.bluetoothreciever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bluetoothreciever.objects.CityAqi;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {

    ArrayList<CityAqi> mumbai = new ArrayList<>();
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lineChart = findViewById(R.id.lineChart);
         mumbai = (ArrayList<CityAqi>) getIntent().getSerializableExtra("mylist");

        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(),"data set");
        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

    private ArrayList<Entry> lineChartDataSet(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        for (int counter=0;counter < mumbai.size();counter++){
            dataSet.add(new Entry(counter, (float) mumbai.get(counter).getAqi()));
        }
        return dataSet;
    }

}