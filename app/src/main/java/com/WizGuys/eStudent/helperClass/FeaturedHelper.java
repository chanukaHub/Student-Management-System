package com.WizGuys.eStudent.helperClass;

public class FeaturedHelper {

    int image;
    String title,description;

    public FeaturedHelper(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public FeaturedHelper() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
