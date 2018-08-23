package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.header);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        init(sandwich);

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        setTitle(sandwich.getMainName());

    }

    private void init(Sandwich sandwich) {
        TextView alsoKnowAsTv = findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView description = findViewById(R.id.description_tv);

        if(sandwich.getAlsoKnownAs().size()>0)
            alsoKnowAsTv.setText(sandwich.getAlsoKnownAs().toString());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        ingredients.setText(sandwich.getIngredients().toString());
        description.setText(sandwich.getDescription());
        //description.setMovementMethod(new ScrollingMovementMethod());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        int imageDimension = ingredientsIv.getMaxWidth();

        Picasso.with(DetailActivity.this)
                .load(sandwich.getImage())
                .resize(imageDimension, imageDimension)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        ingredientsIv.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        CollapsingToolbarLayout collapsingTool;
                                        collapsingTool = findViewById(R.id.collapsing_toolbar);
                                        int mutedColor = palette.getVibrantColor(R.attr.colorPrimary);
                                        collapsingTool.setContentScrimColor(mutedColor);

                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
