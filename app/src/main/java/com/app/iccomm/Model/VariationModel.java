package com.app.iccomm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class VariationModel implements Parcelable {
    private int variation_id;
    private String variation_name;
    private List<VariationModel> variation_data = new ArrayList<>();
    private int variation_data_id;
    private String variation_data_value;
    private String variationColorName;
    private String vPrice;

    public VariationModel() {
    }

    protected VariationModel(Parcel in) {
        variation_id = in.readInt();
        variation_name = in.readString();
        variation_data = in.createTypedArrayList(VariationModel.CREATOR);
        variation_data_id = in.readInt();
        variation_data_value = in.readString();
        variationColorName = in.readString();
        vPrice = in.readString();
    }

    public static final Creator<VariationModel> CREATOR = new Creator<VariationModel>() {
        @Override
        public VariationModel createFromParcel(Parcel in) {
            return new VariationModel(in);
        }

        @Override
        public VariationModel[] newArray(int size) {
            return new VariationModel[size];
        }
    };

    public int getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(int variation_id) {
        this.variation_id = variation_id;
    }

    public String getVariation_name() {
        return variation_name;
    }

    public void setVariation_name(String variation_name) {
        this.variation_name = variation_name;
    }

    public List<VariationModel> getVariation_data() {
        return variation_data;
    }

    public void setVariation_data(List<VariationModel> variation_data) {
        this.variation_data = variation_data;
    }

    public int getVariation_data_id() {
        return variation_data_id;
    }

    public void setVariation_data_id(int variation_data_id) {
        this.variation_data_id = variation_data_id;
    }

    public String getVariation_data_value() {
        return variation_data_value;
    }

    public void setVariation_data_value(String variation_data_value) {
        this.variation_data_value = variation_data_value;
    }

    public String getVariationColorName() {
        return variationColorName;
    }

    public void setVariationColorName(String variationColorName) {
        this.variationColorName = variationColorName;
    }

    public String getvPrice() {
        return vPrice;
    }

    public void setvPrice(String vPrice) {
        this.vPrice = vPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(variation_id);
        dest.writeString(variation_name);
        dest.writeTypedList(variation_data);
        dest.writeInt(variation_data_id);
        dest.writeString(variation_data_value);
        dest.writeString(variationColorName);
        dest.writeString(vPrice);
    }
}
