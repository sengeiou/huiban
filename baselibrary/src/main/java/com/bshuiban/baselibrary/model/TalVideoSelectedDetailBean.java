package com.bshuiban.baselibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zx315476228 on 17-8-3.
 */

public class TalVideoSelectedDetailBean extends ResultBean  {
    private List<TalVideoSelectedDetailBean.Data> data;

    public List<TalVideoSelectedDetailBean.Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data implements Parcelable{
        private int id;
        private String name;

        protected Data(Parcel in) {
            id = in.readInt();
            name = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Data() {
        }

        public Data(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
        }
    }
}
