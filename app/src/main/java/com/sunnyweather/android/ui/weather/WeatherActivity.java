package com.sunnyweather.android.ui.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import static java.lang.Thread.sleep;

public class WeatherActivity extends AppCompatActivity {
    public WeatherViewModel viewModel;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_weather);

        Sky.initSky();
        swipeRefresh = (SwipeRefreshLayout) WeatherActivity.this.findViewById(R.id.swipeRefresh);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.locationLng = getIntent().getStringExtra("location_lng");
        viewModel.locationLat = getIntent().getStringExtra("location_lat");
        viewModel.placeName = getIntent().getStringExtra("place_name");

        viewModel.weatherLiveData.observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather.getRealtime() != null && weather.getDaily() != null) {
                    showWeatherInfo(weather);
                } else if (weather.getRealtime() == null && weather.getDaily() == null) {
                    Toast.makeText(WeatherActivity.this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show();
                } else {
                    //由于SunnyWeatherNetwo类中数据两个线程不同步，返回数据时weather的属性可能一个是空的，一个是正常，此时不能正常显示界面，待解决
                    Toast.makeText(WeatherActivity.this, "下拉获取最新数据", Toast.LENGTH_SHORT).show();
                }
                //请求结束后，刷新事件结束
                swipeRefresh.setRefreshing(false);
            }
        });
        swipeRefresh.setColorSchemeResources(R.color.purple_500);
        refreshWeather();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWeather();
            }
        });

        //设置滑动菜单
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Button navBtn = (Button) findViewById(R.id.navBtn);
        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //隐藏输入法
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    public void refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat);
        swipeRefresh.setRefreshing(true);
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