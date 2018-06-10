package ulan.menuterracotta.Food;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.util.ArrayList;

import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.Shared;


public class RVFoodAdapter extends RecyclerView.Adapter<RVFoodAdapter.PersonViewHolder> {
    Context context;
    Food vse;
    String nameOfStation;
    FoodListActivity activity;


    public class PersonViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, price, weight, text_stock,real_price;
        RelativeLayout relativeLayout;


        PersonViewHolder(final View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            weight = (TextView) itemView.findViewById(R.id.weight);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            relativeLayout = itemView.findViewById(R.id.action);
            text_stock = itemView.findViewById(R.id.text_stock);
            real_price = itemView.findViewById(R.id.real_price);

            ViewGroup root = (ViewGroup) itemView.findViewById(R.id.sss);
            setFont(root, Shared.mFont);


            itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FoodActivity.class);
                    intent.putExtra("dish", listVse.get(getAdapterPosition()).getDish());
                    intent.putExtra("position", getAdapterPosition());
                    activity.startActivityForResult(intent, 2);
                    //activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);


                }


            });


        }


    }

    ArrayList<Food> listVse;

    public RVFoodAdapter(FoodListActivity activity, Context context, ArrayList<Food> listVse) {
        this.listVse = listVse;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        try {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_new, viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        vse = new Food();
        vse = listVse.get(i);

        personViewHolder.title.setText(vse.getTitle());
        personViewHolder.price.setText(vse.getPrice() + " сом / kgs");
        if (vse.getIngridients().equals("0") || vse.getIngridients().equals("")) {
            personViewHolder.weight.setVisibility(View.INVISIBLE);
        }
        personViewHolder.real_price.setVisibility(View.GONE);

        personViewHolder.weight.setText(vse.getIngridients() + " гр / g ");
        if (vse.getStock_type() != null) {
            if (vse.getStock_type().equals("discount")) {
                personViewHolder.text_stock.setText("-"+vse.getStock_desc()+"%");
                personViewHolder.relativeLayout.setBackgroundResource(R.drawable.back_discount);
                personViewHolder.real_price.setVisibility(View.VISIBLE);
                personViewHolder.real_price.setPaintFlags(personViewHolder.real_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                personViewHolder.real_price.setText(vse.getPrice()+" сом / kgs");
                int p = vse.getPrice()-(Integer.parseInt(vse.getStock_desc())*vse.getPrice()/100);
                personViewHolder.price.setText(p+" сом / kgs");
            } else if (vse.getStock_type().equals("stock")) {

                personViewHolder.relativeLayout.setBackgroundResource(R.drawable.back_stock);
                personViewHolder.text_stock.setText(R.string.stock);

            } else if (vse.getStock_type().equals("new")) {
                personViewHolder.relativeLayout.setBackgroundResource(R.drawable.back_new_food);
                personViewHolder.text_stock.setText(R.string.new_food);
            }
        } else {
            personViewHolder.relativeLayout.setVisibility(View.GONE);
        }
        Glide.with(context).load(new File(vse.getImg())).into(personViewHolder.imageView);

    }


    @Override
    public int getItemCount() {
        return listVse.size();
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