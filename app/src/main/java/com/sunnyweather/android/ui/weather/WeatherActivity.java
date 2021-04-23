package com.sunnyweather.android.ui.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyweather.android.R;
import com.sunnyweather.android.logic.model.DailyResponse;
import com.sunnyweather.android.logic.model.RealtimeResponse;
import com.sunnyweather.android.logic.model.Sky;
import com.sunnyweather.android.logic.model.Weather;
import com.sunnyweather.android.ui.place.PlaceViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        setContentView(R.layout.activity_weather);
        Sky.initSky();
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = getIntent().getStringExtra("location_lng");
            //Log.d("Tag","lng OK"+viewModel.locationLng);
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = getIntent().getStringExtra("location_lat");
            //Log.d("Tag","lat OK"+viewModel.locationLat);
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = getIntent().getStringExtra("place_name");
            //Log.d("Tag","name OK"+viewModel.placeName);
        }
        viewModel.weatherLiveData.observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather.getRealtime() != null && weather.getDaily() != null) {
                    showWeatherInfo(weather);
                } else if (weather.getRealtime() == null && weather.getDaily() == null) {
                    Toast.makeText(WeatherActivity.this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat);
    }

    private void showWeatherInfo(Weather weather) {
        TextView placeName = (TextView) findViewById(R.id.placeName);
        placeName.setText(viewModel.placeName);
        RealtimeResponse.Realtime realtime = weather.getRealtime();
        DailyResponse.Daily daily = weather.getDaily();
        //填充now中的数据
        TextView currentTemp = (TextView) findViewById(R.id.currentTemp);
        currentTemp.setText((int) realtime.getTemperature() + "℃");

        TextView currentSky = (TextView) findViewById(R.id.currentSky);
        currentSky.setText(Sky.getSky(realtime.getSkycon()).getInfo());

        TextView currentAQI = (TextView) findViewById(R.id.currentAQI);
        currentAQI.setText("空气指数" + (int) realtime.getAirQuality().getAqi().getChn());

        RelativeLayout nowLayout = (RelativeLayout) findViewById(R.id.nowLayout);
        nowLayout.setBackgroundResource(Sky.getSky(realtime.getSkycon()).getBg());
        //填充forecast布局中的数据
        LinearLayout forecastLayout = (LinearLayout) findViewById(R.id.forecastLayout);
        forecastLayout.removeAllViews();
        int days = daily.getSkycon().size();
        for (int i = 0; i < days; i++) {
            DailyResponse.Skycon skycon = daily.getSkycon().get(i);
            DailyResponse.Temperature temperature = daily.getTemperature().get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateInfo = (TextView) view.findViewById(R.id.dateInfo);
            ImageView skyIron = (ImageView) view.findViewById(R.id.skyIron);
            TextView skyInfo = (TextView) view.findViewById(R.id.skyInfo);
            TextView temperatureInfo = (TextView) view.findViewById(R.id.temperatureInfo);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateInfo.setText(simpleDateFormat.format(skycon.getDate()));
            Sky sky = Sky.getSky(skycon.getValue());
            skyIron.setImageResource(sky.getIcon());
            skyInfo.setText(sky.getInfo());
            temperatureInfo.setText((int) temperature.getMin() + "~" + (int) temperature.getMax() + "℃");
            forecastLayout.addView(view);
        }
        //填充life_index
        DailyResponse.LifeIndex lifeIndex = daily.getLifeIndex();
        TextView coldRiskText = (TextView) findViewById(R.id.coldRiskText);
        coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());

        TextView dressingText = (TextView) findViewById(R.id.dressingText);
        dressingText.setText(lifeIndex.getDressing().get(0).getDesc());

        TextView ultravioletText = (TextView) findViewById(R.id.ultravioletText);
        ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());

        TextView carWashingText = (TextView) findViewById(R.id.carWashingText);
        carWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());

        ScrollView weatherLayout = (ScrollView) findViewById(R.id.weatherLayout);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}