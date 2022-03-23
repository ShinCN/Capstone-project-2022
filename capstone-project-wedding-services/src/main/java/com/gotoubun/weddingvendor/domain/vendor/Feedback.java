package com.gotoubun.weddingvendor.domain.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private SinglePost singlePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_post_id")
    private PackagePost packagePost;
}
