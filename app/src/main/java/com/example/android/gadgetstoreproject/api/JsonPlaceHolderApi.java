package com.example.android.gadgetstoreproject.api;

import com.example.android.gadgetstoreproject.models.CommentModel;
import com.example.android.gadgetstoreproject.models.ItemModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<ItemModel>> getItem(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<ItemModel>> getItem(
            @QueryMap Map<String, String> parameters
    );

    @GET("posts/{id}/comments")
    Call<List<CommentModel>> getComments(@Path("id") int itemId);

    @GET
    Call<List<CommentModel>> getComments(@Url String url);

    @POST("posts")
    Call<ItemModel> createItem(@Body ItemModel itemModel);

    @FormUrlEncoded
    @POST("posts")
    Call <ItemModel> createItem(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call <ItemModel> createItem(@FieldMap Map<String, String> fields);

    @PUT("posts/{id}")
    Call<ItemModel> putItem(@Path("id") int id, @Body ItemModel itemModel);

    @PATCH("posts/{id}")
    Call<ItemModel> patchPost(@Path("id") int id, @Body ItemModel itemModel);
}
