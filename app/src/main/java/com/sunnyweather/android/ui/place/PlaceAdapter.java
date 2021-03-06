package com.sunnyweather.android.ui.place;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.sunnyweather.android.R;
import com.sunnyweather.android.SunnyWeatherApplication;
import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.Weather;
import com.sunnyweather.android.ui.weather.WeatherActivity;
import com.sunnyweather.android.ui.weather.WeatherViewModel;

import java.util.List;

import static java.lang.Thread.sleep;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private PlaceFragment fragment;
    private List<Place> placeList;

    public PlaceAdapter(PlaceFragment fragment, List<Place> placeList) {
        this.fragment = fragment;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.palce_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Place place = placeList.get(position);

                FragmentActivity activity = fragment.getActivity();
                if (activity instanceof WeatherActivity) {
                    DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawerLayout);
                    drawerLayout.closeDrawers();
                    ((WeatherActivity) activity).viewModel.locationLat = place.getLocation().getLat();
                    ((WeatherActivity) activity).viewModel.locationLng = place.getLocation().getLng();
                    ((WeatherActivity) activity).viewModel.placeName = place.getName();
                    ((WeatherActivity) activity).refreshWeather();
                } else {
                    Intent intent = new Intent(parent.getContext(), WeatherActivity.class)
                            .putExtra("location_lng", place.getLocation().getLng())
                            .putExtra("location_lat", place.getLocation().getLat())
                            .putExtra("place_name", place.getName());
                    fragment.startActivity(intent);
                    fragment.getActivity().finish();
                }
                fragment.viewModel.savePlace(place);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
        /*
        if(place.getAddress() != null)
            Log.d("Tag",place.getAddress());
        Log.d("Tag","nothing");
        */
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView placeName;
        TextView placeAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            placeName = view.findViewById(R.id.placeName);
            placeAddress = view.findViewById(R.id.placeAddress);
        }
    }
}
