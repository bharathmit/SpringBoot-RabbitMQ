package com.cable.batch.job.paymentgeneration;

import java.util.List;







import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cable.rest.model.GeneratePayment;
import com.cable.rest.repository.ConnectionAccountJPARepo;
import com.cable.rest.repository.GeneratePaymentJPARepo;

public class PaymentGenerationWriter implements ItemWriter<GeneratePayment> {
	
	@Autowired
	GeneratePaymentJPARepo generatePaymentJPARepo;
	
	@Autowired
	ConnectionAccountJPARepo connectionAccountJPARepo;
	
	
	@Override
	public void write(List<? extends GeneratePayment> items) throws Exception {

		for(GeneratePayment item : items){		
			
			System.out.println("************* WRITER **************");
			generatePaymentJPARepo.saveAndFlush(item);
			
			connectionAccountJPARepo.updatePayGenMonth(""+item.getConnectionAccount().getAccountId(), ""+item.getBillDate().getMonth());
		}

	}

}
