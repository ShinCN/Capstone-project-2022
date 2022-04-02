package com.gotoubun.weddingvendor.resource.payment;

import com.gotoubun.weddingvendor.data.PaymentRequest;
import com.gotoubun.weddingvendor.data.PaymentResponse;
import com.gotoubun.weddingvendor.utils.DataUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PaymentController {

    @PostMapping("create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest requestParams) throws UnsupportedEncodingException, IOException {

//        Customer customer =
            int amount = requestParams.getAmount() * 100;
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", PaymentConfig.VERSION);
            vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
            vnp_Params.put("vnp_TmnCode", PaymentConfig.TMNCODE);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            String bank_code = requestParams.getBankCode();
            if (bank_code != null && !bank_code.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bank_code);
            }
            vnp_Params.put("vnp_TxnRef", "VNPAY001"); //cho ni get id cua service thanh toan
            vnp_Params.put("vnp_OrderInfo", requestParams.getPaymentDescription()); // cho ni get noi dung thanh toan
            vnp_Params.put("vnp_OrderType", PaymentConfig.ORDERTYPE);
            vnp_Params.put("vnp_Locale", PaymentConfig.LOCALEDEFAULT);
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.RETURNURL);
            vnp_Params.put("vnp_IpAddr", PaymentConfig.IPADDR);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            //Add Params of 2.1.0 Version
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            //Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = DataUtils.hmacSHA512(PaymentConfig.CHECKSUM, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = PaymentConfig.VNPURL + "?" + queryUrl;
//            com.google.gson.JsonObject job = new JsonObject();
//            job.addProperty("code", "00");
//            job.addProperty("message", "success");
//            job.addProperty("data", paymentUrl);
//            Gson gson = new Gson();
//            resp.getWriter().write(gson.toJson(job));
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus("00");
        paymentResponse.setMessage("success");
        paymentResponse.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);

    }
}
