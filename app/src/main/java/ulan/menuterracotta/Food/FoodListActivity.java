package ulan.menuterracotta.Food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import ulan.menuterracotta.Backet.BasketActivity;
import ulan.menuterracotta.BaseActivity;
import ulan.menuterracotta.Category.CategoryActivity;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.DataHelper;
import ulan.menuterracotta.helpers.Shared;
import ulan.menuterracotta.recyclerviewfastscroll.FastScroller;

public class FoodListActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RVFoodAdapter adapter;
    ArrayList<Food> foods;
    DataHelper dataHelper;
    int spinnerPosition;
    String[] lang = {"ru","en","tr"};
    String[] langSpinner = {"Ру","Eng","Tr"};
    Spinner spinnerLang;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    boolean isFirst = true;
    int dish;
    RelativeLayout relative_menu;
    TextView text_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_food_new);
        dataHelper = new DataHelper(this);

        dataHelper.readFood();


        ViewGroup root = (ViewGroup) findViewById(R.id.sss);
        setFont(root, Shared.mFont);

        dish = getIntent().getIntExtra("dish",2);
        foods = dataHelper.getFoodByDish(dish);


        text_category = (TextView) findViewById(R.id.text_category);

        text_category.setText(dataHelper.getTitleOfCategory(dish));
        relative_menu = (RelativeLayout) findViewById(R.id.relative_menu);
        relative_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(verticalLayoutmanager);
        mRecyclerView.setHasFixedSize(true);

        adapter = new RVFoodAdapter( this,this,foods);
        mRecyclerView.setAdapter(adapter);

        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast);
        fastScroller.setRecyclerView(mRecyclerView);
        LinearLayout layout = (LinearLayout) findViewById(R.id.line1);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(FoodListActivity.this, BasketActivity.class),1);
               // //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });
    }
    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);


    }
    public void setFont(ViewGroup group, Typeface font) {
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
    private void reboot(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            TextView order_count;
            order_count = (TextView) findViewById(R.id.order_count);
            if (Shared.foods.size()>0) order_count.setTextColor(Color.parseColor("#ffffff"));else order_count.setTextColor(Color.parseColor("#555555"));
            order_count.setText(Shared.foods.size()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();

        }else  if (requestCode==2&&resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();

        }
    }

}
