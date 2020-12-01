package com.konovus.tvshowsapp.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.TextUtils;
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
        activityTVShowDetailsBinding.backImg.setOnClickListener(v -> onBackPressed());
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
                    if(tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                        loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                        activityTVShowDetailsBinding.setTvShowImageUrl(tvShowDetailsResponse.getTvShowDetails().getImage_path());
                        activityTVShowDetailsBinding.posterImg.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.setDescription(
                                String.valueOf(HtmlCompat.fromHtml(
                                        tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY))
                        );
                        activityTVShowDetailsBinding.descriptionDetails.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.readMoreDetails.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.readMoreDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(activityTVShowDetailsBinding.readMoreDetails.getText().toString().equals("Read More")){
                                    activityTVShowDetailsBinding.readMoreDetails.setText("Read Less");
                                    activityTVShowDetailsBinding.descriptionDetails.setEllipsize(null);
                                    activityTVShowDetailsBinding.descriptionDetails.setMaxLines(Integer.MAX_VALUE);
                                } else {
                                    activityTVShowDetailsBinding.readMoreDetails.setText("Read More");
                                    activityTVShowDetailsBinding.descriptionDetails.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTVShowDetailsBinding.descriptionDetails.setMaxLines(4);
                                }
                            }
                        });
                        loadBasicTVShowInfo(tvShow);
                    }
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
    private void loadBasicTVShowInfo(TVShow tvShow){
        activityTVShowDetailsBinding.setTvShowName(tvShow.getName());
        activityTVShowDetailsBinding.setNetworkCountry(tvShow.getNetwork() + "(" + tvShow.getCountry() + ")");
        activityTVShowDetailsBinding.setStatus(tvShow.getStatus());
        activityTVShowDetailsBinding.setStartedDate(tvShow.getStartDate());

        activityTVShowDetailsBinding.titleDetails.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.networkDetails.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.statusDetails.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.startedDateDetails.setVisibility(View.VISIBLE);
    }
}
