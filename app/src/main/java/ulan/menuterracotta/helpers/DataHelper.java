package ulan.menuterracotta.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

import ulan.menuterracotta.Category.Category;
import ulan.menuterracotta.Food.Food;

/**
 * Created by User on 16.02.2018.
 */

public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORY_TITLE_ENG_COLUMN = "category_title_eng";
    public static final String CATEGORY_TITLE_TR_COLUMN = "category_title_tr";
    public static final String CATEGORY_TITLE_COLUMN = "category_title";
    public static final String CATEGORY_TITLE_KG_COLUMN = "category_title_kg";
    public static final String CATEGORY_ID_COLUMN = "category_id";
    public static final String CATEGORY_IMAGE_COLUMN = "category_image";
    public static final String TABLE_FOOD = "food";
    public static final String FOOD_DISH_COLUMN = "food_dish";
    public static final String STOCK_TYPE_COLUMN = "stock_type";
    public static final String STOCK_DESC_COLUMN = "stock_desc";
    public static final String STOCK_DESC_COLUMN_ENG = "stock_desc_eng";
    public static final String STOCK_DESC_COLUMN_TR = "stock_desc_tr";
    public static final String STOCK_DESC_COLUMN_KG = "stock_desc_kg";
    public static final String FOOD_TITLE_COLUMN = "food_title";
    public static final String FOOD_ID_COLUMN = "food_id";
    public static final String FOOD_TITLE_COLUMN_ENG = "food_title_eng";
    public static final String FOOD_TITLE_COLUMN_TR = "food_title_tr";
    public static final String FOOD_TITLE_COLUMN_KG = "food_title_kg";
    public static final String FOOD_DESCRIPTION_COLUMN = "food_description";
    public static final String FOOD_DESCRIPTION_COLUMN_ENG = "food_description_eng";
    public static final String FOOD_DESCRIPTION_COLUMN_TR = "food_description_tr";
    public static final String FOOD_DESCRIPTION_COLUMN_KG = "food_description_kg";
    public static final String FOOD_WEIGHT_COLUMN = "food_weight";
    public static final String FOOD_PRICE_COLUMN = "food_price";
    public static final String FOOD_IMG_COLUMN = "food_img";


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CATEGORY + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                CATEGORY_TITLE_COLUMN + " text," +
                CATEGORY_TITLE_ENG_COLUMN + " text," +
                CATEGORY_ID_COLUMN + " integer," +
                CATEGORY_TITLE_TR_COLUMN + " text," +
                CATEGORY_TITLE_KG_COLUMN + " text," +
                CATEGORY_IMAGE_COLUMN + " text);"
        );
        db.execSQL("create table " + TABLE_FOOD + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                FOOD_DESCRIPTION_COLUMN + " text," +
                FOOD_DESCRIPTION_COLUMN_KG + " text," +
                FOOD_DESCRIPTION_COLUMN_ENG + " text," +
                FOOD_DESCRIPTION_COLUMN_TR + " text," +
                FOOD_IMG_COLUMN + " text," +
                STOCK_TYPE_COLUMN + " text," +
                STOCK_DESC_COLUMN + " text," +
                STOCK_DESC_COLUMN_KG + " text," +
                STOCK_DESC_COLUMN_TR + " text," +
                STOCK_DESC_COLUMN_ENG + " text," +
                FOOD_WEIGHT_COLUMN + " text," +
                FOOD_TITLE_COLUMN + " text," +
                FOOD_ID_COLUMN + " text," +
                FOOD_TITLE_COLUMN_KG + " text," +
                FOOD_TITLE_COLUMN_ENG + " text," +
                FOOD_TITLE_COLUMN_TR + " text," +
                FOOD_PRICE_COLUMN + " integer," +
                FOOD_DISH_COLUMN + " integer);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCategory(Category category) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_TITLE_COLUMN, category.getTitle());
        contentValues.put(CATEGORY_TITLE_KG_COLUMN, category.getTitle_kg());
        contentValues.put(CATEGORY_ID_COLUMN, category.getId());
        contentValues.put(CATEGORY_TITLE_ENG_COLUMN, category.getTitle_eng());
        contentValues.put(CATEGORY_TITLE_TR_COLUMN, category.getTitle_tr());
        contentValues.put(CATEGORY_IMAGE_COLUMN, category.getImage());
        SQLiteDatabase db = getWritableDatabase();

       db.insert(TABLE_CATEGORY, null, contentValues);
        db.close();
    }

    public ArrayList<Category> getCategory() {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor =db.query(TABLE_CATEGORY, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                categories.add(getCategory(cursor));
            }
        }
        db.close();
        return categories;
    }

    public Category getCategory(Cursor cursor) {
        Category category = new Category();
        category.setImage(cursor.getString(cursor.getColumnIndex(CATEGORY_IMAGE_COLUMN)));
        category.setId(cursor.getInt(cursor.getColumnIndex(CATEGORY_ID_COLUMN)));
        if (Shared.language == null) {
            category.setTitle(cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_COLUMN)));
        } else {
            if (Shared.language.equals("ru"))
                category.setTitle(cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_COLUMN)));
            else if (Shared.language.equals("en"))
                category.setTitle(cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_ENG_COLUMN)));
            else if (Shared.language.equals("tr"))
                category.setTitle(cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_TR_COLUMN)));
            else
                category.setTitle(cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_KG_COLUMN)));
        }


        return category;
    }

    public String getTitleOfCategory(int id){
        String title="";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, null, CATEGORY_ID_COLUMN + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            if (Shared.language.equals("ru"))
            title = cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_COLUMN));
            else if (Shared.language.equals("en"))
                title = cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_ENG_COLUMN));
            else if(Shared.language.equals("tr"))
                title = cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_TR_COLUMN));
            else
                title = cursor.getString(cursor.getColumnIndex(CATEGORY_TITLE_KG_COLUMN));

        }

        db.close();
    return title;
    }

    public void deleteCategory() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_CATEGORY, null, null);
        db.close();
    }

    //--------------------------------------------------------------------------------------------

    public void addFood(Food food,String id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_DESCRIPTION_COLUMN, food.getDescription());
        contentValues.put(FOOD_DESCRIPTION_COLUMN_ENG, food.getDescription_eng());
        contentValues.put(FOOD_DESCRIPTION_COLUMN_TR, food.getDescription_tr());
        contentValues.put(FOOD_DESCRIPTION_COLUMN_KG, food.getDescription_kg());
        contentValues.put(FOOD_DISH_COLUMN, food.getDish());
        contentValues.put(FOOD_IMG_COLUMN, food.getImg());
        contentValues.put(FOOD_WEIGHT_COLUMN, food.getIngridients());
        contentValues.put(FOOD_PRICE_COLUMN, food.getPrice());
        contentValues.put(FOOD_TITLE_COLUMN, food.getTitle());
        contentValues.put(FOOD_TITLE_COLUMN_KG, food.getTitle_kg());
        contentValues.put(FOOD_TITLE_COLUMN_ENG, food.getTitle_eng());
        contentValues.put(FOOD_TITLE_COLUMN_TR, food.getTitle_tr());
        contentValues.put(FOOD_ID_COLUMN, id);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FOOD, null, contentValues);
        db.close();
    }

    public ArrayList<Food> getFood() {
        ArrayList<Food> foods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                foods.add(getFood(cursor));
            }
        }
        db.close();
        return foods;
    }


    public ArrayList<Food> getFoodByDish(int dish) {
        ArrayList<Food> foods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, null, FOOD_DISH_COLUMN + " =? ", new String[]{String.valueOf(dish)}, null, null, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                foods.add(getFood(cursor));

            }
        }
        db.close();
        return foods;
    }

    public Food getFood(Cursor cursor) {
        Food food = new Food();
        food.setImg(cursor.getString(cursor.getColumnIndex(FOOD_IMG_COLUMN)));

        Log.e("LANG",Shared.language);

        String type = cursor.getString(cursor.getColumnIndex(STOCK_TYPE_COLUMN));
        food.setStock_type(type);
        if (type!=null){
            if (Shared.language == null) {
               food.setStock_desc(cursor.getString(cursor.getColumnIndex(STOCK_DESC_COLUMN)));
            } else {
                if (Shared.language.equals("ru")) {
                    food.setStock_desc(cursor.getString(cursor.getColumnIndex(STOCK_DESC_COLUMN)));
                } else if (Shared.language.equals("en")) {
                    food.setStock_desc(cursor.getString(cursor.getColumnIndex(STOCK_DESC_COLUMN_ENG)));

                } else if (Shared.language.equals("tr")){
                    food.setStock_desc(cursor.getString(cursor.getColumnIndex(STOCK_DESC_COLUMN_TR)));

                }else {
                    food.setStock_desc(cursor.getString(cursor.getColumnIndex(STOCK_DESC_COLUMN_KG)));

                }
            }
        }
        if (Shared.language == null) {
            food.setTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN)));
            food.setDescription(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN)));
        } else {
            if (Shared.language.equals("ru")) {
                food.setTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN)));
            } else if (Shared.language.equals("en")) {
                food.setTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_ENG)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_ENG)));
            } else if (Shared.language.equals("tr")){
                food.setTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_TR)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_TR)));
            }else {
                food.setTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_KG)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_KG)));
            }
        }
        food.setTitle_eng(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_ENG)));
        food.setTitle_ru(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN)));
        food.setTitle_kg(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_KG)));
        food.setDescription_eng(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_ENG)));
        food.setDescription_kg(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_KG)));
        food.setTitle_tr(cursor.getString(cursor.getColumnIndex(FOOD_TITLE_COLUMN_TR)));
        food.setDescription_tr(cursor.getString(cursor.getColumnIndex(FOOD_DESCRIPTION_COLUMN_TR)));
        food.setDish(cursor.getInt(cursor.getColumnIndex(FOOD_DISH_COLUMN)));
        food.setIngridients(cursor.getString(cursor.getColumnIndex(FOOD_WEIGHT_COLUMN)));
        food.setPrice(cursor.getInt(cursor.getColumnIndex(FOOD_PRICE_COLUMN)));
        food.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));

        return food;
    }


    public void deleteFood() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FOOD, null, null);
        db.close();
    }


    public void readFood() {
        ArrayList<Food> foods = getFood();
        for (Food food : foods) {
            Log.e("Food", food.getImg()+"\n"+food.getTitle()+"\n"+food.getStock_type()+"\n"+food.getStock_desc());
        }
    }

    public void readCategory() {
        ArrayList<Category> foods = getCategory();
        for (Category food : foods) {
            Log.e("dsddds", food.getImage()+"\n"+food.getTitle()+"\n"+food.getTitle_eng()+"\n"+food.getTitle_tr());
        }
    }

    public void updateFood(String type,String desc,String desc_eng,String desc_tr,String desc_kg,String id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCK_TYPE_COLUMN,type);
        values.put(STOCK_DESC_COLUMN,desc);
        values.put(STOCK_DESC_COLUMN_ENG,desc_eng);
        values.put(STOCK_DESC_COLUMN_TR,desc_tr);
        values.put(STOCK_DESC_COLUMN_KG,desc_kg);
        db.update(TABLE_FOOD, values, "food_id="+id, null);
    }



}
