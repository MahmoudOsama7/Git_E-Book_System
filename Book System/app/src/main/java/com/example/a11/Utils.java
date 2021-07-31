package com.example.a11;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/*
the downbelow constractor we're using as only to return one constractor s
    utils is the constructor but to make sure only called once will make it inside a new method to be the new constructor and will call it instance
    so when called if for the first time it will be = null so will create new utils and will put it in the instance
    so when accessing get instance i'm accessing the utils constructor but only one time
    so to access it;s data need first to access to access get instance by using the class utils then access getinstance that will be now the constrcutor then to
    access any data like allbooks or already added books
    note that when we're creating static we used it as to to get the value in the array as it will be static because w're dealing with data we want to save
    like the database
    however now we're trying to implement database which will be done by using sharedspace so since we decleared the schared space as not static
    so we cannot access it in a static function , so all function that will access the shared space must be normal not static
    in order to static the shared space we need key to get the data we want to have
    note that in functions like return allbooks , return wanted books and so on i already converted the file returned from string to array again by using json format
    i can do the same in functions when adding and removing but i make use of what i did in line 24 so no need to return same line of codes
    i already will return the books and will use h=it however converstion from and  Gson will leave it to the original functions in line 24 of comments herr
    and when passing to gson i will add to the array using gson format
    for ex in add or remove i got the array returned by converting from json
    then add or remove from it
    then converting to json again by using toJson and using it;s key to pass it again
    in the get arraylists , we're passing the Json to the sharedspace
    in the conditions we're asking as if the null is returned so no lists are created so we're creating
    so algorithim is as follows
    1- we create the constructor and to avoid creating it multiple time we create the instance to ask first if created , will do nothing if not will create one
    2-then creating all the lists as array static normally
    then asking if they are found in the shared space , if found will do nothing if not will pass them to the shared space
    3-then askto add and remove from them by retriving the list by using the lists passed already to the shared space that are already retrieved from Json
    to convert from string to array
    4-then ask to add or remove from them by their keys then return them back again to the shared prefences
    this shared prefences has a key already for all of it;s that is created when creating the constructor
 */
public class Utils
{

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVOURITE_BOOKS = "favourite_books";
    private static Utils instance;
    private SharedPreferences sharedPreferences ;

    // the next static sentences only used when dealing with static array as to make database for one time usage however now
    //shared preference will make permemant memory so no need for them now
    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;
    private static ArrayList<Book> currentlyReadingBooks;
    private static ArrayList<Book> favouriteBooks;


    private void initData() // will use it to add new books to my list
    {
        // here the allbooks array is created and passed to the sharedprefences
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1,"1Q84","Haruki Murakami",1350,"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1483103331l/10357575.jpg","A Work of maddehing brilliance","Long Description"));
        //above line is to add the book which is of type class , but to save time we added the declartion directly instead of decaling a object of this book to use it;s constructor to add book in the array list
        books.add(new Book(2,"1Q85","Haruki Murakami2",1351,"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Harry_Potter_wordmark.svg/2180px-Harry_Potter_wordmark.svg.png","A Work of maddehing brilliance is amazing","Long Description 2"));
        SharedPreferences.Editor editor = sharedPreferences.edit();  // sharedprefencese.edit will redturn editor to be stored in editor we creted
        Gson gson = new Gson();  //creating object of gson library we implemented to use to convert files
        String text = gson.toJson(books);  // converting the data on books library to string and store it in text variable of string type
        editor.putString(ALL_BOOKS_KEY,text);
        editor.commit();
        // apply  will handle the data in the background thread and won't block main thread to user will interact normally with main application , however commit  write the data to the storage immediately so will block the interface with the user
    }
    public static Utils getInstance(Context context) //syncronized means that if many threads at the same time call this method , syncronized will ensure that it will be called one by one
    {
        if(null!=instance)
        {
            return instance;
        }
        else
        {
            instance= new Utils(context);

            return instance;
        }

    }
    private Utils(Context context)
    {
        sharedPreferences = context.getSharedPreferences("alternate_db",context.MODE_PRIVATE); // mode private means the sharedprefences is designed only for this application
        if(null==getAllBooks()) // if no books found so will initialize new all books but if already found will do nothing
        {
            initData(); // as to be called here when using the constructor which will be static and not called 2 times just only once
        }

        //shared prfrences to edit in each booklist of the book system
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();



        if(null==getAlreadyReadBooks())
        {
            // as to add the alreadyreadbooks to the shared space after asking if alread this list is already created already if created will do nothing if npt will create
            editor.putString(ALREADY_READ_BOOKS,gson.toJson(new ArrayList<Book>()));  // we initialized the array and put it to be passed to the gson as to be converted and the key for this to use again is ALREADY_READ_BOOKS
            editor.commit();
        }
        if(null==getWantToReadBooks())
        {
            //as to add the wanttoreadbooks to the shared space after asking if alread this list is already created already if created will do nothing if npt will create
            editor.putString(WANT_TO_READ_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(null==getCurrentlyReadingBooks())
        {
            //as to add the currently reading books to the shared dpace after asking if alread this list is already created already if created will do nothing if npt will create
            editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(null==getFavouriteBooks())
        {
            //as to add the favourite books to the shared space after asking if alread this list is already created already if created will do nothing if npt will create
            editor.putString(FAVOURITE_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }
    public ArrayList<Book> getAllBooks()
    {  // here we created gson and retrived the data stored in the shared space that is stored as string but we need to convert it again so will covert it to array by using concept of type that will create interface called type and will be defined with data type arraylist of type structure
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();  //we're definning a type token of type arraylist
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null),type);
        // the previous line means if null returned so we failed to retrieve data from sharedprences that means nothing is in the shared preferences
        return books;
    }
    public ArrayList<Book> getAlreadyReadBooks()
    {
        //here the data is retrieved from the Gson to be arraylist again to be used
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS,null),type);
        return books;
    }
    public ArrayList<Book> getWantToReadBooks()
    {
        //here the data is retrieved from the Gson to be arraylist again to be used
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS,null),type);
        return books;
    }
    public ArrayList<Book> getCurrentlyReadingBooks()
    {
        //here the data is retrieved from the Gson to be arraylist again to be used
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS,null),type);
        return books;
    }
    public ArrayList<Book> getFavouriteBooks()
    {
        //here the data is retrieved from the Gson to be arraylist again to be used
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS,null),type);
        return books;
    }
    public Book getBookById(int id)
    {
        ArrayList<Book> books = getAllBooks();
        if(null!=books)
        {
            for (Book b : books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }
    public boolean addToAlreadyRead(Book book) // will use this as to add the book i want to add to the already read list and will return this list to access it when clicking on already ready button
    {
        ArrayList<Book>books=getAlreadyReadBooks();
        if(null!=books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
                 // as the return type of the function is true , that indicates that book is added
            }
        }
        return false;
        // here the add(book) will add the book to the list and return boolean that will be true indicating that the book is added
        //however after using sharred prefrences , i first checked if there is a data got from already read book null or not , if not
        // so will remove it all then will add it again after adding the new book  same goes for all other like currently readying , favourite , want to read
    }
    public boolean addToWantToRead (Book book)
    {
        ArrayList<Book>books=getWantToReadBooks();
        if(null!=books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean addToCurrentlyReadBooks (Book book)
    {
        ArrayList<Book>books = getCurrentlyReadingBooks();
        if(null!=books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean addToFavourite (Book book)
    {
        ArrayList<Book> books = getFavouriteBooks();
        if(null!=books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITE_BOOKS);
                editor.putString(FAVOURITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean removeFromAlreadyRead(Book book)
    {
        //cannot do the same procedure like adding as the book that we're trying from the list is different from the book with same data already in the list
        //but the refernece is not so that there is no matching between them so cannot remove as it;s not there already
        //so when comparing here we're comparing by the ID so if matching will renove the book of this id in the returned book
        ArrayList<Book>books = getAlreadyReadBooks();
        if(null!=books)
        {
            for(Book b:books)
            {
                if(b.getId() == book.getId())
                {
                    if(books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromWantToRead(Book book)
    {
        //cannot do the same procedure like adding as the book that we're trying from the list is different from the book with same data already in the list
        //but the refernece is not so that there is no matching between them so cannot remove as it;s not there already
        ArrayList<Book>books = getWantToReadBooks();
        if(null!=books)
        {
            for(Book b:books)
            {
                if(b.getId() == book.getId())
                {
                    if(books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromFavourite(Book book)
    {
        //cannot do the same procedure like adding as the book that we're trying from the list is different from the book with same data already in the list
        //but the refernece is not so that there is no matching between them so cannot remove as it;s not there already
        ArrayList<Book>books = getFavouriteBooks();
        if(null!=books)
        {
            for(Book b:books)
            {
                if(b.getId() == book.getId())
                {
                    if(books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITE_BOOKS);
                        editor.putString(FAVOURITE_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromCurrentlyReading(Book book)
    {
        //cannot do the same procedure like adding as the book that we're trying from the list is different from the book with same data already in the list
        //but the refernece is not so that there is no matching between them so cannot remove as it;s not there already
        ArrayList<Book>books = getCurrentlyReadingBooks();
        if(null!=books)
        {
            for(Book b:books)
            {
                if(b.getId() == book.getId())
                {
                    if(books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
