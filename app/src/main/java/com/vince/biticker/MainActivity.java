package com.vince.biticker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
//    Constants
    private String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

//    Variables
    Spinner mSpinner;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.conversion);

        String[] symbols = {"A$", "R$","C$"};

        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array,R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Biticker", ""+ parent.getItemAtPosition(position) + " Selected");
                String final_url = BASE_URL + parent.getItemAtPosition(position);
                networkRequest(final_url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void networkRequest(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Biticker", response.toString());
                try {
                    String price = String.valueOf(response.getInt("last")) ;
                    Log.d("Biticker", "" + price);
                    mTextView.setText(price);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Biticker", "Status BAD");
            }
        });
    }
}
