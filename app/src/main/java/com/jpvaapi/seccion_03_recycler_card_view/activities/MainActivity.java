package com.jpvaapi.seccion_03_recycler_card_view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toast;

import com.jpvaapi.seccion_03_recycler_card_view.adapters.MyAdapter;
import com.jpvaapi.seccion_03_recycler_card_view.R;
import com.jpvaapi.seccion_03_recycler_card_view.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;
    private RecyclerView mRecyclerView;
    //It can be declared as 'RecyclerView' or as our 'MyAdapter' adapter class
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = this.getAllMovies();
        mRecyclerView = findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new GridLayoutManager(this, 2);
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //We implement our own OnItemClickListener, overwriting the method that we
        //we define in the adapter and receiving the parameters that we need
        mAdapter = new MyAdapter(movies, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie, int position) {
                //Toast.makeText(MainActivity.this, name + " -" + position, Toast.LENGTH_LONG).show();
                removeMovie(position);
            }
        });

        //We use it in case we know that the layout will not change size, improving the performance
        mRecyclerView.setHasFixedSize(true);
        //Add an effect by default, if we deactivate it completely null
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //We link the manager and adapter layout directly to the recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_name:
                this.addMovie(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Movie> getAllMovies() {
        return new ArrayList<Movie>() {{
            add(new Movie("Ben hur", R.drawable.benhur));
            add(new Movie("DeadPool", R.drawable.deadpool));
            add(new Movie("Guardians of the Galaxy", R.drawable.guardians));
            add(new Movie("WarCraft", R.drawable.warcraft));
        }};
    }

    private void addMovie(int position) {
        movies.add(position, new Movie("New image " + (++counter), R.drawable.newmovie));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }

    private void removeMovie(int position) {
        movies.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
