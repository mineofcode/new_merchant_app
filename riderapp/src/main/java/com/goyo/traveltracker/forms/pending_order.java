package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.pending_order_adapter;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_pending;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;

import static com.goyo.traveltracker.R.id.txtNodata;
import static com.goyo.traveltracker.Service.RiderStatus.Rider_Lat;
import static com.goyo.traveltracker.Service.RiderStatus.Rider_Long;
import static com.goyo.traveltracker.gloabls.Global.urls.getAllocateTask;
import static com.goyo.traveltracker.gloabls.Global.urls.setTripAction;


public class pending_order extends AppCompatActivity {

    @BindView(R.id.Btn_Call)
    ImageButton Btn_Call;
    @BindView(R.id.Btn_Delivery)
    Button Btn_Delivery;
    @BindView(R.id.Btn_map)
    ImageButton Btn_Map;
    @BindView(R.id.Btn_Return)
    Button Btn_Return;
    @BindView(R.id.Collected_Cash)
    EditText collected_cash;

    private RecyclerView mRecyclerView;
    private ImageButton StartRide,BackButton;
    private com.goyo.traveltracker.adapters.pending_order_adapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    public static String TripId = "0";
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setCustomView(R.layout.pending_order_item);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle(getResources().getString(R.string.Pending_Order));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

//        StartRide = (ImageButton) findViewById(R.id.startRide);
//        BackButton = (ImageButton) findViewById(R.id.back);

//        BackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//
//            }
//        });


//        StartRide.setImageResource(R.drawable.start_trip);
//        StartRide.setBackgroundColor(Color.parseColor("#ff99cc00"));
//        StartRide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TripId.equals("0")) {
//                    new AlertDialog.Builder(pending_order.this)
//                            .setTitle(R.string.starttrip_head)
//                            .setMessage(R.string.starttrip_body)
//                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startTrip();
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.rider).show();
//
//                } else {
//                    new AlertDialog.Builder(pending_order.this)
//                            .setTitle(R.string.stoptrip_head)
//                            .setMessage(R.string.stoptrip_body)
//                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//
//                                    stopTrip();
//
//
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.stop_trip).show();
//                }
//            }
//        });



    }

    private void DataFromServer() {
//
//        loader = new ProgressDialog(this);
//        loader.setCancelable(false);
//        loader.setMessage(getResources().getString(R.string.wait_msg));
//        loader.show();
//
//        Ion.with(this)
//                .load("GET", getAllocateTask.value)
//                .addQuery("flag", "byemp")
//                .addQuery("empid","1")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_pending>>() {
//                            }.getType();
//                            List<model_pending> events = (List<model_pending>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        loader.hide();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });

        JsonObject json = new JsonObject();
        json.addProperty("flag", "byemp");
        json.addProperty("empid","1");

        Ion.with(this)
                .load(getAllocateTask.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_pending>>() {
                            }.getType();
                            List<model_pending> events = (List<model_pending>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void startTrip(){

        JsonObject json = new JsonObject();
        json.addProperty("flag", "start");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", TripId);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");

        Ion.with(this)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                          JsonObject Data=  result.get("data").getAsJsonObject();
                            if(Data.get("status").getAsBoolean()){
                                TripId=Data.get("tripid").toString();
                                StartRide.setBackgroundColor(Color.RED);
                                Toast.makeText(getApplicationContext(),"Your Ride Has started"
                                        ,Toast.LENGTH_SHORT).show();
                                StartRide.setBackgroundColor(Color.parseColor("#ffff4444"));
                                StartRide.setImageResource(R.drawable.end_trip);
                                mTimeLineAdapter.tripid = TripId;
                            }
                            else{
                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    public void stopTrip(){

        JsonObject json = new JsonObject();
        json.addProperty("flag", "stop");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", TripId);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");

        Ion.with(this)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){
                                TripId="0";
                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                                   onBackPressed();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void bindCurrentTrips(List<model_pending> lst) {
        if (lst.size() > 0) {
//            TripId = lst.get(0).tripid;
//            if(TripId.equals("0")){
//                //greeen
//                StartRide.setImageResource(R.drawable.start_trip);
//                StartRide.setBackgroundColor(Color.parseColor("#ff99cc00"));
//            }else {
//                //red
//                StartRide.setBackgroundColor(Color.parseColor("#ffff4444"));
//                StartRide.setImageResource(R.drawable.end_trip);
//            }
//
//
//            StartRide.setVisibility(View.VISIBLE);
//            findViewById(txtNodata).setVisibility(View.GONE);
//            for (int i =0; i<=lst.size()-1 ;i ++){
//                if(!lst.get(i).stats.equals("0")){
//                    lst.remove(i);
//                    i-=1;
//                }
//            }
//
//            if(!TripId.equals("0") && lst.size() ==0){
//                TextView Text =(TextView)findViewById(txtNodata);
//                findViewById(txtNodata).setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.INVISIBLE);
//                Text.setText(R.string.stoptrip_msg);
//            }
            mRecyclerView.setVisibility(View.VISIBLE);
            mTimeLineAdapter = new pending_order_adapter(lst, mOrientation, mWithLinePadding);
            mTimeLineAdapter.tripid = TripId;
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            StartRide.setVisibility(View.GONE);
            findViewById(txtNodata).setVisibility(View.VISIBLE);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTimeLineAdapter!=null){
            mTimeLineAdapter.notifyDataSetChanged();
        }
    }
}
