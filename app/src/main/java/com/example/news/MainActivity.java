package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.news.API.APIClient;
import com.example.news.API.Models.Post;
import com.example.news.Adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> titleList = new ArrayList<>();
    public ArrayList<String> titleList2 = new ArrayList<>();
    private ListView lv;
    private List<Post> listPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listViev);


        Toolbar mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        lv = findViewById(R.id.listViev);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, titleList);
        lv.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://188.40.167.45:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIClient client = retrofit.create(APIClient.class);


        client.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                listPost = (List<Post>) response.body();
                lv.setAdapter(new PostAdapter(getApplicationContext(), listPost));

                if (response.code() == 200) {
                    String res = "";
                    for (Post post : response.body()) {
                        if (post.getImg() != null) {
                            titleList.add(post.getImg());
                            if (post.getTitle() != null) {
                                titleList2.add(post.getTitle() + "\n" + post.getClickUrl() + " - " + post.getTime());
                            }
                        }
                    }

                    ImageSlider imageSlider = findViewById(R.id.top_news);
                    List<SlideModel> slideModels = new ArrayList<>();
                    for (int i = 0; i < titleList.size(); i++) {
                        slideModels.add(new SlideModel(titleList.get(i), titleList2.get(i)));
                    }
                    imageSlider.setImageList(slideModels, true);

                } else {
                    Log.d("outRes", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("outRes", t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}


