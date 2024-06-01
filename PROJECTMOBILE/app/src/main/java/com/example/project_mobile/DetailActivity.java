package com.example.project_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.project_mobile.API.Api;
import com.example.project_mobile.API.RetrofitClient;
import com.example.project_mobile.models.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private List<Book> bookList;
    private TextView text_title,text_author,textpublication_year,text_genre,text_description;
    private ImageView image_cover;
    int randomImageId = (int) (Math.random() * 1000) + 1;
    String imageUrl = "https://picsum.photos/200/300?random=" + randomImageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        text_title = findViewById(R.id.text_title);
        text_author = findViewById(R.id.text_author);
        textpublication_year = findViewById(R.id.text_publication_year);
        text_genre = findViewById(R.id.text_genre);
        text_description = findViewById(R.id.text_description);
        image_cover = findViewById(R.id.image_cover);

        bookList = new ArrayList<>();

        int id = getIntent().getIntExtra("id",0);

        Api api = RetrofitClient.getClient();
        Call<Book> call = api.getBooksById(id);
            call.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful()) {
                        Book books = response.body();
                        if (books != null) {
                            bookList.add(books);
                            text_title.setText(books.getTitle());
                            text_author.setText(books.getAuthor());
                            textpublication_year.setText(books.getPublication_year());

                            for (String genre : books.getGenre()){
                                text_genre.setText(genre);
                            }


                            text_description.setText(books.getDescription());
                            Glide.with(DetailActivity.this)
                                    .load(imageUrl)
                                    .into(image_cover);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                }
            });



    }
}