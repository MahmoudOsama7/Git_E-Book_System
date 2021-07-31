package com.example.a11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;


/*

    algorithm is as follows
    1- the array is created here
    2- the recycler arapter is created in bookrecviewadapter
    3-then creating to constructors one to pass the context , other to pass array created here to array created in the recviewadapter
    4-then books is added here in this array and passed to recyclerview adapter of book to be added in the other array
    5-and arranged and for each one there is a position
    5- holder is the same for all of them but when clicking on psotion , to get position of certain book will use the holder to show it;s data for example when clicking
    to show the descrition or author    
 */

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        booksRecView = findViewById(R.id.booksRecView);
        adapter = new BookRecViewAdapter(this,"allBooks");

        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAllBooks()); // here we will use the utils class to access and to get the get all books class but to do that we need to use the constructor which is getinstance here

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {   // used as when click on the backbutton designed by the getsupportactionbar will do something
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}