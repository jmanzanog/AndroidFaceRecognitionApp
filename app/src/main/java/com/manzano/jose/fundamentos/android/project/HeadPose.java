
package com.manzano.jose.fundamentos.android.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeadPose {

    @SerializedName("pitch")
    @Expose
    private Double pitch;
    @SerializedName("roll")
    @Expose
    private Double roll;
    @SerializedName("yaw")
    @Expose
    private Double yaw;

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

}
