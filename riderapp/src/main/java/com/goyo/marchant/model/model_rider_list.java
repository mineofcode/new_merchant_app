package com.goyo.marchant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajar on 30-May-17.
 */

public class model_rider_list {


    @SerializedName("nm")
    public String nm;

    @SerializedName("up_on")
    public String  up_on;

    @SerializedName("rdrtyp")
    public String rdrtyp;

    @SerializedName("btry")
    public Float btry;

    @SerializedName("mb")
    public String mb;

    @SerializedName("ch")
    public Boolean ch;

    @SerializedName("rdrid")
    public Integer rdrid;

    @SerializedName("onoff")
    public Boolean onoff;

    @SerializedName("km")
    public String km;

    public model_rider_list() {
    }


}
