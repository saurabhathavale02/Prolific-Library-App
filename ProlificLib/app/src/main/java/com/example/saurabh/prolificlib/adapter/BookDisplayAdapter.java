package com.example.saurabh.prolificlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.data.ResponseParameter;
import com.example.saurabh.prolificlib.screen.BookDisplayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 10/14/16.
 */

public class BookDisplayAdapter extends RecyclerView.Adapter<BookDisplayAdapter.ViewHolder>
{

    public BookItemItemListener mlistener;
    public List<ResponseParameter> blist;
    public  int position;

    public BookDisplayAdapter(BookDisplayFragment context, BookItemItemListener listener)
    {
            this.mlistener = listener;
            this.blist = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bookname.setText(blist.get(position).getTitle());
        holder.author.setText(blist.get(position).getAuthor());

    }


    @Override
    public int getItemCount() {

        return blist.size();
    }

    public void addBook(List<ResponseParameter> bookNamelist)
    {
            blist.clear();
            blist.addAll(bookNamelist);
            notifyDataSetChanged();

    }

    public void deletebook(ResponseParameter responseParameter)
    {
        Log.i("text4","deletebook="+position);
        blist.remove(responseParameter);
        notifyDataSetChanged();

    }


    public void clearList()
    {
        blist.clear();
        notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bookname;
        TextView author;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            bookname = (TextView) view.findViewById(R.id.bookname);
            author = (TextView) view.findViewById(R.id.author);


        }

        @Override
        public void onClick(View view)
        {
            position = getAdapterPosition();
           // String SelectedBook = book.get(position);
            mlistener.onBookClick(blist.get(position));

        }
    }

    public interface BookItemItemListener {
        void onBookClick(ResponseParameter responseParameter);
    }
}