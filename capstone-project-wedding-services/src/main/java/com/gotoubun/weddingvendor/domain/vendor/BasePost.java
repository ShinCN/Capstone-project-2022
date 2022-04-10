package com.gotoubun.weddingvendor.domain.vendor;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePost extends BaseEntity{

	@Column(name = "service_name")
	private String serviceName;

	@Lob
	@Nationalized
	@Column(name="about")
	private String about;

	@Column(name="price")
	private Float price;

	@Column(name="rate")
	private Integer rate;

}