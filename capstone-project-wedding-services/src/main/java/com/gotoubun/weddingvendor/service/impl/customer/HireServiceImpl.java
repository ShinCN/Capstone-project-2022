package com.gotoubun.weddingvendor.service.impl.customer;

import com.gotoubun.weddingvendor.domain.weddingtool.HireDTO;
import org.springframework.stereotype.Service;

@Service
public class HireServiceImpl {
    public HireDTO setHireRequest(Long customerID, Long serviceID){
        HireDTO hireDTO = new HireDTO();
        hireDTO.setHireID(Long.toString(customerID + serviceID));
        return hireDTO;
    }
}
