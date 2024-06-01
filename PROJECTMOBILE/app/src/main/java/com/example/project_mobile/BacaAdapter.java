package com.example.project_mobile;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class BacaAdapter extends RecyclerView.Adapter<BacaAdapter.BacaViewHolder> {

    private List<Book> bookList;

    public BacaAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }
    int randomImageId = (int) (Math.random() * 1000) + 1;
    String imageUrl = "https://picsum.photos/200/300?random=" + randomImageId;

    @NonNull
    @Override
    public BacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book, parent, false);
        return new BacaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BacaViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id",book.getId());
                intent.putExtra("coverimage",imageUrl);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BacaViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView;
        ImageView coverImageView;
        ImageButton buttonIcon;
        RecyclerView recyclerView;
        DatabaseHelper databaseHelper;


        public BacaViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            coverImageView = itemView.findViewById(R.id.bookImageView);
            buttonIcon = itemView.findViewById(R.id.bookIconButton);
            recyclerView = itemView.findViewById(R.id.recyclerView1);
            databaseHelper = new DatabaseHelper(itemView.getContext());

            buttonIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("username", Context.MODE_PRIVATE);
                        String username = sharedPreferences.getString("usernamelog", "");
                        Book selectedBook = bookList.get(position);
                        int userid = databaseHelper.loginUser(username);
                        int bookid = selectedBook.getId();
                        databaseHelper.deleteBook(bookid, userid);
                        bookList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

        }

        public void bind(Book book) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());

            if (itemView.getContext() != null) {
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .into(coverImageView);
            }
        }
    }
}
