package se.byfootapp.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class HTTPClient {

    public static final int STATUS_CODE_OK = 200;
    
    public JSONObject getResponseAsJSON(String urlString) throws Exception {
        JSONObject serverResponse = new JSONObject(doRequest(urlString));
        return serverResponse;
    }
    
    public String getResponseAsString(String urlString) throws Exception {
        return doRequest(urlString);
    }
    
    private String doRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = null;

        try {
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setRequestProperty("Accept", "application/json;charset=utf-8");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == STATUS_CODE_OK) {
                InputStream in = new BufferedInputStream(
                        urlConnection.getInputStream());
                reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            }
            
            throw new Exception("no server response..");
        } finally {
            urlConnection.disconnect();
            if(reader != null) {reader.close();}
        }
    }
}
