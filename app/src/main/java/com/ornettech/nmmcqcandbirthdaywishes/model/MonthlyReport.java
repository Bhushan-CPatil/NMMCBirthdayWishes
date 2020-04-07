package com.ornettech.nmmcqcandbirthdaywishes.model;

/**
 * Created by Ornet on 12/23/2017.
 */

public class MonthlyReport /*implements Parcelable*/{
    public String title;
    public String corporators;
    private String message;



    public MonthlyReport(String title, String corporators, String message) {
        this.title = title;
        this.corporators = corporators;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorporators() {
        return corporators;
    }

    public void setCorporators(String corporators) {
        this.corporators = corporators;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*@Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(corporators);
        parcel.writeString(message);
    }*/

    /*public static final Parcelable.Creator
            CREATOR = new Parcelable.Creator() {
        public MonthlyReport createFromParcel(Parcel in) {
            return new MonthlyReport(in);
        }

        public MonthlyReport[] newArray(int size) {
            return new MonthlyReport[size];
        }
    };

    private MonthlyReport(Parcel in) {
        title = in.readString();
        corporators = in.readString();
        message = in.readString();
    }
*/
}
