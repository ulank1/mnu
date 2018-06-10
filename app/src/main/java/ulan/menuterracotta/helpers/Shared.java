package ulan.menuterracotta.helpers;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ulan.menuterracotta.Backet.Basket;
import ulan.menuterracotta.Food.Food;

/**
 * Created by User on 20.02.2018.
 */

public class Shared {
    public static String language = "ru";
    public static Basket basket;
    public static ArrayList<Food> foods=new ArrayList<>();
    public static String[] count;
    public static Typeface mFont;
    public static double margin_handle;

    public static void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof EditText || v instanceof Button) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }
}
