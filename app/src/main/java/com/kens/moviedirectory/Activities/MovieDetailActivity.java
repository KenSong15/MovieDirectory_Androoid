package com.kens.moviedirectory.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kens.moviedirectory.Model.Movie;
import com.kens.moviedirectory.R;
import com.kens.moviedirectory.Util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;

    private TextView movieYear;
    private TextView director;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView runTime;

    private RequestQueue queue;
    private String movieId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getImdbId();

        setUpUI();
        getMovieDetails(movieId);
    }

    private void setUpUI() {
        movieTitle = (TextView) findViewById(R.id.movieTitleIDDets);
        movieImage = (ImageView) findViewById(R.id.movieImageIDDets);
        movieYear = (TextView) findViewById(R.id.movieReleaseIDDets);
        director = (TextView) findViewById(R.id.directedByDet);
        category = (TextView) findViewById(R.id.movieCatIDDet);
        rating = (TextView) findViewById(R.id.movieRatingIDDet);
        writers = (TextView) findViewById(R.id.writersDet);
        plot = (TextView) findViewById(R.id.plotDet);
        boxOffice = (TextView) findViewById(R.id.boxOfficeDet);
        runTime = (TextView) findViewById(R.id.runtimeDet);
        actors = (TextView) findViewById(R.id.actorsDet);
    }

    private void getMovieDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.DETS_HEAD + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.has("Ratings")){
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null; // using hard code to show only one source and value
                        if(ratings.length() > 0){

                            JSONObject mRating = ratings.getJSONObject(ratings.length() - 1);
                            source = mRating.getString("Source");
                            value = mRating.getString("Value");
                            rating.setText("Rating source: " + source + " with " + value);
                        } else {
                            rating.setText("Not Avaliable");
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }


}
