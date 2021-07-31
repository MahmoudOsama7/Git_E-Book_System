package com.example.a11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CurrentlyReadingBooks extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // activate that to go directly to main activity like i'm doing on click listerner on the back button in already read book activity
        // but this will create an error as when pressing back , will direct to main activity but when pressing on back again will be directed to
        // alread read book activity again as due to history of application so will prevent this
        Intent intent = new Intent(this,MainActivity.class);
        // this means when pressing back , i;m now deleting the history of application usage with flag activity clear and defining new task as flag activity new task
        // in other words i deleted to go back to already read book and now i'm on new direction of not going back and only quiting the application
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_reading_books);

        RecyclerView recyclerView  = findViewById(R.id.bookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this,"currentlyReading");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Book> allBooksCurrentlyReading = Utils.getInstance(this).getCurrentlyReadingBooks();
        adapter.setBooks(allBooksCurrentlyReading); // or i can use directly Utils.getwanttoreadbooks directly without using get instance as it;s static s

    }
}