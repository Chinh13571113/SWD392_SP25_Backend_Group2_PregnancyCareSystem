package com.swd.pregnancycare.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.services.PayPalServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "Payment API", description = "Checkout Management")
@RequestMapping("/api/paypal")

public class PaymentController {
    @Autowired
    private PayPalServices payPalService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create")
    public String createPayment(
            @RequestParam Double amount, @RequestParam String currency) throws JsonProcessingException {

        // Kiểm tra mã tiền tệ trước khi gửi request
        List<String> validCurrencies = Arrays.asList("USD", "EUR", "JPY", "VND");
        if (!validCurrencies.contains(currency.toUpperCase())) {
            throw new AppException(ErrorCode.CURRENCY_INVALID);
        }
        String cancelUrl = "http://localhost:8080/paypal/cancel";
        String successUrl = "http://localhost:8080/paypal/success";

        return payPalService.createPayment(amount, currency.toUpperCase(), successUrl, cancelUrl);
    }





    @GetMapping("/cancel")
    public String cancelPayment() {
        return "Thanh toán bị hủy.";
    }
}
