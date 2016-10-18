package com.augmentis.ayp.mymovie.GsonShowtime;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Waraporn on 10/18/2016.
 */

public class MyCinema {
    @SerializedName("elements")
    List<Element> elements;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
