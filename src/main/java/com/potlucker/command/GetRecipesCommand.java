package com.potlucker.command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.potlucker.properties.PropertiesLookup;

/**
 * Created by treznick on 6/27/14.
 * based on http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 */
public class GetRecipesCommand {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String execute(String inputString) {
        PropertiesLookup pl = new PropertiesLookup();
        String baseUrl = "http://api.yummly.com/v1/api/recipes?";
        String appID = "_app_id=" + pl.getProperty("yummlyAPPID");
        String appKEY = "&_&_app_key=" +pl.getProperty("yummlyAPPKEY");

        List<String> ingredientArray = new ArrayList<String>(Arrays.asList(inputString.split(";")));

        String ingredientsString = "";
        for (String i : ingredientArray) {
            ingredientsString += "&allowedIngredient[]=" + i;
        }

        String url = baseUrl + appID + appKEY + ingredientsString;
        String outString = null;
        try {
            JSONObject output = readJsonFromUrl(url);
            outString = output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outString;
    }

    public static void main(String[] args) {
        GetRecipesCommand comm = new GetRecipesCommand();
        String ingredients = "eggs;milk;butter";
        System.out.println(comm.execute(ingredients));
    }
}
