package com.example.project_mobile.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_mobile.API.Api;
import com.example.project_mobile.API.RetrofitClient;
import com.example.project_mobile.DatabaseHelper;
import com.example.project_mobile.R;
import com.example.project_mobile.BookAdapter;
import com.example.project_mobile.models.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private EditText searchEditText;
    private List<Book> bookList;
    private List<Book> filteredBookList;
    private ProgressBar progressBar;
    private ProgressBar searchProgressBar;
    private Handler handler = new Handler();
    private Runnable hideProgressRunnable;

    private TextView titleTextView;

    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchEditText = view.findViewById(R.id.searchEditText);
        progressBar = view.findViewById(R.id.progressBar);
        searchProgressBar = view.findViewById(R.id.searchProgressBar);
        titleTextView = view.findViewById(R.id.titleTextView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        bookList = new ArrayList<>();
        filteredBookList = new ArrayList<>();

        bookAdapter = new BookAdapter(requireContext(), filteredBookList);
        recyclerView.setAdapter(bookAdapter);

        fetchBooks();
        databaseHelper = new DatabaseHelper(requireContext());
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchProgressBar.setVisibility(View.VISIBLE);

                if (hideProgressRunnable != null) {
                    handler.removeCallbacks(hideProgressRunnable);
                }
                hideProgressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        filterBooks(s.toString());
                        searchProgressBar.setVisibility(View.GONE);
                    }
                };
                handler.postDelayed(hideProgressRunnable, 300);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void fetchBooks() {
        progressBar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getClient();
        Call<List<Book>> call = apiService.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    bookList.addAll(books);
                    filteredBookList.addAll(books);
                    bookAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch books", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void filterBooks(String query) {
        filteredBookList.clear();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                filteredBookList.add(book);
            }
        }
        bookAdapter.notifyDataSetChanged();
    }
}
