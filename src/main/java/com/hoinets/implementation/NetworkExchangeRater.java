package com.hoinets.implementation;

import com.hoinets.base.ExchangeRater;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkExchangeRater implements ExchangeRater {
    private static final String URL = "http://data.fixer.io/api/latest?access_key=f06278316405ef6555eeb346d51fc75d";
    private static final String FIELD_RATES = "rates";

    @Override
    public float getRate(String currency) {
        float result = 0f;
        try {
            URL obj = new URL(URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());
            result = new JSONObject(myResponse.getJSONObject(FIELD_RATES).toString()).getFloat(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
