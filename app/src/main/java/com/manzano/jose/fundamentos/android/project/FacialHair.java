
package com.manzano.jose.fundamentos.android.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacialHair {

    @SerializedName("moustache")
    @Expose
    private Double moustache;
    @SerializedName("beard")
    @Expose
    private Double beard;
    @SerializedName("sideburns")
    @Expose
    private Double sideburns;

    public Double getMoustache() {
        return moustache;
    }

    public void setMoustache(Double moustache) {
        this.moustache = moustache;
    }

    public Double getBeard() {
        return beard;
    }

    public void setBeard(Double beard) {
        this.beard = beard;
    }

    public Double getSideburns() {
        return sideburns;
    }

    public void setSideburns(Double sideburns) {
        this.sideburns = sideburns;
    }

}
