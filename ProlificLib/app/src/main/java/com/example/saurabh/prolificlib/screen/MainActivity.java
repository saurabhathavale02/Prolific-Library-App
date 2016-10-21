package com.example.saurabh.prolificlib.screen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saurabh.prolificlib.App;
import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.data.ResponseParameter;
import com.example.saurabh.prolificlib.data.component.DaggerMainActivityComponent;
import com.example.saurabh.prolificlib.data.module.MainActivityModule;
import com.example.saurabh.prolificlib.presenter.MainActivityContract;
import com.example.saurabh.prolificlib.presenter.MainActivityPresenter;

import javax.inject.Inject;

import static com.example.saurabh.prolificlib.R.id.fragment_container;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View ,BookDisplayFragment.OnBookSelectedListener,BookDescriptionFragment.OnBookDeletedListener,BookDescriptionFragment.OnCheckoutListener
{

    @Inject
    MainActivityPresenter mainActivityPresenter;
    BookDescriptionFragment bookDescriptionFragment;
    BookDisplayFragment bookDisplayFragment;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainActivityComponent.builder()
                .netComponent(((App)getApplicationContext()).getNetComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build().inject(this);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(fragment_container) != null)
        {
            if (savedInstanceState != null)
            {
                return;
            }

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back

            bookDisplayFragment = new BookDisplayFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(fragment_container, bookDisplayFragment);

            transaction.commit();


        }


    }

    @Override
    public void onResume() {
        super.onResume();


        Log.i("Checking", "Activity Resume");
        if (((App) getApplicationContext()).ChangeState == 1)
        {
            Log.i("Checking", "CheckLayout()=" + checkLayout());
            if (checkLayout() == 2)
            {

                bookDisplayFragment = (BookDisplayFragment)
                        getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);


            }
            ((App) getApplicationContext()).ChangeState = 0;
           if(bookDisplayFragment==null)
           {
               bookDisplayFragment = (BookDisplayFragment)
                       getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);
           }
            bookDisplayFragment.UpdateBookList();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete)
        {
            if(((App) getApplicationContext()).isNetworkAvailable()==true) {
                if (((App) getApplicationContext()).NumberOfBooks > 0) {
                    DeleAllConfirmAlertBox();
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_delete_item, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(MainActivity.this,R.string.nointernet, Toast.LENGTH_SHORT).show();
            }
                return true;
        }

        if (id == R.id.action_refresh)
        {
            if(((App) getApplicationContext()).isNetworkAvailable()==true) {
                if (checkLayout() == 2) {

                    bookDisplayFragment = (BookDisplayFragment)
                            getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);
                }

                if (bookDisplayFragment == null) {
                    bookDisplayFragment = (BookDisplayFragment)
                            getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);
                }

                bookDisplayFragment.UpdateBookList();
            }
            else
            {
                Toast.makeText(MainActivity.this,R.string.nointernet, Toast.LENGTH_SHORT).show();
            }
        return true;
        }

        return super.onOptionsItemSelected(item);
        }


    @Override
    public void onBackPressed()
    {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.i("Checking","backstackcount="+backStackCount);

        if(getSupportFragmentManager().getBackStackEntryCount()>0) {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            ExitConfirmAlertBox();
        }

    }

    @Override
    public void onBookSelected(ResponseParameter selectedbook)
    {
        displayBookDescription(selectedbook);
    }
    @Override
    public void displayBookDescription(ResponseParameter selectedbook)
    {
        if(checkLayout()==1)
        {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            Log.i("Checking1","backStackCount="+backStackCount);
            for (int i = 0; i < backStackCount; i++)
            {

                getSupportFragmentManager().popBackStack();

            }
            BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();
            Bundle args = new Bundle();
            args.putParcelable("SelectedBook",selectedbook);
            bookDescriptionFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(fragment_container, bookDescriptionFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();


        } else
        {

            // If article frag is available, we're in two-pane layout...
            Log.i("test","in 2 pane layout");
            // Call a method in the ArticleFragment to update its content
            //newFragment.
            bookDescriptionFragment.updateBookDesriptionView(selectedbook);

        }
    }


    @Override
    public void PerformCheckout(ResponseParameter SelectedBook)
    {
        CreateCheckOutAlertBox(SelectedBook);
    }

    private void CreateCheckOutAlertBox(final ResponseParameter selectedBook)
    {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.activity_check_out_book, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this,R.style.Checkout_style);
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setCancelable(false);

        final String url=selectedBook.getUrl().toString().replaceFirst("/","");
        Log.i("Checkout call","Delete= "+url);
        final EditText CheckoutBy = (EditText) mView.findViewById(R.id.CheckOutBy);

        alertDialogBuilderUserInput.setPositiveButton("Checkout", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogBox, int id)
                    {

                        String checkouttext=CheckoutBy.getText().toString();
                        Log.i("checkouttext","checkouttext="+checkouttext.toString());
                        if(checkouttext.trim().isEmpty()==true)
                        {
                            Log.i("checkouttext","is empty");
                            CheckoutBy.setError("Checkoutby is required");

                        }
                        else
                        {
                            Log.i("checkouttext","not empty");
                            checkoutBook(url, checkouttext);
                        }
                        // ToDo get user input here
                    }
                })

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    @Override
    public void checkoutBook(String url, String lastcheckedby)
    {
        Log.i("Checking","checkoutBook");
        mainActivityPresenter.CheckOutBook(url,lastcheckedby);
    }


    @Override
    public void showCheckoutComplete()
    {
        if (checkLayout()==1)
        {

            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            Log.i("Checking1","backStackCount="+backStackCount);
            for (int i = 0; i < backStackCount; i++)
            {

                getSupportFragmentManager().popBackStack();

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            bookDisplayFragment = new BookDisplayFragment();
            transaction.replace(fragment_container, bookDisplayFragment);
            transaction.commit();


        }
        else
        {
            Log.i("Checking","in 2 pane layout=");


            bookDisplayFragment=(BookDisplayFragment)
                    getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);
            bookDisplayFragment.UpdateBookList();
        }

    }


    @Override
    public void deleteAllBook() {

        mainActivityPresenter.DeleteAllbook();
    }

    @Override
    public void showDeleteAllComplete()
    {
        Log.i("Checking", "CheckLayout()=" + checkLayout());
        if(checkLayout()==2)
        {

            bookDescriptionFragment=(BookDescriptionFragment)
                    getSupportFragmentManager().findFragmentById(R.id.bookdescription_fragment);

            bookDescriptionFragment.linearLayout.setVisibility(View.INVISIBLE);

            bookDescriptionFragment=(BookDescriptionFragment)
                    getSupportFragmentManager().findFragmentById(R.id.bookdescription_fragment);
            bookDescriptionFragment.mCurrentselectedbook=null;

            if(bookDisplayFragment==null)
            {
                bookDisplayFragment = (BookDisplayFragment)
                        getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);
            }

            bookDisplayFragment.UpdateBookList();
        }
        else
        {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            Log.i("Checking1","backStackCount="+backStackCount);
            for (int i = 0; i < backStackCount; i++)
            {

                getSupportFragmentManager().popBackStack();

            }

            Log.i("Checking","in bookDescriptionFragment");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            bookDisplayFragment = new BookDisplayFragment();
            transaction.replace(fragment_container, bookDisplayFragment);
            transaction.commit();

        }

    }

    public void DeleAllConfirmAlertBox()
    {
        AlertDialog.Builder deleteallconfirmalertbox = new AlertDialog.Builder(this);
        deleteallconfirmalertbox.setMessage("Delete all book Data ? ");

        deleteallconfirmalertbox
                .setCancelable(false)
                .setPositiveButton(R.string.Agree, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogBox, int id)
                    {
                        deleteAllBook();
                    }
                })

                .setNegativeButton(R.string.DisAgree,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = deleteallconfirmalertbox.create();
        alertDialogAndroid.show();
    }


    @Override
    public void DeleteCompleted()
    {
        if (checkLayout()==1)
        {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            Log.i("Checking1","backStackCount="+backStackCount);
            for (int i = 0; i < backStackCount; i++)
            {

                getSupportFragmentManager().popBackStack();

            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            bookDisplayFragment = new BookDisplayFragment();
            transaction.replace(fragment_container, bookDisplayFragment);
            transaction.commit();

          //  bookDisplayFragment.UpdateBookList();

        }
        else
        {
            Log.i("Checking","in 2 pane layout=");

            bookDisplayFragment=(BookDisplayFragment)
                    getSupportFragmentManager().findFragmentById(R.id.bookdisplay_fragment);

            bookDisplayFragment.UpdateBookList();


        }


    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(MainActivity.this,R.string.server_down,Toast.LENGTH_SHORT).show();
        Log.i("show error","error-"+message);
    }







    public void ExitConfirmAlertBox()
    {
        AlertDialog.Builder deleteallconfirmalertbox = new AlertDialog.Builder(this);
        deleteallconfirmalertbox.setMessage(R.string.exit_app);

        deleteallconfirmalertbox
                .setCancelable(false)
                .setPositiveButton(R.string.Agree, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogBox, int id)
                    {
                        finish();
                    }
                })

                .setNegativeButton(R.string.DisAgree,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = deleteallconfirmalertbox.create();
        alertDialogAndroid.show();
    }

    @Override
    public Integer checkLayout()
    {
        bookDescriptionFragment= (BookDescriptionFragment) getSupportFragmentManager().findFragmentById(R.id.bookdescription_fragment);

        //In 1 Pane layout
        if (bookDescriptionFragment == null || !bookDescriptionFragment.isInLayout())
        {
            return 1;
        }
        //In 2 pane layout
        else
        {
            return 2;
        }

    }


}
