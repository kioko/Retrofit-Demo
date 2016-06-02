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

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.grid_view)
    StaggeredGridView mGridView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<Movie> mMovieList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getMovies();
    }

    /**
     * This method gets the Movie
     */
    private void getMovies() {

        ApiClient apiClient = new ApiClient();
        apiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        Call<List<Movie>> listCall = apiClient.movieServices().getMovies();
        listCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, retrofit2.Response<List<Movie>> response) {

                for (Movie mMovie : response.body()) {
                    mMovieList.add(mMovie);
                    if (mMovieList != null) {
                        mGridView.setAdapter(new ListAdapter(getApplicationContext(), mMovieList));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

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
