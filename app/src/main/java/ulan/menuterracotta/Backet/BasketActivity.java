package ulan.menuterracotta.Backet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import ulan.menuterracotta.BaseActivity;
import ulan.menuterracotta.Food.RVFoodAdapter;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.Shared;

public class BasketActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RVBasketAdapter adapter;
    TextView totalPrice,obsluga,summa;
    LinearLayout removeAll;

    String[] lang = {"ru","en","tr"};
    String[] langSpinner = {"Ру","Eng","Tr"};
    Spinner spinnerLang;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";
    boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        totalPrice = (TextView) findViewById(R.id.summa);
        obsluga = (TextView) findViewById(R.id.obslujivanie);
        summa = (TextView) findViewById(R.id.itog);
        removeAll = (LinearLayout) findViewById(R.id.ochistit);
        ViewGroup root = (ViewGroup) findViewById(R.id.sss);
        Shared.setFont(root, Shared.mFont);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        RelativeLayout back_pressed =  findViewById(R.id.relative_menu);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);


            }
        });
        adapter = new RVBasketAdapter(this,totalPrice,obsluga,summa);
        mRecyclerView.setAdapter(adapter);

        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertPush(getString(R.string.clear_order)+"?");

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

    private void reboot(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void alertPush(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.allert_dialog_basket, null);

        builder.setView(dialogView);
        TextView text_in_place_alert = (TextView) dialogView.findViewById(R.id.txt_in_place);
        LinearLayout btn_neutral =  dialogView.findViewById(R.id.dialog_neutral_btn3);
        LinearLayout okInPlace = dialogView.findViewById(R.id.okInPlace);
//        //Log.e("TAGGG",taksiSer.getDriver_name());
        text_in_place_alert.setText(s);

        final AlertDialog dialog = builder.create();

        okInPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Shared.foods.clear();
                Log.e("FOOD_SIZE",Shared.foods.size()+"");
                adapter = new RVBasketAdapter(BasketActivity.this,totalPrice,obsluga,summa);
                mRecyclerView.setAdapter(adapter);
                totalPrice.setText(getString(R.string.sum_order)+" 0 сом / kgs");
                obsluga.setText(getString(R.string.obsluga)+" 0 сом / kgs");
                summa.setText(getString(R.string.itogo)+" 0 сом / kgs");
                sharedPreferences.edit().putString(APP_PREFERENCES_BASKET,"").apply();
                setResult(RESULT_OK);
                finish();

                dialog.cancel();



            }
        });

        btn_neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog1
                dialog.cancel();

            }
        });
        dialog.show();
    }

}
