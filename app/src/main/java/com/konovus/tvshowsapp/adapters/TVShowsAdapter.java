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

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder>{

    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;

    public TVShowsAdapter(List<TVShow> tvShows) {
        this.tvShows = tvShows;
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

    public static class TVShowViewHolder extends RecyclerView.ViewHolder{

        private TvShowItemBinding tvShowItemBinding;

        public TVShowViewHolder(TvShowItemBinding tvShowItemBinding) {
            super(tvShowItemBinding.getRoot());
            this.tvShowItemBinding = tvShowItemBinding;
        }

        public void bindTVShow(TVShow tvShow){
            tvShowItemBinding.setTvShow(tvShow);
            tvShowItemBinding.executePendingBindings();
        }
    }
}
