package org.cable.batch.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.cable.rest.constants.PaymentStatus;
import com.cable.rest.model.ConnectionAccount;
import com.cable.rest.model.GeneratePayment;

public class PayGenRowMapper implements RowMapper<GeneratePayment> {

	@Override
	public GeneratePayment mapRow(ResultSet rs, int rowNum) throws SQLException {
		GeneratePayment payGenEntity = new GeneratePayment();
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dueDate,billDate;
		
		try{
			if(BatchUtils.getCurrentMonth()==12){
				dueDate=formatter.parse(""+BatchUtils.getCurrentYear()+1+"-01-"+rs.getString("payment_due_date"));
			}
			else{
				dueDate=formatter.parse(""+BatchUtils.getCurrentYear()+"-"+BatchUtils.getCurrentMonth()+"-"+rs.getString("payment_due_date"));
			}
			
			
			billDate=formatter.parse(""+BatchUtils.getCurrentYear()+"-"+BatchUtils.getCurrentMonth()+"-"+rs.getString("payment_generate_date"));
			
			payGenEntity.setDueDate(dueDate);
			payGenEntity.setBillDate(billDate);
		}
		catch(Exception e){
			
		}
		
		payGenEntity.setInvoiceAmount(Double.parseDouble(rs.getString("rent_amount")));
		payGenEntity.setPayGenDate(new Date());
		payGenEntity.setPayGenStatus(PaymentStatus.PENDING);
		ConnectionAccount connection=new ConnectionAccount();
		connection.setAccountId(Long.valueOf(rs.getString("account_id")));
		payGenEntity.setConnectionAccount(connection);
		
		return payGenEntity;
	}

}
