package com.konovus.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.TVShowsAdapter;
import com.konovus.tvshowsapp.databinding.ActivitySearchBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.responses.TVShowsResponse;
import com.konovus.tvshowsapp.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowsAdapter.TVShowListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter adapter;
    private int currentPage = 1;
    private int totalPages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInitialization();
    }

    private void doInitialization() {
        activitySearchBinding.backImg.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.recyclerViewSearch.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        adapter = new TVShowsAdapter(tvShows, this);
        activitySearchBinding.recyclerViewSearch.setAdapter(adapter);
        activitySearchBinding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalPages = 1;
                                    searchTVShow(s.toString());
                                }
                            });
                        }
                    }, 800);
                } else {
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!activitySearchBinding.searchEt.getText().toString().isEmpty()) {
                        if (currentPage < totalPages) {
                            currentPage += 1;
                            searchTVShow(activitySearchBinding.searchEt.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.searchEt.requestFocus();
    }

    private void searchTVShow(String query) {
        toggleLoading();
        searchViewModel.searchTvShow(query, currentPage).observe(this, new Observer<TVShowsResponse>() {
            @Override
            public void onChanged(TVShowsResponse tvShowsResponse) {
                toggleLoading();
                if (tvShowsResponse != null) {
                    totalPages = tvShowsResponse.getPages();
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowsResponse.getTvShows());
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size());

                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading())
                activitySearchBinding.setIsLoading(false);
            else
                activitySearchBinding.setIsLoading(true);
        } else {
            if (activitySearchBinding.getIsLoadingMore())
                activitySearchBinding.setIsLoadingMore(false);
            else
                activitySearchBinding.setIsLoadingMore(true);
        }
    }

    @Override
    public void OnTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(SearchActivity.this, TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}
