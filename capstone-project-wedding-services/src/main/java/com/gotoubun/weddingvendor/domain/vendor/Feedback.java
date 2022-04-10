package com.gotoubun.weddingvendor.domain.vendor;

import com.gotoubun.weddingvendor.domain.user.Customer;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name="content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "single_post_id")
    private SinglePost singlePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_post_id")
    private PackagePost packagePost;

    @Column(name="createdby")
    @CreatedBy
    private String createdBy;

    @Column(name="modifiedby")
    @LastModifiedBy
    private String modifiedBy;
}