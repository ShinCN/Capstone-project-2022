package com.gotoubun.weddingvendor.utils;

import lombok.Getter;

@Getter
public enum ConstantUtils {
    IDNOTESIST
            ("Id is not exist"),
    ADDSUCCESS
            ("add successfully"),
    DELETESUCCESS
            ("delete successfully"),
    UPDATESUCCESS
            ("update successfully"),
    VENDORNOTFOUND
            ("Vendor is not found");


    private final String message;

    private ConstantUtils(String message) {
        this.message = message;
    }
}
