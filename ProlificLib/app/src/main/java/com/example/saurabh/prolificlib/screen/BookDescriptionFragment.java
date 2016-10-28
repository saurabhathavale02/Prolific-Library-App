package com.example.saurabh.prolificlib.screen;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import static android.util.Log.i;

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
    EditText SelectedAuthor,SelectedTitle,SelectedPublish,SelectedCategory,SelectedLastCheckedBy;
    TextView SelectedLastCheckedDate;
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
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_book_description, container, false);
        return v;
    }

    public void onViewCreated(View v, Bundle savedInstanceState)
    {


        DaggerBookDescriptionComponent.builder()
                .netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .bookDescriptionModule(new BookDescriptionModule(this))
                .build().inject(this);


        SelectedAuthor=(EditText) v.findViewById(R.id.selectedauthor);
        SelectedTitle=(EditText) v.findViewById(R.id.selecedtitle);
        SelectedPublish=(EditText) v.findViewById(R.id.selectedpublisher);
        SelectedCategory=(EditText) v.findViewById(R.id.selectedcategory);
        SelectedLastCheckedBy=(EditText) v.findViewById(R.id.selectedlastcheckedby);
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

                    String updatedauthor = SelectedAuthor.getText().toString();
                    String updatedtitle = SelectedTitle.getText().toString();
                    String updatedpublisher = SelectedPublish.getText().toString();
                    String updatedcategory = SelectedCategory.getText().toString();
                    String updatedcheckby = SelectedLastCheckedBy.getText().toString();
                    mCallbackcheckout.PerformCheckout(mCurrentselectedbook,updatedauthor,updatedtitle,updatedpublisher,updatedcategory,updatedcheckby);


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

                    deleteBooks(Url,mCurrentselectedbook);
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

        i("Checking","Create onActivityCreated");

    }


    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.

        i("Checking"," onStart Fragment description");
        Bundle args = getArguments();

        if (args != null)
        {

            i("Checking","args != null");
            mCurrentselectedbook=(ResponseParameter) args.getParcelable("SelectedBook");

            // Set article based on argument passed in
            updateBookDesriptionView(mCurrentselectedbook);
        }


        else
        if (mCurrentselectedbook != null)
        {
            i("Checking","mCurrentselectedbook != null");
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
        SelectedTitle.setText(selectedbookdata.getTitle());
        SelectedAuthor.setText(selectedbookdata.getAuthor());
        SelectedCategory.setText(selectedbookdata.getCategories());
        SelectedPublish.setText(selectedbookdata.getPublisher());
        SelectedLastCheckedBy.setText(selectedbookdata.getLastCheckedOutBy());
        SelectedLastCheckedDate.setText("LastCheckedDate : "+selectedbookdata.getLastCheckedOut());

        linearLayout.setVisibility(View.VISIBLE);

        mCurrentselectedbook = selectedbookdata;


    }


    @Override
    public void deleteBooks(String url,ResponseParameter responseParameter)
    {
        bookDescriptionPresenter.DeleteBookCall(url,responseParameter);
    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(getActivity(),R.string.server_down,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showComplete(ResponseParameter responseParameter)
    {

        linearLayout.setVisibility(View.INVISIBLE);
        mCurrentselectedbook=null;
        mCallback.DeleteCompleted(responseParameter);



    }


    @Override
    public void onPause()
    {
        super.onPause();
        i("Checking"," onPause Fragment description");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        i("Checking"," onResume Fragment description");

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
        public void DeleteCompleted(ResponseParameter responseParameter);
    }

    public interface OnCheckoutListener
    {

        public void PerformCheckout(ResponseParameter SelectedBook, String updatedauthor, String updatedtitle, String updatedpublisher, String updatedcategory, String updatedcheckby);
    }


}
