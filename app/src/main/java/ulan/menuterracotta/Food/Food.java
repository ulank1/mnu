package ulan.menuterracotta.Food;

import java.io.Serializable;

/**
 * Created by User on 16.02.2018.
 */

public class Food implements Serializable {
    String title,description,ingridients,img,title_eng,title_tr,description_eng,description_tr,title_ru,title_kg,description_kg,stock_desc_eng,stock_desc_tr,stock_desc_kg,stock_desc,stock_type;
    int price,dish,count,id;



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

    public String getIngridients() {
        return ingridients;
    }

    public void setIngridients(String ingridients) {
        this.ingridients = ingridients;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getDescription_eng() {
        return description_eng;
    }

    public void setDescription_eng(String description_eng) {
        this.description_eng = description_eng;
    }

    public String getDescription_tr() {
        return description_tr;
    }

    public void setDescription_tr(String description_tr) {
        this.description_tr = description_tr;
    }

    public int getDish() {
        return dish;
    }

    public void setDish(int dish) {
        this.dish = dish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public String getTitle_kg() {
        return title_kg;
    }

    public void setTitle_kg(String title_kg) {
        this.title_kg = title_kg;
    }

    public String getDescription_kg() {
        return description_kg;
    }

    public void setDescription_kg(String description_kg) {
        this.description_kg = description_kg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStock_desc_eng() {
        return stock_desc_eng;
    }

    public void setStock_desc_eng(String stock_desc_eng) {
        this.stock_desc_eng = stock_desc_eng;
    }

    public String getStock_desc_tr() {
        return stock_desc_tr;
    }

    public void setStock_desc_tr(String stock_desc_tr) {
        this.stock_desc_tr = stock_desc_tr;
    }

    public String getStock_desc_kg() {
        return stock_desc_kg;
    }

    public void setStock_desc_kg(String stock_desc_kg) {
        this.stock_desc_kg = stock_desc_kg;
    }

    public String getStock_desc() {
        return stock_desc;
    }

    public void setStock_desc(String stock_desc) {
        this.stock_desc = stock_desc;
    }

    public String getStock_type() {
        return stock_type;
    }

    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }
}
