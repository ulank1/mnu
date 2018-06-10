package ulan.menuterracotta.Category;

import java.io.Serializable;

/**
 * Created by User on 16.02.2018.
 */

public class Category implements Serializable {

    String title,image,title_eng,title_tr,title_kg;
    int id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public void setTitle_eng(String title_eng) {
        this.title_eng = title_eng;
    }

    public String getTitle_tr() {
        return title_tr;
    }

    public void setTitle_tr(String title_tr) {
        this.title_tr = title_tr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_kg() {
        return title_kg;
    }

    public void setTitle_kg(String title_kg) {
        this.title_kg = title_kg;
    }
}
