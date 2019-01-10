
package com.manzano.jose.fundamentos.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blur {

    @SerializedName("blurLevel")
    @Expose
    private String blurLevel;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getBlurLevel() {
        return blurLevel;
    }

    public void setBlurLevel(String blurLevel) {
        this.blurLevel = blurLevel;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
