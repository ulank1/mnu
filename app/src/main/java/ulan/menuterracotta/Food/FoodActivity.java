package ulan.menuterracotta.Food;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import ulan.menuterracotta.Backet.BasketActivity;
import ulan.menuterracotta.BaseActivity;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.DataHelper;
import ulan.menuterracotta.helpers.Shared;

public class FoodActivity extends BaseActivity {
    ViewPager pager;

    PagerAdapter pagerAdapter;
    DataHelper dataHelper;
    int dish;
    ArrayList<Food> foods;
    int position;
 RelativeLayout linearLayout;
    int spinnerPosition;
    String[] lang = {"ru","en","tr"};
    String[] langSpinner = {"Ру","Eng","Tr"};
    Spinner spinnerLang;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    boolean isFirst = true;
    public static TextView order_count;
    public static ImageView order_image;
    RelativeLayout vpered,nazad;
    ImageView imageView;
    Animation grow;
    ObjectAnimator scaleDown;
    public static  LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_food);
        ViewGroup root = (ViewGroup) findViewById(R.id.sss);
        Shared.setFont(root, Shared.mFont);
        grow = AnimationUtils.loadAnimation(this, R.anim.grow);
        pager = (ViewPager) findViewById(R.id.view_pager);

        order_count = (TextView) findViewById(R.id.order_count);
        order_image = (ImageView) findViewById(R.id.order_image);
        imageView = (ImageView) findViewById(R.id.image);

        layout = (LinearLayout) findViewById(R.id.line1);
        vpered =  findViewById(R.id.vperep);
        nazad = findViewById(R.id.nazad);
        RelativeLayout back_pressed = (RelativeLayout) findViewById(R.id.relative_menu);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(FoodActivity.this, BasketActivity.class),1);
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);


            }
        });





        dataHelper = new DataHelper(this);
        dish = getIntent().getIntExtra("dish",0);
        position = getIntent().getIntExtra("position",0);
        foods = dataHelper.getFoodByDish(dish);
        if (position==0){
            nazad.setVisibility(View.GONE);
        }else if (position==foods.size()-1) {
            vpered.setVisibility(View.GONE);
        }



        Glide.with(this).load(new File(foods.get(position).getImg())).into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleDown = ObjectAnimator.ofPropertyValuesHolder(imageView,
                        PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.1f));
                scaleDown.setDuration(2000);
                scaleDown.start();
            }
        },500);

        vpered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });




        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
            }
        });
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(position);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("POSITION",position+"");
                scaleDown.cancel();
                ObjectAnimator scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(imageView,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f));
                scaleDown1.setDuration(0);
                scaleDown1.start();

                Glide.with(FoodActivity.this).load(new File(foods.get(position).getImg())).into(imageView);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scaleDown = ObjectAnimator.ofPropertyValuesHolder(imageView,
                                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                                PropertyValuesHolder.ofFloat("scaleY", 1.1f));
                        scaleDown.setDuration(2000);
                        scaleDown.start();
                    }
                },500);


                nazad.setVisibility(View.VISIBLE);
                vpered.setVisibility(View.VISIBLE);
                if (position==0) {

                    nazad.setVisibility(View.GONE);

                }

                if(position == foods.size()-1) vpered.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }




    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

        }



        @Override
        public Fragment getItem(int position) {
           Food food = foods.get(position);


            return FoodFragment.newInstance(FoodActivity.this, food);

        }

        @Override
        public int getCount() {
            return foods.size();
        }

    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);


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

        }
    }
}
