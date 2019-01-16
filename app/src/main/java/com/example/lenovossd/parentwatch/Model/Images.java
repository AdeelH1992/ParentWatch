package com.example.lenovossd.parentwatch.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Images {
        ArrayList<String> Gallery = new ArrayList <>(  );

    public Images() {
    }

    public Images(ArrayList <String> gallery) {
        Gallery = gallery;
    }

    public ArrayList <String> getGallery() {
        return Gallery;
    }

    public void setGallery(ArrayList <String> gallery) {
        Gallery = gallery;
    }
}
