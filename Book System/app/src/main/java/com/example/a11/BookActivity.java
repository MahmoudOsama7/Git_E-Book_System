package com.example.a11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();
/*
        String Desc = "Throughout the series, Harry is described as having his father's perpetually untidy black hair, his mother's bright green eyes, and a lightning bolt-shaped scar on his forehead. He is further described as \"small and skinny for his age\" with \"a thin face\" and \"knobbly knees\", and he wears round eyeglasses. In the first book, his scar is described as \"the only thing Harry liked about his own appearance\". When asked about the meaning behind Harry's lightning bolt scar, Rowling said, \"I wanted him to be physically marked by what he has been through. It was an outward expression of what he has been through inside... It is almost like being the chosen one or the cursed one, in a sense.\" Rowling has also stated that Harry inherited his parents' good looks.[26] In the later part of the series Harry grows taller, and by the seventh book is said to be 'almost' the height of his father, and 'tall' by other characters.[27]\n" +
                "\n" +
                "Rowling explained that Harry's image came to her when she first thought up Harry Potter, seeing him as a \"scrawny, black-haired, bespectacled boy\".[2] She also mentioned that she thinks Harry's glasses are the clue to his vulnerability.[28]";
        Book book = new Book(1,"1Q84","Haruki Murakami",1350,
                "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1483103331l/10357575.jpg",
                "A Work of maddehing brilliance",Desc);
 */

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra("bookId", -1); // as if nothing is passed like bookid , the default will passed as -1 as no id is having -1 which means no id now is found only id passed is -1 whic i'm not using
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId); // this will return the book (of type class ) that has this id i need to be put in incomingBook
                if (incomingBook != null) {
                    setData(incomingBook);
                    handleAlreadyRead(incomingBook);
                    handleWantToReaDBooks(incomingBook);
                    handleCurrentlyReadingBook(incomingBook);
                    handleAddToFavourite(incomingBook);
                }
            }
        }


    }

    private void handleWantToReaDBooks(final Book book)
    {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();
        boolean existInWantToReadBooks = false;
        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInWantToReadBooks = true;
            }
        }
        if (existInWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false); // as now the book already exist so i don't want to add it again so will disable the option of adding it
        } else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                                                              Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                                                              Intent intent = new Intent(BookActivity.this, WantToReadActivity.class); // as we inside the book activity interface
                                                              startActivity(intent);
                                                          } else {

                                                              Toast.makeText(BookActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                                  }
            );
        }
    }

    private void handleAlreadyRead(final Book book)
    {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existInAlreadyReadBooks = false;
        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
            }
        }
        if (existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false); // as now the book already exist so i don't want to add it again so will disable the option of adding it
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                                                               Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                                                               Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class); // as we inside the book activity interface
                                                               startActivity(intent);
                                                           } else {
                                                               Toast.makeText(BookActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }
            );
        }
    }

    private void handleCurrentlyReadingBook(final Book book)
    {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean existInCurrentlyReadingBooks = false;
        for (Book b : currentlyReadingBooks) {
            if (b.getId() == book.getId()) {
                existInCurrentlyReadingBooks = true;
            }
        }
        if (existInCurrentlyReadingBooks) {
            btnAddToCurrentlyReading.setEnabled(false); // as now the book already exist so i don't want to add it again so will disable the option of adding it
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           if (Utils.getInstance(BookActivity.this).addToCurrentlyReadBooks(book)) {
                                                               Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                                                               Intent intent = new Intent(BookActivity.this, CurrentlyReadingBooks.class); // as we inside the book activity interface
                                                               startActivity(intent);
                                                           } else {
                                                               Toast.makeText(BookActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }
            );
        }
    }

    private void handleAddToFavourite(final Book book)
    {
        ArrayList<Book> favouriteBooks = Utils.getInstance(this).getFavouriteBooks();
        boolean existInFavouriteBooks = false;
        for (Book b : favouriteBooks) {
            if (b.getId() == book.getId()) {
                existInFavouriteBooks = true;
            }
        }
        if (existInFavouriteBooks) {
            btnAddToFavourite.setEnabled(false); // as now the book already exist so i don't want to add it again so will disable the option of adding it
        } else {
            btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (Utils.getInstance(BookActivity.this).addToFavourite(book)) {
                                                                    Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(BookActivity.this, FavouriteBooks.class); // as we inside the book activity interface
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(BookActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
            );
        }
    }

    private void setData(Book book)  // used to give the data of the book created to the full descption page
    {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this).asBitmap().load(book.getImageUrl()).into(bookImage);
    }

    private void initView()  // for initialization normally
    {
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavourite = findViewById(R.id.btnAddToFavourite);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToReadList);

        bookImage = findViewById(R.id.imgBook);

    }
}