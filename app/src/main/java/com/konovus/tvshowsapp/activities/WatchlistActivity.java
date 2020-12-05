package com.konovus.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.WatchlistAdapter;
import com.konovus.tvshowsapp.databinding.ActivityWatchlistBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.konovus.tvshowsapp.utilities.TempDataHolder.isWatchlistUpdated;

public class WatchlistActivity extends AppCompatActivity implements WatchlistAdapter.WatchlistListener {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization() {
        watchlistViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WatchlistViewModel.class);
        activityWatchlistBinding.backImg.setOnClickListener((view)-> onBackPressed());
        watchlist = new ArrayList<>();
        loadWatchlist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isWatchlistUpdated){
            loadWatchlist();
            isWatchlistUpdated = false;
        }
    }

    private void loadWatchlist() {
        activityWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tvShows -> {
                        activityWatchlistBinding.setIsLoading(false);
                        if(watchlist.size() > 0)
                            watchlist.clear();
                        watchlist.addAll(tvShows);
                        watchlistAdapter = new WatchlistAdapter(watchlist, this);
                        activityWatchlistBinding.recyclerViewWatchlist.setAdapter(watchlistAdapter);
                        activityWatchlistBinding.recyclerViewWatchlist.setVisibility(View.VISIBLE);
                        compositeDisposable.dispose();
                    }));
    }

    @Override
    public void OnTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void deleteTvShowFromWatchlist(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(watchlistViewModel.removeFromWatchList(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    watchlist.remove(position);
                    watchlistAdapter.notifyItemRemoved(position);
                    watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.getItemCount());
                    compositeDisposableForDelete.dispose();
                }));
    }
}
