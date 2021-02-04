package com.tribyssapps.gallery;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.jetbrains.annotations.NotNull;

@GlideModule
public class GlideAppModule extends AppGlideModule {
    @Override
    public void applyOptions(@NotNull Context context, @NotNull GlideBuilder builder) {
    }
}