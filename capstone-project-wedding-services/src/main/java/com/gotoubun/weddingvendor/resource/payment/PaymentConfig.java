package com.gotoubun.weddingvendor.resource.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentConfig {

    public static final String IPADDR = "0.0.0.0.0.0.0.1";
    public static final String VERSION = "2.1.0";
    public static final String COMMAND = "pay";
    public static final String TMNCODE = "1A0CS8G9";
    public static final String ORDERTYPE = "250006"; //Hoa don dich vu
    public static final String LOCALEDEFAULT = "VN";
    public static final String CURRCODE = "VND";
    public static final String RETURNURL = "https://gotoubun.azurewebsites.net/payment-receipt";
    public static final String VNPURL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String CHECKSUM = "BNGAFKFVBFZAYDGNDPGVLEYHOJAGZIVX";

}
