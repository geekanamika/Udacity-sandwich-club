package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.header);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
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

            //populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
            toolbarSetUp(sandwich);

        }



    }

    private void toolbarSetUp(Sandwich sandwich) {
        toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(sandwich.getMainName());

//        appBarLayout = findViewById(R.id.appbar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//            }
//        });
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

//    private void populateUI(Sandwich sandwich) {
//
//        int imageDimension = ingredientsIv.getMaxWidth();
//        Log.d("myTag", " "+ imageDimension);
//        Picasso.with(DetailActivity.this)
//                .load(sandwich.getImage())
//                .resize(imageDimension, imageDimension)
//                .centerCrop()
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        ingredientsIv.setImageBitmap(bitmap);
//                        Palette.from(bitmap)
//                                .generate(new Palette.PaletteAsyncListener() {
//                                    @Override
//                                    public void onGenerated(@NonNull Palette palette) {
//                                        CollapsingToolbarLayout collapsingTool;
//                                        collapsingTool = findViewById(R.id.collapsing_toolbar);
//                                        int darkVibrantColor = palette.getDarkVibrantColor(R.attr.colorPrimary);
//                                        collapsingTool.setContentScrimColor(darkVibrantColor);
//                                        Log.d("myTag", " "+ darkVibrantColor);
//
//                                        toolbar.setBackgroundColor(darkVibrantColor);
//
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//                        Log.d("myTag", "failed");
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
