package com.konovus.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.databinding.ItemContainerImageSliderBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSliderAdapter extends RecyclerView.Adapter<ItemSliderAdapter.ItemSliderViewHolder>{

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public ItemSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ItemSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        ItemContainerImageSliderBinding sliderBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_image_slider, parent, false
        );
        return new ItemSliderViewHolder(sliderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSliderViewHolder holder, int position) {
        holder.setSliderBinding(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

    static class ItemSliderViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerImageSliderBinding sliderBinding;

        public ItemSliderViewHolder(ItemContainerImageSliderBinding sliderBinding){
            super(sliderBinding.getRoot());
            this.sliderBinding = sliderBinding;
        }

        public void setSliderBinding(String imageUrl){
            sliderBinding.setImageUrl(imageUrl);
        }
    }
}
