package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.udacity.sandwichclub.adapters.SandwichAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);

        RecyclerView recyclerView = findViewById(R.id.sandwiches_recycler_view);
        // use this setting to improve performance of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        SandwichAdapter sandwichAdapter = new SandwichAdapter(sandwiches, this, new SandwichAdapter.SandwichItemListener() {
            @Override
            public void onItemClicked(int i) {
                launchDetailActivity(i);
            }
        });
        recyclerView.setAdapter(sandwichAdapter);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
}
