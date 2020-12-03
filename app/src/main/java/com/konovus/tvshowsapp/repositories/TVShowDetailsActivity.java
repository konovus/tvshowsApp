package com.konovus.tvshowsapp.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.EpisodesAdapter;
import com.konovus.tvshowsapp.adapters.ItemSliderAdapter;
import com.konovus.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.konovus.tvshowsapp.databinding.EpisodesBottomLayoutBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.responses.TVShowDetailsResponse;
import com.konovus.tvshowsapp.viewmodels.TVShowDetailsViewModel;

import java.util.Locale;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel viewModel;
    private BottomSheetDialog bottomSheetDialog;
    private EpisodesBottomLayoutBinding episodesBottomLayoutBinding;

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
                        activityTVShowDetailsBinding.setRating(
                                String.format(Locale.getDefault(), "%.2f",
                                        Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating()))
                        );
                        if(tvShowDetailsResponse.getTvShowDetails().getGenres() != null)
                            activityTVShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                        else activityTVShowDetailsBinding.setGenre("N/A");
                        activityTVShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + "Min");
                        activityTVShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.btnWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            }
                        });
                        activityTVShowDetailsBinding.btnWebsite.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.btnEpisodes.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.btnEpisodes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bottomSheetDialog == null)
                                    bottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                                EpisodesBottomLayoutBinding episodesBottomLayoutBinding = DataBindingUtil.inflate(
                                        LayoutInflater.from(TVShowDetailsActivity.this),
                                        R.layout.episodes_bottom_layout, findViewById(R.id.episodesContainer), false
                                );
                                bottomSheetDialog.setContentView(episodesBottomLayoutBinding.getRoot());
                                episodesBottomLayoutBinding.episodesRecyclerView.setAdapter(
                                        new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes())
                                );
                                episodesBottomLayoutBinding.titleEpisode.setText("Episodes | " + tvShow.getName());
                                episodesBottomLayoutBinding.closeImg.setOnClickListener((view)-> bottomSheetDialog.dismiss());

                                bottomSheetDialog.show();
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
