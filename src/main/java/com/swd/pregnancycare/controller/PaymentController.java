package com.swd.pregnancycare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.services.PayPalServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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

        List<String> validCurrencies = Arrays.asList("USD", "EUR", "JPY");

        try {
            if (currency.equalsIgnoreCase("VND")) {
                double exchangeRate = getExchangeRate("VND", "USD");
                amount = amount / exchangeRate;
                currency = "USD";
                logger.info("Converted VND to USD: {}", amount);
            }

            if (!validCurrencies.contains(currency.toUpperCase())) {
                logger.error("Invalid currency: {}", currency);
                throw new AppException(ErrorCode.CURRENCY_INVALID);
            }

            // Format amount to two decimal places (PayPal yêu cầu)
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("#.00", symbols);
            String formattedAmount = df.format(amount);

            logger.info("Creating payment: amount={}, currency={}", formattedAmount, currency);
            String cancelUrl = "http://localhost:8080/api/paypal/cancel";
            String successUrl = "http://localhost:8080/api/paypal/success";

            return payPalService.createPayment(Double.valueOf(formattedAmount), currency.toUpperCase(), successUrl, cancelUrl);
        } catch (Exception e) {
            logger.error("Error creating payment: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Hàm lấy tỷ giá hối đoái từ API bên ngoài (ví dụ)
    private double getExchangeRate(String from, String to) {
        if (from.equals("VND") && to.equals("USD")) {
            return 24000.0; // Giả định tỷ giá 1 USD = 24,000 VND
        }
        return 1.0;
    }

    @GetMapping("/cancel")
    public String cancelPayment() {
        logger.warn("Payment was cancelled.");
        return "Thanh toán bị hủy.";
    }



    @GetMapping("/success")
    public ResponseEntity<Void> successPayment(@RequestParam("paymentId") String paymentId,
                                               @RequestParam("PayerID") String payerId,
                                               HttpServletResponse response) throws IOException {
        try {
            logger.info("Executing payment: paymentId={}, payerId={}", paymentId, payerId);
            String transactionData = payPalService.executePayment(paymentId, payerId);

            // Redirect về frontend với paymentId
            String redirectUrl = "http://localhost:5173/payment/success/" + paymentId;
            response.sendRedirect(redirectUrl);
            return ResponseEntity.status(HttpStatus.FOUND).build();
        } catch (Exception e) {
            logger.error("Error executing payment: {}", e.getMessage(), e);
            response.sendRedirect("http://localhost:5173/payment/failure");
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
    }



}
