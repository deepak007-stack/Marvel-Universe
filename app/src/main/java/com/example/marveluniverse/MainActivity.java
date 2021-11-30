package com.example.marveluniverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private AppCompatEditText search;
    private RecyclerView recycler;
    private ArrayList<GetSet> arrayList = new ArrayList<>();
    private ArrayList<GetSet> filter_list = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        search = (AppCompatEditText) findViewById(R.id.search);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        get_data();

        recycler.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL,false));
        myAdapter = new MyAdapter(this,arrayList);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });

    }

    public void get_data(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://gateway.marvel.com/v1/public/characters?ts=1&apikey=36551afbfb0e41b6d6e23c6880e35335&hash=c5486b00df63774cacdee0a1e3698f13";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("Ok")){

                        String data = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(data);

                        String result = jsonObject1.getString("results");
                        JSONArray jsonArray = new JSONArray(result);

                        for(int i =0 ; i<jsonArray.length();i++){

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String id = jsonObject2.getString("id");  // id
                            String name = jsonObject2.getString("name");    // name
                            String description = jsonObject2.getString("description"); // description
                            String modified = jsonObject2.getString("modified");  // modified details
                            String resourceUri = jsonObject2.getString("resourceURI");
                            String thumbnail = jsonObject2.getString("thumbnail");
                            JSONObject jsonObject3 = new JSONObject(thumbnail);
                            String path = jsonObject3.getString("path");
                            String url = path +"/portrait_fantastic" +".jpg";                            // img url

                            GetSet getSet = new GetSet(name,url,id,description,modified,resourceUri);
                            arrayList.add(getSet);

                            recycler.setAdapter(myAdapter);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    public void filter(String text){

        ArrayList<GetSet> filteredList = new ArrayList<>();
        for(GetSet item : arrayList){

            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        myAdapter.setSearchData(filteredList);
    }

}