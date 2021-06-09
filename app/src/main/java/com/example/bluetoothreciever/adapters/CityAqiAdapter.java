package com.example.bluetoothreciever.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothreciever.R;
import com.example.bluetoothreciever.interfaces.ItemClickPositionInterface;
import com.example.bluetoothreciever.objects.CityAqi;

import java.util.List;

public class CityAqiAdapter extends RecyclerView.Adapter<CityAqiAdapter.ExampleViewHolder>{

    private static final String TAG = "StoreListAdapter";

    CityAqi cityAqisList;
    ItemClickPositionInterface onClickItemPositionInterface;
    Activity context;
    View v;

    public CityAqiAdapter(Activity context, CityAqi cityAqisList, ItemClickPositionInterface onClickItemPositionInterface) {

        this.context = context;
        this.onClickItemPositionInterface = onClickItemPositionInterface;
        this.cityAqisList = cityAqisList;
    }



    @Override
    public int getItemCount() {
        return 1;
    }

    @NonNull
    @Override
    public CityAqiAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aqi_level_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;



    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView aqi;
        TextView city;
        TextView lastDate;
        ConstraintLayout cs;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            aqi = itemView.findViewById(R.id.aqi);
            city = itemView.findViewById(R.id.city);
            lastDate = itemView.findViewById(R.id.lastUpdateTV);
            cs = itemView.findViewById(R.id.cs);
            cs.setOnClickListener(v -> onClickItemPositionInterface.Onitemclick(getAdapterPosition()));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CityAqiAdapter.ExampleViewHolder holder, int position) {


        holder.city.setText(cityAqisList.getCity());
        holder.aqi.setText(Double.toString(cityAqisList.getAqi()));
        holder.lastDate.setText(String.valueOf(cityAqisList.getSavedTime()));


    }

}

