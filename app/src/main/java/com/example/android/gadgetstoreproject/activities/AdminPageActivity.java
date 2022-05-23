package com.example.android.gadgetstoreproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.api.JsonPlaceHolderApi;
import com.example.android.gadgetstoreproject.models.CommentModel;
import com.example.android.gadgetstoreproject.models.ItemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Comment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminPageActivity extends AppCompatActivity {

    private EditText item_name, item_description, item_price;
    private Button buttonAddItem;
    private TextView textViewAddItem;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        item_name = findViewById(R.id.item_name);
        item_description = findViewById(R.id.item_description);
        item_price = findViewById(R.id.item_price);
        textViewAddItem = findViewById(R.id.textViewAddItem);

        Gson gson = new GsonBuilder().serializeNulls().create();

        buttonAddItem = findViewById(R.id.btnAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                //getItems();
                //getComments();
                //createItem();
                //updateItem();
                deleteItem();
            }
        });
    }

    private void deleteItem() {
        ItemModel itemModel = new ItemModel(23,"New Title", "New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<ItemModel> call = jsonPlaceHolderApi.createItem(fields);

        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                textViewAddItem.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                textViewAddItem.setText(t.getMessage());
            }
        });
    }

    private void updateItem() {
        ItemModel itemModel = new ItemModel(23,"New Title", "New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<ItemModel> call = jsonPlaceHolderApi.createItem(fields);

        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if(!response.isSuccessful()){
                    textViewAddItem.setText("Code: " + response.code());
                    return;
                }

                ItemModel itemResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Id:  " + itemResponse.getId() + "\n";
                content += "User ID: " + itemResponse.getUserId() + "\n";
                content += "Title: " + itemResponse.getTitle() + "\n";
                content += "Text: " + itemResponse.getText() + "\n";

                textViewAddItem.setText(content);
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                textViewAddItem.setText(t.getMessage());
            }
        });
    }

    private void createItem() {
        ItemModel itemModel = new ItemModel(23,"New Title", "New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<ItemModel> call = jsonPlaceHolderApi.createItem(fields);

        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if(!response.isSuccessful()){
                    textViewAddItem.setText("Code: " + response.code());
                    return;
                }

                ItemModel itemResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Id:  " + itemResponse.getId() + "\n";
                content += "User ID: " + itemResponse.getUserId() + "\n";
                content += "Title: " + itemResponse.getTitle() + "\n";
                content += "Text: " + itemResponse.getText() + "\n";

                textViewAddItem.setText(content);
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                textViewAddItem.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        retrofit2.Call<List<CommentModel>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(!response.isSuccessful()){
                    textViewAddItem.setText("Code: " + response.code());
                    return;
                }
                List<CommentModel> comments = response.body();
                for(CommentModel comment: comments){
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Item Id: " + comment.getItemId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n";

                    textViewAddItem.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                textViewAddItem.setText(t.getMessage());
            }
        });
    }

    private void getItems() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        retrofit2.Call<List<ItemModel>> call = jsonPlaceHolderApi.getItem(parameters);
        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                if(!response.isSuccessful()){
                    textViewAddItem.setText("Code: " + response.code());
                    return;
                }
                List<ItemModel> itemModels = response.body();
                for(ItemModel itemModel: itemModels){
                    String content = "";
                    content += "ID: " + itemModel.getId() + "\n";
                    content += "User Id: " + itemModel.getUserId() + "\n";
                    content += "Title: " + itemModel.getTitle() + "\n";
                    content += "Text: " + itemModel.getText() + "\n";

                    textViewAddItem.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                textViewAddItem.setText(t.getMessage());
            }
        });
    }
}