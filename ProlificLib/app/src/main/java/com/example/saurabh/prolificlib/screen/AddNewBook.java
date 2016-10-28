package com.example.saurabh.prolificlib.screen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saurabh.prolificlib.App;
import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.data.component.DaggerAddNewBookComponent;
import com.example.saurabh.prolificlib.data.module.AddNewBookModule;
import com.example.saurabh.prolificlib.presenter.AddNewBookContract;
import com.example.saurabh.prolificlib.presenter.AddNewBookPresenter;

import javax.inject.Inject;

public class AddNewBook extends AppCompatActivity implements AddNewBookContract.View
{

    @Inject
    AddNewBookPresenter addNewBookPresenter;
    EditText Title,Author,Publisher,Category;
    Button CancelButton,AddBookButton;
    Toolbar toolbar;
    public static String TAG="LogData";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        DaggerAddNewBookComponent.builder()
                .netComponent(((App)getApplicationContext()).getNetComponent())
                .addNewBookModule(new AddNewBookModule(this))
                .build().inject(this);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        Title=(EditText) findViewById(R.id.Newtitle);
        Author=(EditText) findViewById(R.id.NewAuthor);
        Publisher=(EditText) findViewById(R.id.NewPublisher);
        Category=(EditText) findViewById(R.id.NewCategory);
        CancelButton=(Button) findViewById(R.id.cancelbutton);
        AddBookButton=(Button) findViewById(R.id.addbookbutton);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String newtitle=Title.getText().toString();
                String newauthor=Author.getText().toString();
                String newpublisher=Publisher.getText().toString();
                String newcategory=Category.getText().toString();

                Integer ValidValue=validdataentered(newauthor,newcategory,newtitle,newpublisher);

                if(ValidValue==0)
                {
                    addnewbook(newauthor, newcategory, newtitle, newpublisher);
                }
                else
                {
                    if(ValidValue==1)
                    {
                        Title.requestFocus();
                        Title.setError("Title is require");
                    }

                    if(ValidValue==2)
                    {
                        Author.requestFocus();
                        Author.setError("Author is require");

                    }

                    if(ValidValue==3)
                    {
                        Publisher.requestFocus();
                        Publisher.setError("Publisher is require");
                    }

                    if(ValidValue==4)
                    {
                        Category.requestFocus();
                        Category.setError("Category is require");
                    }
                }
            }
        });


        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EraseDataAlertBox();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            EraseDataAlertBox();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void addnewbook(String author, String categories, String title, String publisher)
    {
        if(((App) getApplicationContext()).isNetworkAvailable()==true) {
            addNewBookPresenter.AddNewBook(author, categories, title, publisher);
        }
        else
        {
            Toast.makeText(AddNewBook.this,R.string.nointernet,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public Integer validdataentered(String author, String categories, String title, String publisher)
    {
        return addNewBookPresenter.ValidDataEntered(author,categories,title,publisher);


    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(AddNewBook.this,R.string.server_down,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete()
    {
        Log.i(TAG,"New Book Added to server");
        ((App) getApplicationContext()).ChangeState=1;
        finish();

    }



    @Override
    public void onBackPressed()
    {
        EraseDataAlertBox();

    }

    public void EraseDataAlertBox()
    {
        AlertDialog.Builder deleteallconfirmalertbox = new AlertDialog.Builder(this);
        deleteallconfirmalertbox.setMessage(R.string.book_not_store);

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

}
