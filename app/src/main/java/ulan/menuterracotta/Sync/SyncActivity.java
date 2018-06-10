package ulan.menuterracotta.Sync;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ulan.menuterracotta.Category.Category;
import ulan.menuterracotta.Food.Food;
import ulan.menuterracotta.R;
import ulan.menuterracotta.helpers.DataHelper;

public class SyncActivity extends AppCompatActivity {
    String root;
    DataHelper dataHelper;
    ArrayList<String> images;
    int position=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        root = Environment.getExternalStorageDirectory().toString();

        File file = new File(root);
        file.mkdirs();
        dataHelper = new DataHelper(this);
        images = new ArrayList<>();

        getFood();



    }

    public void getImage(){
        String s = images.get(position);

        File file = new File(root+"/"+s);

        if (!file.exists())new DownloadFileFromURL().execute(s);else {
            position++;
            if (position>=images.size()){
                finish();
            }else
            getImage();
        }
    }

    public void fromJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("objects");
            for (int i = 0; i < array.length(); i++) {
                Food food = new Food();
                JSONObject object = array.getJSONObject(i);
                food.setDescription(object.getString("description"));
                food.setDescription_eng(object.getString("description_eng"));
                food.setDescription_tr(object.getString("description_tr"));
                food.setDescription_kg(object.getString("description_kg"));
                food.setIngridients(object.getString("weight"));
                String s = object.getString("img");
                String image = s.split("/")[s.split("/").length-1];
                food.setImg(root+"/"+image);
                images.add(image);

                try {
                    food.setPrice(object.getInt("price"));
                } catch (Exception e) {
                    e.printStackTrace();
                    food.setPrice(0);
                }
                JSONObject dish = object.getJSONObject("dish");
                food.setDish(dish.getInt("id"));
                food.setTitle(object.getString("title"));
                food.setTitle_eng(object.getString("title_eng"));
                food.setTitle_tr(object.getString("title_tr"));
                food.setTitle_kg(object.getString("title_kg"));
                String id = object.getString("id");
                dataHelper.addFood(food,id);
                /*boolean is_new = object.getBoolean("is_new_food");
                if (is_new){
                    dataHelper.updateFood("new","","","","",id);
                }*/
                if(i%3==1)  dataHelper.updateFood("new","","","","",id);
                else if (i%3==2) dataHelper.updateFood("discount","15","15","15","15",id);
                else dataHelper.updateFood("stock","jdsijfsdifsdoifio","jsdfojsdoijfoisjd","fsdfpjspdjfpdspfsd","iehruhirhuiduish",id);

            }

            dataHelper.readFood();
            getCategory();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Suke","sd");
        }

    }

    private void fromJsonCategory(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        JSONArray array = jsonObject.getJSONArray("objects");
        for (int i = 0; i < array.length(); i++) {
            Category category = new Category();
            JSONObject object = array.getJSONObject(i);
            String s = object.getString("img");

            String image = s.split("/")[s.split("/").length-1];
            category.setImage(root+"/"+image);
           images.add(image);
            category.setTitle(object.getString("title"));
            category.setTitle_eng(object.getString("title_eng"));
            category.setTitle_tr(object.getString("title_tr"));
            category.setTitle_kg(object.getString("title_kg"));
            category.setId(object.getInt("id"));
            dataHelper.addCategory(category);
        }
        dataHelper.readCategory();
        getImage();
    }


    public void getFood() {

        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url("http://46.229.212.4/api/v1/food/?format=json")


                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                 Log.e("RESPONSE_Finish",json);


                if (!json.equals("")) {
                    if (json!=null) {
                        dataHelper.deleteCategory();
                        dataHelper.deleteFood();
                    }
                }
                fromJson(json);


            }
        });
    }


    public void getCategory() {

        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url("http://46.229.212.4/api/v1/category/?format=json")


                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.e("RESPONSE_Finish",json);


                try {
                    fromJsonCategory(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }




    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           Log.e("sdsd","sdsd");
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {


                System.out.println("Downloading");
                URL url = new URL("http://46.229.212.4/media/images/"+f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file



                OutputStream output = new FileOutputStream(root+"/"+f_url[0]);
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }



        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            position++;
            if (position<images.size())
            getImage();
            else finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void getStock() {

        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url("http://46.229.212.4/api/v1/stock/?format=json")


                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.e("RESPONSE_Finish",json);



                fromJsonStock(json);


            }
        });
    }

    private void fromJsonStock(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("objects");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject stock = jsonArray.getJSONObject(i);
                String desc = stock.getString("description");
                String desc_eng = stock.getString("description_eng");
                String desc_tr = stock.getString("description_tr");
                String desc_kg = stock.getString("description_kg");

                JSONObject food = stock.getJSONObject("food");
                String id = food.getString("id");

                dataHelper.updateFood("stock",desc,desc_eng,desc_tr,desc_kg,id);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getDiscount() {

        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url("http://46.229.212.4/api/v1/discount/?format=json")


                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.e("RESPONSE_Finish",json);



                fromJsonDiscount(json);


            }
        });
    }

    private void fromJsonDiscount(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("objects");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject stock = jsonArray.getJSONObject(i);
                String desc = stock.getString("discount");


                JSONObject food = stock.getJSONObject("food");
                String id = food.getString("id");

                dataHelper.updateFood("discount",desc,desc,desc,desc,id);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
