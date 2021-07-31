package com.example.a11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks,btnCurrentlyReadying,btnAlreadyRead,btnWantToReadBooks,btnFavourite,btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { //here the intent is used to direct to another page , when clicking on the allbooksactivity which hold the books
                Intent intent = new Intent(MainActivity.this,AllBooksActivity.class); // will use this to navigate to the other activity page i created that is all books activity it takes two parameters the 1st is the activity i'm standing in that is main activity and passed to the destination's class that is all books activity.class
                startActivity(intent); //used to start the process of the movement from activity main to the other activity all books  and the intent is activated when clicking on the btn that has the name of all books to show all books we have
            }
        });
        // will do this as to initiate the instance to initiate the constructor as to use it internakky and avoid returneining any nul from internally
        //as when goes internally without presence of this line no constructor is initiated so will return null so no functions can be accessed in utils class

        Utils.getInstance(this);
        btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AlreadyReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnWantToReadBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WantToReadActivity.class);
                startActivity(intent);
            }
        });
        btnCurrentlyReadying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CurrentlyReadingBooks.class);
                startActivity(intent);
            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FavouriteBooks.class);
                startActivity(intent);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed by the younger Android Developer Check my Facebook account link here  :) ");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent.putExtra("url","https://google.com");
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        });
    }

    private void initViews()
    {
        
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnCurrentlyReadying = findViewById(R.id.btnCurrentlyReadying);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnWantToReadBooks = findViewById(R.id.btnWantToReadBooks);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnAbout = findViewById(R.id.btnAbout);
    }
}