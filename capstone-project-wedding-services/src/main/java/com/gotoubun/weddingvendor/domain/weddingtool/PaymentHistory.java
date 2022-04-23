package com.gotoubun.weddingvendor.domain.weddingtool;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "single_service_in_receipt",
            joinColumns = @JoinColumn(name = "receiptId"),
            inverseJoinColumns = @JoinColumn(name = "single_service_id")
    )
    private Collection<SinglePost> singlePosts;

    @Column(name="vnp_tmncode")
    private String tmnCode;

    @Column(name="vnp_txnRef")
    private String txnRef;

    @Column(name="vnp_amount")
    private Float amount;

    @Column(name="response_code")
    private String responseCode;

    @Column(name="bank_code")
    private String bankCode;

    @Column(name="bank_trans_no")
    private String bankTransNo;

    @Column(name="card_type")
    private String cardType;

    @Column(name="order_info", columnDefinition="NVARCHAR(255)")
    private String orderInfo;

    @Column(name="pay_date")
    private LocalDateTime payDate;

    @Column(name="transaction_no")
    private String transNo;

    @Column(name="transaction_status")
    private String transStatus;

    @Column(name="secure_hash_type")
    private String secureHashType;

    @Column(name="secure_hash")
    private String secureHash;
}
