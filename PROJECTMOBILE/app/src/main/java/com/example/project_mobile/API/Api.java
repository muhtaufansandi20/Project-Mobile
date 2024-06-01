package com.example.project_mobile.API;

import com.example.project_mobile.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Api {
    @GET("v1/books")
    Call<List<Book>> getBooks();

    @GET("v1/books/{id}")
    Call<Book> getBooksById(@Path("id") int id);
}

