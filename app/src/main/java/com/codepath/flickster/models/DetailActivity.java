package com.codepath.flickster.models;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.Movie;
import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyARVvk8wcc2w_Qeuil8ddPQ9wO65nAc5bg";
    private static final String TRAILER_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePayerView;

    Movie movie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        tvTitle = findViewById( R.id.tvTitle );
        tvOverview = findViewById( R.id.tvOverview );
        ratingBar = findViewById( R.id.ratingBar );
        youTubePayerView = findViewById(R.id.player);

        //String title = getIntent().getStringExtra( "title" );
       movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra( "movie" ));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getVoteAverage());

        AsyncHttpClient client = new AsyncHttpClient( );
        client.get(String.format( TRAILER_API, movie.getMovieId()), new JsonHttpResponseHandler( ){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess( statusCode, headers, response );
                try {
                    JSONArray results = response.getJSONArray( "results" );
                    if(results.length()==0){
                        return;
                    }
                    JSONObject movieTrailer = results.getJSONObject( 0 );
                    String youTubeKey = movieTrailer.getString( "key" );
                    initializeYoutube(youTubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure( statusCode, headers, responseString, throwable );
            }
        } );

    }

    private void initializeYoutube(final String youTubeKey) {
        youTubePayerView.initialize( YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile", "on init success");
                youTubePlayer.cueVideo(youTubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("smile", "on init failure");
            }
        } );
    }
}
