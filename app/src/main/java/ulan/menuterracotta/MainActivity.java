package ulan.menuterracotta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import ulan.menuterracotta.Category.CategoryActivity;
import ulan.menuterracotta.Sync.SyncActivity;
import ulan.menuterracotta.helpers.Shared;

public class MainActivity extends BaseActivity {
    boolean isFirst = true;

    int spinnerPosition;
    String[] lang = {"ru","en","tr","ky"};
    String[] langSpinner = {"Русский","English","Türkçe","Кыргызча"};
    Spinner spinnerLang;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";
    ImageView sync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        spinnerPosition = sharedPreferences.getInt(APP_PREFERENCES_LANG,0);
        Shared.language = lang[spinnerPosition];
        setLocale(lang[spinnerPosition]);
        setContentView(R.layout.activity_main);
        spinnerLang = (Spinner) findViewById(R.id.spinner);
        TextView textView = (TextView) findViewById(R.id.ff);

        Shared.mFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Condensed.ttf");
        ViewGroup root = (ViewGroup) findViewById(R.id.sss);
        setFont(root, Shared.mFont);


        sync = (ImageView) findViewById(R.id.sync);



        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SyncActivity.class));
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiener_item, langSpinner);
        dataAdapter.setDropDownViewResource(R.layout.spiener_dropdown2);
        spinnerLang.setAdapter(dataAdapter);
        spinnerLang.setSelection(spinnerPosition);

        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirst) {
                    if (position == 0) {
                        reboot();
                    } else if (position == 1) {
                        reboot();
                    } else {
                        reboot();
                    }
                    sharedPreferences.edit().putInt(APP_PREFERENCES_LANG,position).apply();
                    Shared.language = lang[position];
                }
                isFirst=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.line);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);


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

    private void reboot(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

}
