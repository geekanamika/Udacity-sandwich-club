package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainObj = new JSONObject(json);
            Sandwich sandwich;

            List<String> ingredientsList = new ArrayList<>();
            List<String> alsoKnownAsList = new ArrayList<>();
            JSONObject name = mainObj.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = mainObj.getString("placeOfOrigin");
            String description = mainObj.getString("description");
            String image = mainObj.getString("image");
            JSONArray ingredientsArray = mainObj.getJSONArray("ingredients");

            if(alsoKnownAsArray.length()>0) {
                for(int i=0; i<alsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }
            }

            if(ingredientsArray.length()>0) {
                for(int i=0; i<ingredientsArray.length(); i++) {
                    ingredientsList.add(ingredientsArray.getString(i));
                }
            }

            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;

        } catch (JSONException e) {
            Log.e("myTag", "JsonUtils: json exception");
        }

        return null;
    }
}
