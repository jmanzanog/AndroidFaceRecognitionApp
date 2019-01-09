
package com.manzano.jose.fundamentos.android.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Face {

    @SerializedName("faceId")
    @Expose
    private String faceId;
    @SerializedName("faceRectangle")
    @Expose
    private FaceRectangle faceRectangle;
    @SerializedName("faceAttributes")
    @Expose
    private FaceAttributes faceAttributes;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FaceRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public FaceAttributes getFaceAttributes() {
        return faceAttributes;
    }

    public void setFaceAttributes(FaceAttributes faceAttributes) {
        this.faceAttributes = faceAttributes;
    }

}
