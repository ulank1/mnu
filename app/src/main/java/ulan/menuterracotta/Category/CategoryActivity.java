package ulan.menuterracotta.Category;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import ulan.menuterracotta.Backet.Basket;
import ulan.menuterracotta.Backet.BasketActivity;
import ulan.menuterracotta.BaseActivity;
import ulan.menuterracotta.Food.FoodActivity;
import ulan.menuterracotta.MainActivity;
import ulan.menuterracotta.R;
import ulan.menuterracotta.Sync.SyncActivity;
import ulan.menuterracotta.helpers.DataHelper;
import ulan.menuterracotta.helpers.Shared;
import ulan.menuterracotta.recyclerviewfastscroll.FastScroller;

public class CategoryActivity extends BaseActivity {
    ViewPager pager;
    DataHelper dataHelper;

    PagerAdapter pagerAdapter;
    int spinnerPosition;
    String[] lang = {"ru","en","tr"};
    String[] langSpinner = {"Ру","Eng","Tr"};
    Spinner spinnerLang;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";

    ImageView vpered,nazad;
    boolean isFirst=true;
    TextView lang_menu,lang_order;
 //   ArrayList<FourCategories> fourCategories = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
       /* spinnerPosition = sharedPreferences.getInt(APP_PREFERENCES_LANG,0);
        Shared.language = lang[spinnerPosition];
        setLocale(lang[spinnerPosition]);*/
       Log.e("KKKK", Environment.getExternalStorageDirectory().getAbsolutePath());
       String root1 = Environment.getExternalStorageDirectory().toString()+"/android/menu";
        checkPermissionsState();
        File file = new File(root1);
        file.mkdirs();
        setContentView(R.layout.activity_category);
        Shared.margin_handle = getPixelsFromDPs(75);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(APP_PREFERENCES_BASKET, "");
        ViewGroup root = (ViewGroup) findViewById(R.id.sss);
        Shared.setFont(root, Shared.mFont);
        if (!json.equals("")){
            Basket settings = gson.fromJson(json, Basket.class);

            Shared.foods = settings.getFoods();
        }
        RelativeLayout relative_menu = (RelativeLayout) findViewById(R.id.relative_menu);
        relative_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
             // startActivity(new Intent(CategoryActivity.this, SyncActivity.class));

            }
        });




        dataHelper = new DataHelper(this);

        categories = dataHelper.getCategory();

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setHasFixedSize(true);
        RVCategoryAdapter adapter = new RVCategoryAdapter(this,categories);
        recyclerView.setAdapter(adapter);
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast);
        fastScroller.setRecyclerView(recyclerView);
        LinearLayout layout = (LinearLayout) findViewById(R.id.line1);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CategoryActivity.this, BasketActivity.class),1);
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });



    }


    public double getPixelsFromDPs(int dps){

        Resources r = getResources();


        double  px =  (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
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
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }else  if (requestCode==2&&resultCode==RESULT_OK){
           startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }
    private void checkPermissionsState() {
        int internetPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int networkStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE);

        int writeExternalStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int wifiStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE);

        int cameraStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        int callPhoneStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);


        if (internetPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                networkStatePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                writeExternalStoragePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                readExternalStoragePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                fineLocationPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                cameraStatePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                callPhoneStatePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                wifiStatePermissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.e("CheckPermission","TRUE");

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.ACCESS_WIFI_STATE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    boolean somePermissionWasDenied = false;
                    for (int result : grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            somePermissionWasDenied = true;
                        }
                    }

                    if (somePermissionWasDenied) {
                        // Toast.makeText(this, "Cant load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    // Toast.makeText(this, "Cant load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
                }


                return;
            }

        }
    }

}
