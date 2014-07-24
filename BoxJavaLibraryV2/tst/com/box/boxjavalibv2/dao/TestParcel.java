package com.box.boxjavalibv2.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TestParcel implements IBoxParcelWrapper {

    private final ArrayList<Object> container = new ArrayList<Object>();

    @Override
    public void writeString(String string) {
        container.add(string);
    }

    @Override
    public void writeLong(long value) {
        container.add(value);
    }

    @Override
    public void writeBooleanArray(boolean[] val) {
        container.add(val);
    }

    @Override
    public void writeInt(int val) {
        container.add(val);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void writeMap(Map map) {
        container.add(map);
    }

    @Override
    public void writeParcelable(IBoxParcelable val, int flags) {
        if (val == null) {
            this.writeBooleanArray(new boolean[] {false});
        }
        else {
            val.writeToParcel(this, flags);
        }
    }

    @Override
    public String readString() {
        return (String) container.remove(0);
    }

    @Override
    public long readLong() {
        return (Long) container.remove(0);
    }

    @Override
    public void readBooleanArray(boolean[] val) {
        boolean[] read = (boolean[]) container.remove(0);
        for (int i = 0; i < val.length; i++) {
            val[i] = read[i];
        }
    }

    @Override
    public int readInt() {
        return (Integer) container.remove(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void readMap(Map map) {
    	HashMap<String, String> m = (HashMap<String, String>) container.remove(0);
    	map.putAll(m);
    }

    @Override
    public boolean isNull() {
        boolean[] isNotNull = new boolean[1];
        readBooleanArray(isNotNull);
        return !isNotNull[0];
    }

    @Override
    public void initParcel() {
        this.writeBooleanArray(new boolean[] {true});
    }

    @Override
    public void writeDouble(double value) {
        container.add(value);
    }

    @Override
    public double readDouble() {
        return (Double) container.remove(0);
    }
}
