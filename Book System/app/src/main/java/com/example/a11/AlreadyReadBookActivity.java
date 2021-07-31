package com.example.a11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class AlreadyReadBookActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {  // since when i press on a book to already add , i will be in already book activity , so when pressing back this will
        // activate that to go directly to main activity like i'm doing on click listerner on the back button in already read book activity
        // but this will create an error as when pressing back , will direct to main activity but when pressing on back again will be directed to
        // alread read book activity again as due to history of application so will prevent this
        super.onBackPressed();

        Intent intent = new Intent(this,MainActivity.class);
        // this means when pressing back , i;m now deleting the history of application usage with flag activity clear and defining new task as flag activity new task
        // in other words i deleted to go back to already read book and now i'm on new direction of not going back and only quiting the application
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read_book);


        RecyclerView recyclerView  = findViewById(R.id.bookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this,"alreadyRead");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Book> allBooksAlreadyRead = Utils.getInstance(this).getAlreadyReadBooks();
        adapter.setBooks(allBooksAlreadyRead); // or i can use directly Utils.getAlreadyReadBooks directly without using get instance as it;s static s

    }
}

