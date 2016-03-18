package com.cable.batch.job.paymentgeneration;

import java.util.List;






import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cable.rest.model.GeneratePayment;
import com.cable.rest.repository.GeneratePaymentJPARepo;

public class PaymentGenerationWriter implements ItemWriter<GeneratePayment> {
	
	@Autowired
	GeneratePaymentJPARepo generatePaymentJPARepo;
	
	@Override
	public void write(List<? extends GeneratePayment> items) throws Exception {

		for(GeneratePayment item : items){		
			/*String[] split = item.getEmail().split("@");
			item.setName(Character.toUpperCase(split[0].charAt(0)) + split[0].substring(1));*/
			System.out.println("************* WRITER **************");
			generatePaymentJPARepo.saveAndFlush(item);
		}

	}

}
