package com.kens.moviedirectory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kens.moviedirectory.Data.MovieRecyclerViewAdapter;
import com.kens.moviedirectory.Model.Movie;
import com.kens.moviedirectory.Util.Constants;
import com.kens.moviedirectory.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private List<Movie> movieList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this); // this is the request pool for volley

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //our alert dialog goes here
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>(); //this list will hold all the movies for this view

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();

        getMovies(search);

    }


    //get our movies
    public List<Movie> getMovies(String searchTerm){
        movieList.clear();

        String targetURL = Constants.URL_LEFT + searchTerm + Constants.URL_RIGHT;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                targetURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray moviesArray = response.getJSONArray("Search"); // this is the name of the array on the target json file

                            for(int i = 0; i < moviesArray.length(); i++){

                                JSONObject movieObj = moviesArray.getJSONObject(i);

                                Movie movie = new Movie();

                                movie.setTitle(movieObj.getString("Title"));
                                movie.setYear(movieObj.getString("Year"));
                                movie.setMovieType(movieObj.getString("Type"));
                                movie.setPoster(movieObj.getString("Poster"));
                                movie.setImdbId(movieObj.getString("imdbID"));

                                movieList.add(movie);

                                Log.d("moviell: ", movie.getTitle());
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { // handler the error

                    }
        });

        queue.add(jsonObjectRequest);

        return movieList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
