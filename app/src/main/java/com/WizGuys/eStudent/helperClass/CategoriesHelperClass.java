package com.WizGuys.eStudent.helperClass;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {

    int image;
    String title;
    Drawable gradientDrawable;

    public CategoriesHelperClass(Drawable gradientDrawable,int image, String title) {
        this.image = image;
        this.title = title;
        this.gradientDrawable = gradientDrawable;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setGradientDrawable(Drawable gradientDrawable) {
        this.gradientDrawable = gradientDrawable;
    }

    public Drawable gradientDrawable() {
        return gradientDrawable;
    }
}
