/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.gui.Message;

/**
 *
 * @author aa
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import java.net.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

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

 
    public static String show(String ip) {
      try {
          JSONObject json = readJsonFromUrl("http://ip-api.com/json/"+ip);
          System.out.println(json.toString());
          return(json.get("city").toString());
      } catch (IOException ex) {
          Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
  }
    
    
     public static String weather (String city) {
      try {
          JSONObject json = readJsonFromUrl("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e9c2b0c499690fa94f67bd8aa2583597");
        //  System.out.println(json.get("weather"));
          JSONArray arr = json.getJSONArray("weather");
           

         // System.out.println(arr.getJSONObject(0).getString("description"));
          return(translate("en", "fr", arr.getJSONObject(0).getString("description")));
      } catch (IOException ex) {
          Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
  }
  
     
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbw4_gN31lSQQqNpeJ7AbRDIK6CVVrqd8T2q6EnktDuTxhWqLREdf7Chvk4ZkuOJ3bHm2A/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
     
     
     
}