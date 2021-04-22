package com.sunnyweather.android.ui.place;

import android.app.Application;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunnyweather.android.R;
import com.sunnyweather.android.SunnyWeatherApplication;
import com.sunnyweather.android.logic.model.Place;

import java.util.List;

public class PlaceFragment extends Fragment {
    PlaceViewModel viewModel;
    private PlaceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(this, viewModel.placeList);
        recyclerView.setAdapter(adapter);
        EditText searchPlaceEdit = getActivity().findViewById(R.id.searchPlaceEdit);
        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("Tag","textChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (!content.isEmpty()) {
                    viewModel.placeList.clear();
                    adapter.notifyDataSetChanged();
                    viewModel.searchPlaces(content);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    ImageView bgImageView = (ImageView) getActivity().findViewById(R.id.bgImageView);
                    bgImageView.setVisibility(View.VISIBLE);
                    viewModel.placeList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        viewModel.placeLiveData.observe(getActivity(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                if (places != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    ImageView bgImageView = (ImageView) getActivity().findViewById(R.id.bgImageView);
                    bgImageView.setVisibility(View.GONE);
                    viewModel.placeList.addAll(places);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
