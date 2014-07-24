package com.box.boxjavalibv2.dao;

import java.io.Serializable;

/**
 * This is a interface wrapping android <a href="http://developer.android.com/reference/android/os/Parcelable.html">Parcelable</a>. If you are a pure java
 * developer, you can ignore this.
 */
public interface IBoxParcelable extends Serializable{

    void writeToParcel(IBoxParcelWrapper parcelWrapper, int flags);
}
