package com.goyo.marchant.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.marchant.R;
import com.goyo.marchant.adapters.AnnouncmentAdapter;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.model.modal_data;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import static com.goyo.marchant.forms.dashboard.SclId;
import static com.goyo.marchant.gloabls.Global.urls.getAnnouncement;

public class Announcment extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private com.goyo.marchant.adapters.AnnouncmentAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcment);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle("Announcement");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);

        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                DataFromServer();
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server
                DataFromServer();
            }
        });

    }


    private void DataFromServer() {
        JsonObject json = new JsonObject();
        json.addProperty("flag", "summary");
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("enttid", SclId+"");
        Ion.with(this)
                .load(getAnnouncement.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            // JSONObj                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ect jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<modal_data>>() {
                            }.getType();
                            List<modal_data> events = (List<modal_data>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

//                    }
                });
    }
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }
    private void bindCurrentTrips(List<modal_data> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new AnnouncmentAdapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

