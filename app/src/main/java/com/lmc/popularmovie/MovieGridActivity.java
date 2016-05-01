package com.lmc.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lmc.popularmovie.utility.MovieApplication;

import java.util.ArrayList;

public class MovieGridActivity extends AppCompatActivity implements MovieGridFragment.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (R.id.action_settings):
                Intent intent = new Intent(this, PrefActivity.class);
                startActivityForResult(intent, 0);

        }
        return true;
    }


    @Override
    public void onItemSelected(ArrayList<String> movieDetailsList) {

        MovieDetailsFragment detailsFragment= (MovieDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.movieDetailFragment);
        if(detailsFragment==null){

            Intent intent = new Intent(MovieApplication.context, MovieDetailsActivity.class);
            intent.putStringArrayListExtra("com.lmc.popularmovie.details", movieDetailsList);
            //intent.putExtra("Details", detailsArray);
            startActivity(intent);

        }
        else{
            Log.d("Lamchith", "tablet view called");

            detailsFragment.updateContent(movieDetailsList);
        }
    }
}
