package com.konovus.tvshowsapp.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
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
                    Toast.makeText(TVShowDetailsActivity.this,
                            "TVShow rating: " + tvShowDetailsResponse.getTvShowDetails().getRating(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
