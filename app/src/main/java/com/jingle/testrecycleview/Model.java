package com.jingle.testrecycleview;

/**
 * Created by Administrator on 2017/7/17.
 */

public class Model {
    private String title, indexUrl, imageUrl;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Model(String title, String indexUrl, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.indexUrl = indexUrl;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}
