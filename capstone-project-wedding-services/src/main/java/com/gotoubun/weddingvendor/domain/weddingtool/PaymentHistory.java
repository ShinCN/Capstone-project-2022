package com.gotoubun.weddingvendor.domain.weddingtool;

import com.gotoubun.weddingvendor.domain.user.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Column(name="service_id")
    private String serviceId;

    @Column(name="vnp_tmncode")
    private float tmnCode;

    @Column(name="vnp_tnxRef")
    private float tnxRef;

    @Column(name="vnp_amount")
    private float money;

    @Column(name="response_code")
    private String responseCode;

    @Column(name="bank_code")
    private String bankCode;

    @Column(name="bank_trans_no")
    private String bankTransNo;

    @Column(name="card_type")
    private String cardType;

    @Column(name="order_info")
    private String orderInfo;

    @Column(name="pay_date")
    private Date payDate;

    @Column(name="transaction_no")
    private String transNo;

    @Column(name="transaction_status")
    private String transStatus;
}
