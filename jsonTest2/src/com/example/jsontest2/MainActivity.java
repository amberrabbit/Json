package com.example.jsontest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView json_text , json_key;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//表示するテキストビューのIDを指定
		json_text = (TextView) findViewById(R.id.json_text);
		json_key = (TextView) findViewById(R.id.json_key);
		
		
		//URLの指定（文字列）
		String requestURL = "http://api.openweathermap.org/data/2.5/find?lat=43.067885&lon=141.355539&cnt=1";
	    
		//URLの生成と初期化
		URL url = null;
		
	    try {
	    	//urlに指定URLを格納
			url = new URL(requestURL);
			
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	    
	    //入力ストリームの生成
	    InputStream is = null;
	    
		try {
			//isに指定URLを入力
			is = url.openConnection().getInputStream();
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	    //JSON形式で結果が返るためパースのためにStringに変換する。
		//入力したものを一時的に入れておくバッファリーダーの生成
	    BufferedReader reader = null;
		try {
			
			//文字コードを指定し、バッファリーダーに格納
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		//バッファリーダーを入れておく可変長文字列の生成。
	    StringBuilder sb = new StringBuilder();
	    
	    //１行ごとに読み込むための文字を生成
	    String line;
	    
	    //条件：readerの中身が空になるまで読んでlineに格納。
	    try {
			while (null != (line = reader.readLine())) {
			    
				//lineをsbに格納
				sb.append(line);
			}
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	    
	    //可変長文字列sbを文字列に変換。
	    String data = sb.toString();
	    
	    //jsonファイルの表示
	    json_text.setText(data);
	    
	    try {
	    	
	    	//data(文字列に変換されているjson)をJSONObjectに格納。
	        JSONObject rootObj = new JSONObject(data);
	        
	        //jsonの中のlist（全部）を取得。
	        JSONArray listArray = rootObj.getJSONArray("list");
	        
	        //json配列を格納
	        JSONObject obj = listArray.getJSONObject(0);
	        
	        // 地点名
	        String cityName = obj.getString("name");
	        
	        //表示
	        json_key.setText(cityName);
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
