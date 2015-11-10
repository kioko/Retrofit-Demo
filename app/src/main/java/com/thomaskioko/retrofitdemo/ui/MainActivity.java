package com.thomaskioko.retrofitdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;
import com.thomaskioko.retrofitdemo.R;
import com.thomaskioko.retrofitdemo.adapter.ListAdapter;
import com.thomaskioko.retrofitdemo.api.ApiClient;
import com.thomaskioko.retrofitdemo.data.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private List<Movie> mMovieList = new ArrayList<>();
    private StaggeredGridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        getMovies();
    }

    /**
     * This method gets the Movie
     */
    public void getMovies() {

        ApiClient.getApiClient().getMovies(new Callback<List<Movie>>() {
            @Override
            public void success(List<Movie> movie, Response response) {

                for (Movie mMovie : movie) {
                    mMovieList.add(mMovie);
                    if (mMovieList != null) {
                        mGridView.setAdapter(new ListAdapter(getApplicationContext(), mMovieList));
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
            }
        });
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
