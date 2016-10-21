package com.example.saurabh.prolificlib.screen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.saurabh.prolificlib.App;
import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.adapter.BookDisplayAdapter;
import com.example.saurabh.prolificlib.data.ResponseParameter;
import com.example.saurabh.prolificlib.data.component.DaggerBookDisplayComponent;
import com.example.saurabh.prolificlib.data.module.BookDisplayModule;
import com.example.saurabh.prolificlib.presenter.BookDisplayContract;
import com.example.saurabh.prolificlib.presenter.BookDisplayPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDisplayFragment extends Fragment implements BookDisplayContract.View,BookDisplayAdapter.BookItemItemListener
{

    @Inject
    BookDisplayPresenter bookDisplayPresenter;
    ProgressBar progressBar;
    BookDisplayAdapter bookDisplayAdapter;
    RecyclerView BookListRecycleView;
    FloatingActionButton AddnewBookButton;
    View v;
    OnBookSelectedListener mCallback;

    public BookDisplayFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        Log.i("Checking","Create Display Fragment");
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_book_display, container, false);
        initRecyclerView();
        AddnewBookButton=(FloatingActionButton) v.findViewById(R.id.fab);
        progressBar=(ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Log.i("Checking","Create onCreateView");
        return v;
    }

    public void onViewCreated(View v, Bundle savedInstanceState)
    {
        super.onViewCreated(v, savedInstanceState);


        DaggerBookDisplayComponent.builder()
                .netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .bookDisplayModule(new BookDisplayModule(this))
                .build().inject(this);
        if(((App) getActivity().getApplicationContext()).isNetworkAvailable()==true) {
            bookDisplayPresenter.loadBooks();
        }
        else
        {
            Toast.makeText(getActivity(),R.string.nointernet,Toast.LENGTH_SHORT).show();
        }


        AddnewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getActivity(),AddNewBook.class);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("Checking","Create onActivityCreated");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnBookSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBookSelectedListener");
        }
    }

    @Override
    public void initRecyclerView()
    {
        Log.i("Testing","initRecyclerView");
        System.out.println("init recycleview");
        BookListRecycleView=(RecyclerView) v.findViewById(R.id.booklistview);
        BookListRecycleView.setVisibility(View.GONE);
        BookListRecycleView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        bookDisplayAdapter = new BookDisplayAdapter(this,this);
        BookListRecycleView.setAdapter(bookDisplayAdapter);

    }

    @Override
    public void UpdateBookList()
    {
        BookListRecycleView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        bookDisplayPresenter.loadBooks();

    }


    @Override
    public void onBookClick(ResponseParameter selectedbook)
    {
        mCallback.onBookSelected(selectedbook);
    }

    @Override
    public void showBooks(List<ResponseParameter> responseParameters)
    {
        Log.i("Checking","In-:showBooks");
        BookListRecycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        ((App) getActivity().getApplicationContext()).NumberOfBooks=responseParameters.size();

        bookDisplayAdapter.clearList();
        Log.i("Checking123","total="+((App) getActivity().getApplicationContext()).NumberOfBooks);
        if(((App) getActivity().getApplicationContext()).NumberOfBooks>0)
        {
            bookDisplayAdapter.addBook(responseParameters);
        }
        else
        {
            Toast.makeText(getActivity(),R.string.no_book,Toast.LENGTH_SHORT).show();
        }




    }


    @Override
    public void showError(String message)
    {
        Log.i("Error Message","Error-:"+message);
        Toast.makeText(getActivity(),R.string.server_down,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete()
    {
        Log.i("Checking","In-:showComplete");
        BookListRecycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallback=null;

    }


    public interface OnBookSelectedListener
    {
        public void onBookSelected(ResponseParameter selectedbook);
    }

}
