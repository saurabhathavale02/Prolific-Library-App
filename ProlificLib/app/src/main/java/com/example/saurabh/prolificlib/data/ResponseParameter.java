package com.example.saurabh.prolificlib.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saurabh on 10/17/16.
 */

public class ResponseParameter implements Parcelable
{
    String author,categories,lastCheckedOut,lastCheckedOutBy,publisher,title,url;



    protected ResponseParameter(Parcel in)
    {
        author = in.readString();
        categories = in.readString();
        lastCheckedOut = in.readString();
        lastCheckedOutBy = in.readString();
        publisher = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<ResponseParameter> CREATOR = new Creator<ResponseParameter>()
    {
        @Override
        public ResponseParameter createFromParcel(Parcel in)
        {
            return new ResponseParameter(in);
        }

        @Override
        public ResponseParameter[] newArray(int size)
        {
            return new ResponseParameter[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(categories);
        parcel.writeString(lastCheckedOut);
        parcel.writeString(lastCheckedOutBy);
        parcel.writeString(publisher);
        parcel.writeString(title);
        parcel.writeString(url);
    }


    public String getAuthor() {
        return author;
    }

    public String getCategories() {
        return categories;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
