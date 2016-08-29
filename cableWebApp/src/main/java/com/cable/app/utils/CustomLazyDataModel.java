
package com.cable.app.utils;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class CustomLazyDataModel<T>  extends LazyDataModel<T> {  

	private static final long serialVersionUID = 1L;

	private List<T> datasource;  

	private SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy"); 

	public CustomLazyDataModel(List<T> datasource) {  
		this.datasource = datasource;  

	}    
	public CustomLazyDataModel(List<T> datasource,String fieldName) {  
		this.datasource = datasource;  

	} 

	@Override  
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
		List<T> data = new ArrayList<T>();  

		if(datasource != null){
			//filter  
			for(T object : datasource) {  
				boolean match = true;  

				for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {  

					try {  
						String filterProperty = it.next();  
						String filterValue = filters.get(filterProperty);  
						//     String fieldValue = String.valueOf(car.getClass().getDeclaredField(filterProperty).get(car));  

						Field field = object.getClass().getDeclaredField(filterProperty);

						field.setAccessible(true);


						String fieldValue="";
						if(field.getType().getName().equals("java.util.Date"))                	  
							fieldValue=sdf.format(Util.getCurrentTime((Date) field.get(object)));
						else if(field.getType().getName().equals("java.lang.Integer"))                	  
							fieldValue=String.valueOf(field.get(object));
						else if(field.getType().getName().equals("java.lang.Double"))                	  
							fieldValue=String.valueOf(field.get(object));
						else if(field.getType().getName().equals("java.lang.Long"))                	  
							fieldValue=String.valueOf(field.get(object));
						else
							fieldValue=(String) field.get(object);

						if(filterValue == null || fieldValue.toUpperCase().startsWith(filterValue.toUpperCase())) {  
							match = true;  
						}  
						else {  
							match = false;  
							break;  
						} 


					} catch(Exception e) {  
						match = false;  
					}   
				}  

				if(match) {  
					data.add(object);  
				}  
			}  
		}
		//sort  
		if(sortField != null) {  
			Collections.sort(data, new LazySorter(sortField,SortOrder.ASCENDING.equals(sortOrder)));  
		}  

		//rowCount  
		int dataSize = data.size();  
		this.setRowCount(dataSize);  

		//paginate  
		if(dataSize > pageSize) {  
			System.out.println("pagination called first "+first+" pageSize "+pageSize+" dataSize "+dataSize);
			System.out.println("pagination called no "+first/pageSize+" pageSize "+pageSize+" dataSize "+dataSize);
			try {  
				return data.subList(first, first + pageSize);  
			}  
			catch(IndexOutOfBoundsException e) {  
				return data.subList(first, first + (dataSize % pageSize));  
			}  
		}  
		else {  
			return data;  
		}  
	}  

	@Override
	public void setRowIndex(int rowIndex) {
		/*
		 * The following is in ancestor (LazyDataModel):
		 * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
		 */
		if (rowIndex == -1 || getPageSize() == 0) {
			super.setRowIndex(-1);
		}
		else
			super.setRowIndex(rowIndex % getPageSize());
	}
}  