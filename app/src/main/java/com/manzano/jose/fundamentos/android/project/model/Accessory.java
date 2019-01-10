package com.manzano.jose.fundamentos.android.project.model;

import com.google.gson.annotations.SerializedName;

public class Accessory {
    public Accessory.AccessoryType type;
    public double confidence;

    public Accessory() {
    }

    public static enum AccessoryType {
        @SerializedName("headwear")
        Headwear,
        @SerializedName("glasses")
        Glasses,
        @SerializedName("mask")
        Mask;

        private AccessoryType() {
        }
    }
}
