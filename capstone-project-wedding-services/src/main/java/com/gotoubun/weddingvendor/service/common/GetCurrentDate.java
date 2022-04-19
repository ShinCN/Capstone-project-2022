package com.gotoubun.weddingvendor.service.common;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class GetCurrentDate {
    public LocalDateTime now()
    {
        return LocalDateTime.now();
    }
}