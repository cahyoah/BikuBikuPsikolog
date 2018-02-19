package com.example.adhit.bikubikupsikolog.data.model;

import android.app.Fragment;

/**
 * Created by adhit on 04/01/2018.
 */

public class Fraggment {

    private Fragment fragment;
    private String title;
    private int image;

    public Fraggment(Fragment fragment, String title, int image) {
        this.fragment = fragment;
        this.title = title;
        this.image = image;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
