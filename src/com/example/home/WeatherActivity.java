package com.example.home;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	
	String appId = "dj0zaiZpPVVNN0VJQlREejg5diZzPWNvbnN1bWVyc2VjcmV0Jng9Y2Q-";
	String locationURL =
			"http://contents.search.olp.yahooapis.jp/OpenLocalPlatform/V1/contentsGeoCoder";
	String WeatherURL = "http://weather.olp.yahooapis.jp/v1/place";
	AsyncHttpClient client;
	
	TextView tv1;
	TextView tv2;
	EditText editText;
	ListView listView;
	ArrayAdapter<String> adapter;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		client = new AsyncHttpClient();
		
		tv1 = (TextView)findViewById(R.id.denwa);
		tv2 = (TextView)findViewById(R.id.tv2);
		editText = (EditText)findViewById(R.id.editText);
		listView = (ListView)findViewById(R.id.listView);
		
		
		
	}
	public void getWeather(View v){
		String query = editText.getText().toString();
		RequestParams requestParams = new RequestParams();
		requestParams.put("appid", appId);
		requestParams.put("output", "json");
		requestParams.put("query", query);
		
		client.get(locationURL, requestParams, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, JSONObject responce){
				super.onSuccess(statusCode, responce);
				
				try{
					JSONArray features = responce.getJSONArray("Feature");
					JSONObject feature = features.getJSONObject(0);
					JSONObject geometry = feature.getJSONObject("Geometry");
					String coordinates = geometry.getString("Coordinates");
					tv1.setText(feature.getString("Name"));
					tv2.setText(coordinates);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		public void getWeatherInfomation(String coordinates){
			RequestParams requestParams = new RequestParams();
			requestParams.put("appid", appId);
			requestParams.put("output", "json");
			requestParams.put("query", coordinates);
			client.get(WeatherURL, requestParams, new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, JSONObject responce) {
					super.onSuccess(statusCode, responce);
					try{
						JSONArray features = responce.getJSONArray("Feature");
						JSONObject feature = features.getJSONObject(0);
						JSONObject property = feature.getJSONObject("Propatie");
						JSONObject weatherList = property.getJSONObject("WeatherList");
						JSONArray weathers = weatherList.getJSONArray("Weather");
						
						adapter.clear();
						
						for(int i = 0; i < weathers.length(); i++){
							JSONObject weather = weathers.getJSONObject(i);
							
							String date = weather.getString("Date");
							String type = weather.getString("Type");
							float strong = Float.valueOf(weather.getString("Rainfall"));
							
							String result = "date: " + date + 
							"_ntype: " + type +
							"_nstrong: " + strong;
							
							adapter.add(result);
						}
					}catch(JSONException e){
						e.printStackTrace();
					}
				}
			});
		}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

}
