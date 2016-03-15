package com.cablePaypal.springpaypal.builders;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.ShippingAddress;

public class PayerInfoBuilder {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private ShippingAddress shippingAddress;

    public PayerInfo build(){
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setEmail(email);
        payerInfo.setFirstName(firstName);
        payerInfo.setLastName(lastName);
        payerInfo.setPhone(phone);
        payerInfo.setShippingAddress(shippingAddress);
        return payerInfo;
    }

    public PayerInfoBuilder email(String email){
        this.email = email;
        return this;
    }

    public PayerInfoBuilder firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PayerInfoBuilder lastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PayerInfoBuilder phone(String phone){
        this.phone = phone;
        return this;
    }

    public PayerInfoBuilder shippingAddress(ShippingAddress shippingAddress){
        this.shippingAddress = shippingAddress;
        return this;
    }
}
