package com.gotoubun.weddingvendor.data.kol;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KOLResponse {

    String username;
    String password;
    int status;
    Date createdDate;
    Date modifiedDate;
    String fullName;
    String phone;
    String address;
    String nanoPassword;
    String description;


}
