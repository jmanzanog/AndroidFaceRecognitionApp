
package com.manzano.jose.fundamentos.android.project.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceAttributes {

    @SerializedName("smile")
    @Expose
    private Double smile;
    @SerializedName("headPose")
    @Expose
    private HeadPose headPose;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Double age;
    @SerializedName("facialHair")
    @Expose
    private FacialHair facialHair;
    @SerializedName("glasses")
    @Expose
    private String glasses;
    @SerializedName("emotion")
    @Expose
    private Emotion emotion;
    @SerializedName("blur")
    @Expose
    private Blur blur;
    @SerializedName("exposure")
    @Expose
    private Exposure exposure;
    @SerializedName("noise")
    @Expose
    private Noise noise;
    @SerializedName("makeup")
    @Expose
    private Makeup makeup;
    @SerializedName("accessories")
    @Expose
    private List<Object> accessories = null;
    @SerializedName("occlusion")
    @Expose
    private Occlusion occlusion;
    @SerializedName("hair")
    @Expose
    private Hair hair;

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }

    public HeadPose getHeadPose() {
        return headPose;
    }

    public void setHeadPose(HeadPose headPose) {
        this.headPose = headPose;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public FacialHair getFacialHair() {
        return facialHair;
    }

    public void setFacialHair(FacialHair facialHair) {
        this.facialHair = facialHair;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public Blur getBlur() {
        return blur;
    }

    public void setBlur(Blur blur) {
        this.blur = blur;
    }

    public Exposure getExposure() {
        return exposure;
    }

    public void setExposure(Exposure exposure) {
        this.exposure = exposure;
    }

    public Noise getNoise() {
        return noise;
    }

    public void setNoise(Noise noise) {
        this.noise = noise;
    }

    public Makeup getMakeup() {
        return makeup;
    }

    public void setMakeup(Makeup makeup) {
        this.makeup = makeup;
    }

    public List<Object> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Object> accessories) {
        this.accessories = accessories;
    }

    public Occlusion getOcclusion() {
        return occlusion;
    }

    public void setOcclusion(Occlusion occlusion) {
        this.occlusion = occlusion;
    }

    public Hair getHair() {
        return hair;
    }

    public void setHair(Hair hair) {
        this.hair = hair;
    }

}
