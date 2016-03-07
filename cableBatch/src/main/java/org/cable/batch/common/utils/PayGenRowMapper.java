package org.cable.batch.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cable.rest.model.GeneratePayment;

public class PayGenRowMapper implements RowMapper<GeneratePayment> {

	@Override
	public GeneratePayment mapRow(ResultSet rs, int rowNum) throws SQLException {
		GeneratePayment payGenEntity = new GeneratePayment();
		//payGenEntity.setEmail(rs.getString("email"));
		
		return payGenEntity;
	}

}
