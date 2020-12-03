package com.konovus.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.databinding.EpisodeItemContainerBinding;
import com.konovus.tvshowsapp.models.Episode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>{

    private List<Episode> episodes;
    private LayoutInflater inflater;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        EpisodeItemContainerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.episode_item_container, parent, false
        );
        return new EpisodesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodesViewHolder extends RecyclerView.ViewHolder{

        private EpisodeItemContainerBinding binding;

        public EpisodesViewHolder(EpisodeItemContainerBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindEpisode(Episode episode){
            String title = "S";
            String season = episode.getSeason();
            if(season.length() == 1)
                season = "0".concat(season);
            String episodeNr = episode.getEpisode();
            if(episodeNr.length() == 1)
                episodeNr = "0".concat(episodeNr);
            episodeNr = "E".concat(episodeNr);
            title = title.concat(season).concat(episodeNr);
            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAir_date());

        }
    }

}
