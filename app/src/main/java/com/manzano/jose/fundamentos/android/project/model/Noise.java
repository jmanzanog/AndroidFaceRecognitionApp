
package com.manzano.jose.fundamentos.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noise {

    @SerializedName("noiseLevel")
    @Expose
    private String noiseLevel;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(String noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
