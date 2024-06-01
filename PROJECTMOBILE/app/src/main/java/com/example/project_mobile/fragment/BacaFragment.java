package com.example.project_mobile.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_mobile.API.Api;
import com.example.project_mobile.API.RetrofitClient;
import com.example.project_mobile.BacaAdapter;
import com.example.project_mobile.DatabaseHelper;
import com.example.project_mobile.R;
import com.example.project_mobile.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BacaFragment extends Fragment {

    private RecyclerView recyclerView;
    private BacaAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;
    private int ongoingCalls;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_baca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        bookList = new ArrayList<>();
        bookAdapter = new BacaAdapter(bookList);
        recyclerView.setAdapter(bookAdapter);

        databaseHelper = new DatabaseHelper(getActivity());

        SharedPreferences preferencesuser = getActivity().getSharedPreferences("username", MODE_PRIVATE);
        String userlog = preferencesuser.getString("usernamelog", "");
        int userId = databaseHelper.loginUser(userlog);
        bookList.clear();

        loadBooks(userId);
    }

    private void loadBooks(int userId) {
        Api api = RetrofitClient.getClient();
        Set<Integer> bookIdSet = databaseHelper.getBookBookmark(userId);
        List<Integer> bookIds = new ArrayList<>(bookIdSet);
        ongoingCalls = bookIds.size();

        if (ongoingCalls == 0) {
            // If there are no bookmarks, hide the progress bar immediately
            progressBar.setVisibility(View.GONE);
            return;
        }

        for (int bookid : bookIds) {
            Call<Book> call = api.getBooksById(bookid);
            call.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful()) {
                        Book books = response.body();
                        if (books != null) {
                            bookList.add(books);
                            bookAdapter.notifyDataSetChanged();
                        }
                    }
                    checkLoadingComplete();
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    checkLoadingComplete();
                }
            });
        }
    }

    private synchronized void checkLoadingComplete() {
        ongoingCalls--;
        if (ongoingCalls <= 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 1000); // Delay of 1 second
        }
    }
}
