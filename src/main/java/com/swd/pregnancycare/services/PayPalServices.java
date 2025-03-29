package com.swd.pregnancycare.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.swd.pregnancycare.config.PayPalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class PayPalServices {

    @Autowired
    private PayPalConfig payPalConfig;

    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String getAccessToken() {
        String url = payPalConfig.getBaseUrl() + "/v1/oauth2/token";


        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(payPalConfig.getClientId(), payPalConfig.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);


        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy access token từ PayPal!", e);
        }
    }

    public String createPayment(Double amount, String currency, String returnUrl, String cancelUrl) throws JsonProcessingException {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{"
                + "\"intent\":\"sale\","
                + "\"redirect_urls\":{"
                + "\"return_url\":\"" + returnUrl + "\","
                + "\"cancel_url\":\"" + cancelUrl + "\""
                + "},"
                + "\"payer\":{"
                + "\"payment_method\":\"paypal\""
                + "},"
                + "\"transactions\":[{"
                + "\"amount\":{"
                + "\"total\":\"" + amount + "\","
                + "\"currency\":\"" + currency + "\""
                + "}"
                + "}]"
                + "}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                payPalConfig.getBaseUrl() + "/v1/payments/payment",
                HttpMethod.POST,
                request,
                String.class
        );
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Định dạng đẹp

        Object jsonObject = objectMapper.readValue(response.getBody(), Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    }


}
