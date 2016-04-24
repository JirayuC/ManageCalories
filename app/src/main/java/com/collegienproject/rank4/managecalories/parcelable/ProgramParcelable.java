package com.collegienproject.rank4.managecalories.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JirayuPC on 14 เม.ย. 2559.
 */
public class ProgramParcelable implements Parcelable {

    String prgName;

    public ProgramParcelable(){

    }
    protected ProgramParcelable(Parcel in) {
        prgName = in.readString();
    }

    public static final Creator<ProgramParcelable> CREATOR = new Creator<ProgramParcelable>() {
        @Override
        public ProgramParcelable createFromParcel(Parcel in) {
            return new ProgramParcelable(in);
        }

        @Override
        public ProgramParcelable[] newArray(int size) {
            return new ProgramParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prgName);
    }
}
