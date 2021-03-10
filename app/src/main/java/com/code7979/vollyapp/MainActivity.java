package com.code7979.vollyapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Bittu";
    TextView textView;
    private String url = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPostData(retrofit, 3, "id", "asc");

    }

    private void getPostData(Retrofit retrofit,int id, String sortBy, String sortingOrder) {
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ModelPost>> call = jsonPlaceHolderApi.getPosts(id, sortBy, sortingOrder);

        call.enqueue(new Callback<List<ModelPost>>() {
            @Override
            public void onResponse(Call<List<ModelPost>> call, Response<List<ModelPost>> response) {
                if(!response.isSuccessful()){
                    textView.setText("" + response.code());
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }

                List<ModelPost> modelPosts = response.body();

                for (ModelPost mp: modelPosts
                     ) {
                    String content = "Id :"+mp.getId()+"\n"+
                            "User Id :"+mp.getUserId()+"\n"+
                            "Title :"+mp.getTitle()+"\n"+
                            "Body :"+mp.getBody()+"\n\n";
                    textView.append(content);
                    Log.d(TAG, "onResponse: "+content);
                }
            }

            @Override
            public void onFailure(Call<List<ModelPost>> call, Throwable t) {

            }
        });
    }

    private void getCommentData(Retrofit retrofit) {

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ModelComment>> call = jsonPlaceHolderApi.getComment(2);

        call.enqueue(new Callback<List<ModelComment>>() {
            @Override
            public void onResponse(Call<List<ModelComment>> call, Response<List<ModelComment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("" + response.code());
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                List<ModelComment> modelComments = response.body();
                assert modelComments != null;
                for (ModelComment mc : modelComments) {
                    String content = "post id :" + mc.getId() + "\n" +
                            "id :" + mc.getPostId() + "\n" +
                            "name :" + mc.getName() + "\n" +
                            "email :" + mc.getName() + "\n" +
                            "body :" + mc.getName() + "\n\n";

                    textView.append(content);
                    Log.d(TAG, "onResponse: "+content);
                }
            }

            @Override
            public void onFailure(Call<List<ModelComment>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}