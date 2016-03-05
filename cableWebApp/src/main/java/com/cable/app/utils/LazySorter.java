package com.cable.app.utils;

import java.lang.reflect.Field;
import java.util.Comparator;


public class LazySorter<T> implements Comparator<T> {

    private String sortField;    
    private boolean sortAsc;
    
    public LazySorter(String sortField, boolean sortAsc) {
        this.sortField = sortField;
        this.sortAsc = sortAsc;
    }

    
    @Override
    public int compare(T object1, T object2) {
        try {
        	Field field1 = object1.getClass().getDeclaredField(this.sortField);
            Field field2 = object2.getClass().getDeclaredField(this.sortField);
            field1.setAccessible(true);
            field2.setAccessible(true);
            Object value1 = field1.get(object1);
            Object value2 = field2.get(object2);
 
            int value = ((Comparable)value1).compareTo(value2);
            return sortAsc ? value : -1 * value;
        }
        catch(Exception e) {
        	return 0;
        }
    }
}