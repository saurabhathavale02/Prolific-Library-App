package com.example.saurabh.prolificlib.screen;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurabh.prolificlib.App;
import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.data.ResponseParameter;
import com.example.saurabh.prolificlib.data.component.DaggerBookDescriptionComponent;
import com.example.saurabh.prolificlib.data.module.BookDescriptionModule;
import com.example.saurabh.prolificlib.presenter.BookDescriptionContract;
import com.example.saurabh.prolificlib.presenter.BookDescriptionPresenter;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDescriptionFragment extends Fragment implements BookDescriptionContract.View
{
    @Inject
    BookDescriptionPresenter bookDescriptionPresenter;

    final static String ARG_POSITION = "position";
    ResponseParameter mCurrentselectedbook = null;
    View v;
    TextView SelectedAuthor,SelectedTitle,SelectedPublish,SelectedCategory,SelectedLastCheckedBy,SelectedLastCheckedDate;
    LinearLayout linearLayout;
    Button Checkout,Delete;
    Boolean ShowDescription=false;

    OnBookDeletedListener mCallback;
    OnCheckoutListener mCallbackcheckout;

    public BookDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        Log.i("Checking"," OnCreate Fragment description");
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Checking"," onCreateView Fragment description");
        v=inflater.inflate(R.layout.fragment_book_description, container, false);
        return v;
    }

    public void onViewCreated(View v, Bundle savedInstanceState)
    {
        Log.i("Checking"," onViewCreated Fragment description");

        DaggerBookDescriptionComponent.builder()
                .netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .bookDescriptionModule(new BookDescriptionModule(this))
                .build().inject(this);


        SelectedAuthor=(TextView) v.findViewById(R.id.selectedauthor);
        SelectedTitle=(TextView) v.findViewById(R.id.selecedtitle);
        SelectedPublish=(TextView) v.findViewById(R.id.selectedpublisher);
        SelectedCategory=(TextView) v.findViewById(R.id.selectedcategory);
        SelectedLastCheckedBy=(TextView) v.findViewById(R.id.selectedlastcheckedby);
        SelectedLastCheckedDate=(TextView) v.findViewById(R.id.selectedlastcheckeddate);

        Checkout =(Button) v.findViewById(R.id.checkout);
        Delete=(Button) v.findViewById(R.id.delete);

        linearLayout=(LinearLayout) v.findViewById(R.id.linearlayout);


        linearLayout.setVisibility(View.INVISIBLE);

        Checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(((App) getActivity().getApplicationContext()).isNetworkAvailable()==true) {

                    mCallbackcheckout.PerformCheckout(mCurrentselectedbook);
                }
                else
                {
                    Toast.makeText(getActivity(),R.string.nointernet,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                if(((App) getActivity().getApplicationContext()).isNetworkAvailable()==true) {

                    String Url = mCurrentselectedbook.getUrl().replaceFirst("/", "");
                    Log.i("Delete call", "Delete= " + Url);
                    // Log.i("Test1","selectedbookposition="+selectedbookposition);
                    deleteBooks(Url);
                }
                else
                {
                    Toast.makeText(getActivity(),R.string.nointernet,Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("Checking","Create onActivityCreated");

    }


    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.

        Log.i("Checking"," onStart Fragment description");
        Bundle args = getArguments();

        if (args != null)
        {

            Log.i("Checking","args != null");
            mCurrentselectedbook=(ResponseParameter) args.getParcelable("SelectedBook");

            // Set article based on argument passed in
            updateBookDesriptionView(mCurrentselectedbook);
        }


        else
        if (mCurrentselectedbook != null)
        {
            Log.i("Checking","mCurrentselectedbook != null");
            // Set article based on saved instance state defined during onCreateView
            updateBookDesriptionView(mCurrentselectedbook);
        }



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (BookDescriptionFragment.OnBookDeletedListener) activity;
            mCallbackcheckout = (BookDescriptionFragment.OnCheckoutListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBookDeletedListener");
        }
    }
    public void updateBookDesriptionView(ResponseParameter selectedbookdata)
    {
        SelectedTitle.setText("Title : "+selectedbookdata.getTitle());
        SelectedAuthor.setText("Author : "+selectedbookdata.getAuthor());
        SelectedCategory.setText("Category : "+selectedbookdata.getCategories());
        SelectedPublish.setText("Publisher : "+selectedbookdata.getPublisher());
        SelectedLastCheckedBy.setText("LastCheckedBy : "+selectedbookdata.getLastCheckedOutBy());
        SelectedLastCheckedDate.setText("LastCheckedDate : "+selectedbookdata.getLastCheckedOut());

        linearLayout.setVisibility(View.VISIBLE);

        mCurrentselectedbook = selectedbookdata;


    }


    @Override
    public void deleteBooks(String url)
    {
        bookDescriptionPresenter.DeleteBookCall(url);
    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(getActivity(),R.string.server_down,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showComplete()
    {

        linearLayout.setVisibility(View.INVISIBLE);
        mCurrentselectedbook=null;
        mCallback.DeleteCompleted();


    }


    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("Checking"," onPause Fragment description");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("Checking"," onResume Fragment description");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       v = null; // now cleaning up!
        mCurrentselectedbook=null;
        mCallback=null;


    }



    public interface OnBookDeletedListener
    {
        public void DeleteCompleted();
    }

    public interface OnCheckoutListener
    {

        public void PerformCheckout(ResponseParameter SelectedBook);
    }


}
