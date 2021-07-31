package com.example.a11;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/*

    the algorithm of the recycler view
    when using get position it means that now we entered the position inside the recycler view and can access the data inside it which is now the arraylist
    we created that will be books array list
    so that i can use the id to select either book 1 i created or book 2
 */

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder>{
    private static final String TAG = "BookRecViewAdapter";

    private ArrayList<Book> books = new ArrayList<>();
    private Context mContext;
    private String parentActivity;

    public BookRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imgBook);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext , BookActivity.class); // here the intent used to direct to another destination that is book activity that has full desciption to certain book
                    intent.putExtra("bookId",books.get(position).getId()); //here i'm passing the id of the book to the book activity as to access the book later by it;s id
                    mContext.startActivity(intent); // intent is activated when clicking on the holder that has all the books

                /*
                else if(books.get(position).getId()==2)
                {
                    Intent intent = new Intent(mContext ,BookActivity2.class); // here the intent used to direct to another destination that is book activity that has full desciption to certain book
                    mContext.startActivity(intent); // intent is activated when clicking on the holder that has all the books
                }
                 */
            }
        });

        holder.txtAuthor.setText((books.get(position).getAuthor()));
        holder.txtDescription.setText((books.get(position).getShortDesc()));

        if(books.get(position).isExpanded()) //if down arrow is expanded , the description will be visiable to see the short description
        {
            TransitionManager.beginDelayedTransition(holder.parent); // as to give animation when expanding by using the down arrow
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            if(parentActivity.equals("allBooks"))
            {
                holder.btnDelete.setVisibility(View.GONE);
            }
            else if(parentActivity.equals("alreadyRead"))
            {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are You Sure You Want To Delete This Book " + books.get(position).getName() + " ...?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.getInstance(mContext).removeFromAlreadyRead(books.get(position)))
                                {
                                    Toast.makeText(mContext,  "Book Is Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                });

            }
            else if(parentActivity.equals("wantToRead"))
            {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are You Sure You Want To Delete This Book " + books.get(position).getName() + " ...?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.getInstance(mContext).removeFromWantToRead(books.get(position)))
                                {
                                    Toast.makeText(mContext,  "Book Is Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                });
            }
            else if(parentActivity.equals("currentlyReading"))
            {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are You Sure You Want To Delete This Book " + books.get(position).getName() + " ...?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.getInstance(mContext).removeFromCurrentlyReading(books.get(position)))
                                {
                                    Toast.makeText(mContext,  "Book Is Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                });
            }
            else
            {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are You Sure You Want To Delete This Book " + books.get(position).getName() + " ...?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.getInstance(mContext).removeFromFavourite(books.get(position)))
                                {
                                    Toast.makeText(mContext,  "Book Is Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                });
            }
        }
        else // if the down arrow is not expanded so will not see the descrition so the rellayout that hold the desciption will be gone
        {
            TransitionManager.beginDelayedTransition(holder.parent); // as to give animation when not expanding  by using the down arrow
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();// this to refresh the data in the recycler view each time
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtName;
        private ImageView imgBook;
        private ImageView downArrow,upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView txtAuthor,txtDescription;
        private TextView btnDelete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            txtName = itemView.findViewById(R.id.txtBookName);
            imgBook = itemView.findViewById(R.id.imgBook);
            downArrow=itemView.findViewById(R.id.btnDownArrow);
            upArrow=itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout=itemView.findViewById(R.id.expandedRelLayout);
            txtAuthor=itemView.findViewById(R.id.txtAuthor);
            txtDescription=itemView.findViewById(R.id.txtShortDesc);
            btnDelete=itemView.findViewById(R.id.btnDelete);


            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //we can get the position like we did in line 42 for the position then name but there is another way that is
                    Book book = books.get(getAdapterPosition()); // so will get the Position of the book we're creating view holder for as the adapter standing in it now so after returning the book posiion so now we have posion of boook and will put all of it in the new class we're creating that is book also , then will access it
                    book.setExpanded(!book.isExpanded()); // book position returned from adapter will be accessed here  by making the expanded to be ( not the value , since the value is false , it will be now true)
                    notifyItemChanged(getAdapterPosition()); // to identify that something in the item that the adapter standing at , is changed


                }
            });
            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = books.get(getAdapterPosition()); // so will get the Position of the book we're creating view holder for as the adapter standing in it now so after returning the book posiion so now we have posion of boook and will put all of it in the new class we're creating that is book also , then will access it
                    book.setExpanded(!book.isExpanded()); // book position returned from adapter will be accessed here  by making the expanded to be ( not the value , since the value is false , it will be now true)
                    notifyItemChanged(getAdapterPosition()); // to identify that something in the item that the adapter standing at , is changed

                }
            });
        }
    }
}
