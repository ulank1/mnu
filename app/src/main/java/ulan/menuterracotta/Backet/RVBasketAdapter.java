package ulan.menuterracotta.Backet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import ulan.menuterracotta.Food.Food;
import ulan.menuterracotta.Food.FoodActivity;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.Shared;


public class RVBasketAdapter extends RecyclerView.Adapter<RVBasketAdapter.PersonViewHolder> {
    Context context;
    Food vse;
    int price1 = 0;
    TextView totalPrice,obsluga,itog;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, remove, plus, minus;
        TextView title, price, weight, count;


        PersonViewHolder(final View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            weight = (TextView) itemView.findViewById(R.id.weight);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            remove = (ImageView) itemView.findViewById(R.id.remove);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            count = (TextView) itemView.findViewById(R.id.count);


            ViewGroup root = (ViewGroup) itemView.findViewById(R.id.sss);
            Shared.setFont(root, Shared.mFont);

            minus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Food vse = Shared.foods.get(getAdapterPosition());
                    if (vse.getCount() > 0) {
                        Shared.foods.get(getAdapterPosition()).setCount(vse.getCount() - 1);

                        int priceSingle = Shared.foods.get(getAdapterPosition()).getPrice();
                        if (Shared.foods.get(getAdapterPosition()).getStock_type().equals("discount")){
                            priceSingle-=priceSingle*Integer.parseInt(Shared.foods.get(getAdapterPosition()).getStock_desc())/100;
                        }

                        price1 -= priceSingle;
                        vse = Shared.foods.get(getAdapterPosition());
                        count.setText(vse.getCount() + "");
                        totalPrice.setText(context.getString(R.string.sum_order)+" "+price1+" сом / kgs");
                        obsluga.setText(context.getString(R.string.obsluga)+" "+price1/10+" сом / kgs");
                        itog.setText(context.getString(R.string.itogo)+" "+(price1+price1/10)+" сом / kgs");
                    }
                }
            });
            plus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Food vse = Shared.foods.get(getAdapterPosition());

                    Shared.foods.get(getAdapterPosition()).setCount(vse.getCount() + 1);
                    int priceSingle = Shared.foods.get(getAdapterPosition()).getPrice();
                    if (Shared.foods.get(getAdapterPosition()).getStock_type().equals("discount")){
                        priceSingle-=priceSingle*Integer.parseInt(Shared.foods.get(getAdapterPosition()).getStock_desc())/100;
                    }

                    price1 += priceSingle;
                    Log.e("PRICE", vse.getPrice() + "");
                    vse = Shared.foods.get(getAdapterPosition());
                    count.setText(vse.getCount() + "");
                    totalPrice.setText(context.getString(R.string.sum_order)+" "+price1+" сом / kgs");
                    obsluga.setText(context.getString(R.string.obsluga)+" "+price1/10+" сом / kgs");
                    itog.setText(context.getString(R.string.itogo)+" "+(price1+price1/10)+" сом / kgs");

                }
            });

            remove.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s;
                    if (Shared.language.equals("ru")){
                        s=Shared.foods.get(getAdapterPosition()).getTitle_ru();
                    }else if (Shared.language.equals("en")){
                        s=Shared.foods.get(getAdapterPosition()).getTitle_eng();
                    }else if (Shared.language.equals("tr")){
                        s=Shared.foods.get(getAdapterPosition()).getTitle_tr();
                    }else  s=Shared.foods.get(getAdapterPosition()).getTitle_kg();
                alertPush(context.getString(R.string.remove_from_order)+"\n\""+ s+"\"",getAdapterPosition());
                }
            });


        }


    }
    public void alertPush(String s, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = getLayoutInflater();

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

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

                Shared.foods.remove(position);
                notifyDataSetChanged();
                price1 = 0;
                for (int i = 0; i < Shared.foods.size(); i++) {

                    int priceSingle = Shared.foods.get(i).getPrice();
                    if (Shared.foods.get(i).getStock_type().equals("discount")){
                        priceSingle-=priceSingle*Integer.parseInt(Shared.foods.get(i).getStock_desc())/100;
                    }

                    price1 += priceSingle * Shared.foods.get(i).getCount();

                }


                totalPrice.setText(context.getString(R.string.sum_order)+" "+price1+" сом / kgs");
                obsluga.setText(context.getString(R.string.obsluga)+" "+price1/10+" сом / kgs");
                itog.setText(context.getString(R.string.itogo)+" "+(price1+price1/10)+" сом / kgs");
                SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                if (Shared.foods.size()!=0) {
                    Basket basket = new Basket();
                    basket.setFoods(Shared.foods);

                    Gson gson = new Gson();
                    String json = gson.toJson(basket);
                    //Log.e("ATGsss",json);
                    sharedPreferences.edit().putString(APP_PREFERENCES_BASKET, json).apply();
                }else sharedPreferences.edit().putString(APP_PREFERENCES_BASKET, "").apply();


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

    ArrayList<Food> listVse;

    public RVBasketAdapter(Context context, TextView totalPrice,TextView obsluga,TextView itog) {
        this.totalPrice = totalPrice;
        this.context = context;
        this.obsluga = obsluga;
        this.itog = itog;
        for (int i = 0; i < Shared.foods.size(); i++) {

            int priceSingle = Shared.foods.get(i).getPrice();
            if (Shared.foods.get(i).getStock_type().equals("discount")){
                priceSingle-=priceSingle*Integer.parseInt(Shared.foods.get(i).getStock_desc())/100;
            }

            price1 += priceSingle * Shared.foods.get(i).getCount();

        }
        totalPrice.setText(context.getString(R.string.sum_order)+" "+price1+" сом / kgs");
        obsluga.setText(context.getString(R.string.obsluga)+" "+price1/10+" сом / kgs");
        itog.setText(context.getString(R.string.itogo)+" "+(price1+price1/10)+" сом / kgs");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        try {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_basket, viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        vse = new Food();
        vse = Shared.foods.get(i);
        if (Shared.language.equals("ru")){
            personViewHolder.title.setText(vse.getTitle_ru());
        }else if (Shared.language.equals("en")){
            personViewHolder.title.setText(vse.getTitle_eng());
        }else if (Shared.language.equals("tr")){
            personViewHolder.title.setText(vse.getTitle_tr());
        }else personViewHolder.title.setText(vse.getTitle_kg());

        int priceSingle = Shared.foods.get(i).getPrice();
        if (Shared.foods.get(i).getStock_type().equals("discount")){
            priceSingle-=priceSingle*Integer.parseInt(Shared.foods.get(i).getStock_desc())/100;
        }
        personViewHolder.price.setText(priceSingle + " сом / kgs");
        personViewHolder.count.setText(vse.getCount() + "");
        Glide.with(context).load(new File(vse.getImg())).asBitmap().centerCrop().into(new BitmapImageViewTarget(personViewHolder.imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(10f);
                personViewHolder.imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
        Log.e("DISH", vse.getDish() + "");

    }


    @Override
    public int getItemCount() {
        return Shared.foods.size();
    }
}