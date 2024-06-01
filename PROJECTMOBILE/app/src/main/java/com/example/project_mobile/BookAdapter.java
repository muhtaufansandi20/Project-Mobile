package com.example.project_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_mobile.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private Context context;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView;
        ImageView coverImageView;
        ImageButton buttonIcon;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            coverImageView = itemView.findViewById(R.id.bookImageView);
            buttonIcon = itemView.findViewById(R.id.bookIconButton);

            buttonIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Book clickedBook = bookList.get(position);
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        SharedPreferences sharedPreferences = context.getSharedPreferences("username", Context.MODE_PRIVATE);
                        String usernamelog = sharedPreferences.getString("usernamelog", "");


                        int userid = databaseHelper.loginUser(usernamelog);
                        databaseHelper.insertBookmarkBook(clickedBook.getId(), userid);
                    }
                }
            });
        }

        public void bind(Book book) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
            int randomImageId = (int) (Math.random() * 1000) + 1;
            String imageUrl = "https://picsum.photos/200/300?random=" + randomImageId;
            Glide.with(context)
                    .load(imageUrl)
                    .into(coverImageView);
        }
    }
}
