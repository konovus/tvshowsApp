package com.konovus.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.databinding.TvShowItemBinding;
import com.konovus.tvshowsapp.models.TVShow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.TVShowViewHolder>{

    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    private WatchlistListener watchlistListener;

    public WatchlistAdapter(List<TVShow> tvShows, WatchlistListener watchlistListener) {
        this.tvShows = tvShows;
        this.watchlistListener = watchlistListener;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        TvShowItemBinding tvShowItemBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.tv_show_item, parent, false
        );
        return new TVShowViewHolder(tvShowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder{

        private TvShowItemBinding tvShowItemBinding;

        public TVShowViewHolder(TvShowItemBinding tvShowItemBinding) {
            super(tvShowItemBinding.getRoot());
            this.tvShowItemBinding = tvShowItemBinding;

        }

        public void bindTVShow(TVShow tvShow){
            tvShowItemBinding.setTvShow(tvShow);
            tvShowItemBinding.executePendingBindings();
            tvShowItemBinding.getRoot().setOnClickListener(v -> watchlistListener.OnTVShowClicked(tvShow));
            tvShowItemBinding.deleteImg.setOnClickListener(
                    v -> watchlistListener.deleteTvShowFromWatchlist(tvShow, getAdapterPosition()));
            tvShowItemBinding.deleteImg.setVisibility(View.VISIBLE);
        }
    }
    public interface WatchlistListener {
        void OnTVShowClicked(TVShow tvShow);
        void deleteTvShowFromWatchlist(TVShow tvShow, int position);
    }
}
