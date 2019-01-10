
package com.manzano.jose.fundamentos.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exposure {

    @SerializedName("exposureLevel")
    @Expose
    private String exposureLevel;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getExposureLevel() {
        return exposureLevel;
    }

    public void setExposureLevel(String exposureLevel) {
        this.exposureLevel = exposureLevel;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
