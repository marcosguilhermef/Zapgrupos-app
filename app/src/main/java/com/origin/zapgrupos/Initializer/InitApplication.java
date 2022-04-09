package com.origin.zapgrupos.Initializer;

import android.content.Context;

import com.origin.zapgrupos.repository.Services;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Arrays;
import java.util.List;


public class InitApplication implements Initializer<Services> {

    @NonNull
    @Override
    public Services create(@NonNull Context context) {
        return new Services();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Arrays.asList();
    }


}
