package com.box.boxjavalibv2.dao;

import java.util.Map;

/**
 * This is a interface wrapping android <a href="http://developer.android.com/reference/android/os/Parcel.html">Parcel</a>. If you are a pure java developer,
 * you can ignore this.
 */
public interface IBoxParcelWrapper {

    /**
     * Wether this parcel wraps a null object.
     * 
     * @return
     */
    boolean isNull();

    /**
     * Write a string into this parcel.
     * 
     * @param string
     *            String
     */
    void writeString(String string);

    /**
     * Write a double into this parcel
     * 
     * @param value
     */
    void writeDouble(double value);

    /**
     * Write a long into this parcel
     * 
     * @param value
     */
    void writeLong(long value);

    void writeBooleanArray(boolean[] val);

    void writeInt(int val);

    /**
     * Write a parcelable into this parcel.
     * 
     * @param val
     *            value
     */
    void writeParcelable(IBoxParcelable val, int flags);

    /**
     * Read a string out from this parcel.
     * 
     * @return string
     */
    String readString();

    /**
     * Read a long out from this parcel.
     * 
     * @return long
     */
    long readLong();

    /**
     * Read a double out from this parcel.
     * 
     * @return double
     */
    double readDouble();

    void readBooleanArray(boolean[] val);

    int readInt();

    /**
     * Initialize parcel.
     */
    void initParcel();

    void writeMap(Map<String, Object> map);

    void readMap(Map<String, Object> map);

}
