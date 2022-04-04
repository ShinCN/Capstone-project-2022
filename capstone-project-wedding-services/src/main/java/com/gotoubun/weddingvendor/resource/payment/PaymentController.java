package com.gotoubun.weddingvendor.resource.payment;

import com.gotoubun.weddingvendor.data.payment.PaymentRequest;
import com.gotoubun.weddingvendor.data.payment.PaymentResponse;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.service.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AccountService accountService;

    @PostMapping("create-payment")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequest requestParams, BindingResult bindingResult, Principal principal) throws UnsupportedEncodingException, IOException {
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        Customer customer = accountService.findByUserName(principal.getName()).get().getCustomer();

            int amount = requestParams.getAmount() * 100;
            String suffix_txn =generateRandomPassword(3);
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", PaymentConfig.VERSION);
            vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
            vnp_Params.put("vnp_TmnCode", PaymentConfig.TMNCODE);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", PaymentConfig.CURRCODE);
            String bank_code = requestParams.getBankCode();
            if (bank_code != null && !bank_code.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bank_code);
            }
            vnp_Params.put("vnp_TxnRef", customer.getId() + suffix_txn); //Ma giao dich
            vnp_Params.put("vnp_OrderInfo", requestParams.getPaymentDescription()); //Noi dung thanh toan
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

//    @GetMapping("payment-receipt")
//    public ResponseEntity<?> transactionHandle(
//            @RequestParam(value = "vnp_Amount", required = false) String amount,
//            @RequestParam(value = "vnp_txnRef", required = false) String txnRef,
//            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
//            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTransNo,
//            @RequestParam(value = "vnp_CardType", required = false) String cardType,
//            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
//            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
//            @RequestParam(value = "vnp_CreateDate", required = false) String payDate,
//            @RequestParam(value = "vnp_TmnCode", required = false) String tmnCode,
//            @RequestParam(value = "vnp_TransactionNo", required = false) String transNo,
//            @RequestParam(value = "vnp_TransactionStatus", required = false) String transStatus,
//            @RequestParam(value = "vnp_SecureHashType", required = false) String secureHashType,
//            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash,
//            Principal principal
//            )throws MessagingException {
//
//        PaymentHistory paymentHistory = new PaymentHistory();
//        PaymentResponse paymentResponse = new PaymentResponse();
//        if(!responseCode.equalsIgnoreCase("00")){
//            paymentResponse.setStatus("02");
//            paymentResponse.setMessage("failed");
//            return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
//        }
//
//        Customer customer = accountService.findByUserName(principal.getName()).get().getCustomer();
//
//        if(){
//
//        }
//    }
}
