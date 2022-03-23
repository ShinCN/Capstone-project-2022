package com.gotoubun.weddingvendor.domain.weddingtool;

import com.gotoubun.weddingvendor.domain.user.Customer;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(name="paid_money")
    private float money;

    @Column(name="response_code")
    private String responseCode;

    @Column(name="vnpay_code")
    private String vnpayCode;

    @Column(name="code_bank")
    private String codeBank;

    @Column(name="note")
    private String note;

    @Column(name="created_date")
    @CreatedDate
    private Date createdDate;

}
