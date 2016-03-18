package com.cable.batch.config;

import org.springframework.stereotype.Service;

@Service("jobone")
public class MyJobOne {
    protected void myTask() {
    	System.out.println("This is my task");
    }
}
