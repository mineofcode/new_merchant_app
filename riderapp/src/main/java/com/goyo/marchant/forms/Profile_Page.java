package com.goyo.marchant.forms;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.JsonObject;
import com.goyo.marchant.R;
import com.goyo.marchant.adapters.ProfileAdapter;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Profile_Page extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView planets_list;
    RecyclerView.LayoutManager layout_manager;
    ProfileAdapter adapter;
    CollapsingToolbarLayout collapsing_container;
    FloatingActionButton Fab;
    String data[]=new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);

        toolbar = (Toolbar) findViewById(R.id.technique_four_toolbar);
        planets_list = (RecyclerView) findViewById(R.id.days_list_4);
        collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);

        layout_manager = new LinearLayoutManager(this);
        planets_list.setLayoutManager(layout_manager);

        GetData();

        setSupportActionBar(toolbar);
        collapsing_container.setTitle(Preferences.getValue_String(getApplicationContext(), Preferences.USER_NAME));

        Fab=(FloatingActionButton)findViewById(R.id.fab);

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void GetData(){
        JsonObject json = new JsonObject();

        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("flag", "parent");
        Ion.with(this)
                .load(Global.urls.getUserDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {

                            data[0]="ID: "+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                            data[1]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("mobile").getAsString();
                            data[3]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("enttname").getAsString();
                            data[2]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("gender").getAsString();
                            data[4]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("address").getAsString();
                            data[5]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("area").getAsString()+","+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("city").getAsString();
                            data[6]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("state").getAsString()+","+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("country").getAsString();
                            Names(data);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }
    private void Names(String data[]){
        adapter = new ProfileAdapter(data);
        planets_list.setAdapter(adapter);
    }

    }

