package com.konovus.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.responses.TVShowsResponse;
import com.konovus.tvshowsapp.viewmodels.MostPopularTvShowsViewModel;

public class MainActivity extends AppCompatActivity {

    private MostPopularTvShowsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);

        getMostPopular();
    }

    private void getMostPopular(){
        viewModel.getMostPopularTvShows(0).observe(this, new Observer<TVShowsResponse>() {
            @Override
            public void onChanged(TVShowsResponse tvShowsResponse) {
                Toast.makeText(MainActivity.this, "Total pages: " +
                        tvShowsResponse.getPages(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
