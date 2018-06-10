package ulan.menuterracotta.Food;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import ulan.menuterracotta.Backet.Basket;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.Shared;

public class FoodFragment extends Fragment {

    static final String ARGUMENT_PAGE_POSTER = "arg_page_number";
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";
  Food food;
  Animation grow,umenwenie;

    public static FoodFragment newInstance(Context context, Food category) {

        FoodFragment pageFragment = new FoodFragment();
        Bundle arguments = new Bundle();
        // String poster= cursor.getString(cursor.getColumnIndex(DataHelper.POSTER_COLUMN));

        arguments.putSerializable(ARGUMENT_PAGE_POSTER, category);

        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    public FoodFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        food = (Food) getArguments().getSerializable(ARGUMENT_PAGE_POSTER);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_food_fragment, null);

        ViewGroup root = (ViewGroup) view.findViewById(R.id.sss);
        Shared.setFont(root, Shared.mFont);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView weight = (TextView) view.findViewById(R.id.weight);
        TextView stock_desc = (TextView) view.findViewById(R.id.tv_stock_desc);
        TextView stock_type = (TextView) view.findViewById(R.id.tv_stock_type);
        ImageView stock_image = view.findViewById(R.id.stock_image);
        LinearLayout stock_line = view.findViewById(R.id.line_pounds);
        TextView real_price = view.findViewById(R.id.real_price);

        final TextView description = (TextView) view.findViewById(R.id.description);

        final ImageView dddd = (ImageView) view.findViewById(R.id.dddd);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.vybrat);
        Log.e("LOO",food.getIngridients()+"");
        title.setText(food.getTitle());
        price.setText(food.getPrice()+" сом / kgs");
        if (food.getIngridients().equals("0")||food.getIngridients().equals(""))
            weight.setVisibility(View.INVISIBLE);
        weight.setText(food.getIngridients()+" гр / g");
        description.setText(food.getDescription());

        if (food.getStock_type()!=null){
            if (food.getStock_type().equals("discount")) {

                stock_type.setText("-"+food.getStock_desc()+"%");
                stock_image.setImageResource(R.drawable.back_discount);
                real_price.setVisibility(View.VISIBLE);
                real_price.setPaintFlags(real_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                real_price.setText(food.getPrice()+" сом / kgs");
                int p = food.getPrice()-(Integer.parseInt(food.getStock_desc())*food.getPrice()/100);
                price.setText(p+" сом / kgs");
            } else if (food.getStock_type().equals("stock")) {
                stock_desc.setText(food.getStock_desc());
               stock_type.setText(R.string.stock);
                stock_image.setImageResource(R.drawable.back_stock);

            } else if (food.getStock_type().equals("new")) {
                stock_image.setImageResource(R.drawable.back_new_food);
                stock_type.setText(R.string.new_food);
            }
        }else {
            stock_line.setVisibility(View.GONE);
        }

        umenwenie = AnimationUtils.loadAnimation(getActivity(), R.anim.umenwenie);
        grow = AnimationUtils.loadAnimation(getActivity(), R.anim.grow_vybrat);

        grow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dddd.startAnimation(umenwenie);
                FoodActivity.layout.startAnimation(umenwenie);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("1",Shared.foods.size()+"");
                boolean bool = true;
                for (int  i = 0  ; i<Shared.foods.size();i++){
                    if(Shared.foods.get(i).getTitle_ru().equals(food.getTitle_ru())) bool = false;
                }
                if (bool) {
                    food.setCount(1);
                    Shared.foods.add(food);

                    FoodActivity.order_count.setText(Shared.foods.size()+"");

                    Basket basket = new Basket();
                    basket.setFoods(Shared.foods);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = gson.toJson(basket);
                    //Log.e("ATGsss",json);
                    sharedPreferences.edit().putString(APP_PREFERENCES_BASKET, json).apply();

                    ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(dddd,
                            PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                            PropertyValuesHolder.ofFloat("scaleY", 1.1f));
                    scaleDown.setDuration(300);
                    scaleDown.start();
                    ObjectAnimator scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(FoodActivity.layout,
                            PropertyValuesHolder.ofFloat("scaleX", 0.9f),
                            PropertyValuesHolder.ofFloat("scaleY", 0.9f));
                    scaleDown1.setDuration(300);
                    scaleDown1.start();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(dddd,
                                    PropertyValuesHolder.ofFloat("scaleX", 1.0f),
                                    PropertyValuesHolder.ofFloat("scaleY", 1.0f));
                            scaleDown.setDuration(300);
                            scaleDown.start();
                            ObjectAnimator scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(FoodActivity.layout,
                                    PropertyValuesHolder.ofFloat("scaleX", 1.0f),
                                    PropertyValuesHolder.ofFloat("scaleY", 1.0f));
                            scaleDown1.setDuration(300);
                            scaleDown1.start();
                        }
                    },350);

                }
                Log.e("2",Shared.foods.size()+"");
            }
        });


        return view;
    }

    public static void move(final ImageView view){
        ValueAnimator va = ValueAnimator.ofFloat(0f, 11f);
        int mDuration = 3000; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setPadding(0 ,0,(int)(float)animation.getAnimatedValue(),0);

            }
        });
        va.start();
    }

}