package com.cable.batch.common.utils;

import java.util.Calendar;

public class  BatchUtils {

	
	public static int getCurrentYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		// month start from 0 to 11
		return calendar.get(Calendar.MONTH)+1;
	}
}
