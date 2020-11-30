package com.konovus.tvshowsapp.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.ItemSliderAdapter;
import com.konovus.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.responses.TVShowDetailsResponse;
import com.konovus.tvshowsapp.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();

    }

    private void doInitialization(){
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
    }

    private void getTVShowDetails(){
        activityTVShowDetailsBinding.setIsLoading(true);
        TVShow tvShow;
        if(getIntent().hasExtra("tvShow")){
            tvShow = getIntent().getParcelableExtra("tvShow");
            viewModel.getTVShowDetails(tvShow.getId()).observe(this, new Observer<TVShowDetailsResponse>() {
                @Override
                public void onChanged(TVShowDetailsResponse tvShowDetailsResponse) {
                    activityTVShowDetailsBinding.setIsLoading(false);
                    if(tvShowDetailsResponse.getTvShowDetails().getPictures() != null)
                        loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
            });
        }
    }
    private void loadImageSlider(String[] sliderImages){
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ItemSliderAdapter(sliderImages));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImages.length);
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8,0,8,0);
        for(int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_inactive_shape
            ));
            indicators[i].setLayoutParams(params);
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position){
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if(i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_active_shape)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_inactive_shape));

            }
        }
    }
}
