
package com.cable.batch.job.paygen;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




import com.cable.batch.common.utils.BatchUtils;
import com.cable.batch.config.ApplicationContextHolder;
import com.cable.rest.model.GeneratePayment;
import com.cable.rest.repository.ConnectionAccountJPARepo;
import com.cable.rest.repository.GeneratePaymentJPARepo;

@Component
public class PayGenWriter implements ItemWriter<GeneratePayment> {

    
    GeneratePaymentJPARepo generatePaymentJPARepo;

    ConnectionAccountJPARepo connectionAccountJPARepo;

    @Override
    @Transactional
    public void write(List<? extends GeneratePayment> items) throws Exception {

        for (GeneratePayment item : items) {

            System.out.println("************* WRITER **************");
            System.out.println("Item Values" + item.getConnectionAccount().getAccountId() +" Month "+BatchUtils.getCurrentMonth());
            
            generatePaymentJPARepo = ApplicationContextHolder.getContext().getBean(GeneratePaymentJPARepo.class);
            
            generatePaymentJPARepo.saveAndFlush(item);
            generatePaymentJPARepo = ApplicationContextHolder.getContext().getBean(GeneratePaymentJPARepo.class);
            connectionAccountJPARepo.updatePayGenMonth(item.getConnectionAccount().getAccountId(), BatchUtils.getCurrentMonth());
        }

    }

}
