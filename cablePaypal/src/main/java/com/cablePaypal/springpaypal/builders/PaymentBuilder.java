package com.cablePaypal.springpaypal.builders;

import java.util.ArrayList;
import java.util.List;

import com.cablePaypal.springpaypal.PaymentIntent;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;

public class PaymentBuilder {

   private PaymentIntent paymentIntent;
   private Payer payer;
   private List<Transaction> transactions = new ArrayList<Transaction>();

   public Payment build(){
	   Payment payment=new Payment(paymentIntent.toString(),payer);
	   payment.setTransactions(transactions);
       return payment;
   }

    public PaymentBuilder intent(PaymentIntent intent){
        this.paymentIntent = intent;
        return this;
    }

    public PaymentBuilder payer(Payer payer){
        this.payer = payer;
        return this;
    }

    public PaymentBuilder transactions(Transaction...transactions){
        for(Transaction transaction : transactions){
            this.transactions.add(transaction);
        }
        return this;
    }
}
