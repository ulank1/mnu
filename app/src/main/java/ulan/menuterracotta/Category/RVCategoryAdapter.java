package ulan.menuterracotta.Category;

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

import ulan.menuterracotta.Backet.Basket;
import ulan.menuterracotta.Food.Food;
import ulan.menuterracotta.Food.FoodListActivity;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.Shared;


public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.PersonViewHolder> {
    Context context;
    Category vse;
    int price1 = 0;
    TextView totalPrice,obsluga,itog;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LANG = "language";
    public static final String APP_PREFERENCES_BASKET = "basket";

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;


        PersonViewHolder(final View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);



            ViewGroup root = (ViewGroup) itemView.findViewById(R.id.sss);
            Shared.setFont(root, Shared.mFont);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FoodListActivity.class);
                    intent.putExtra("dish",listVse.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });


        }


    }


    ArrayList<Category> listVse;

    public RVCategoryAdapter(Context context, ArrayList<Category> categories) {
        listVse = categories;
        this.context = context;


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        try {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        vse = new Category();
        vse = listVse.get(i);

        Glide.with(context).load(vse.getImage()).into(personViewHolder.imageView);
        personViewHolder.title.setText(vse.getTitle());
    }


    @Override
    public int getItemCount() {
        return listVse.size();
    }
}