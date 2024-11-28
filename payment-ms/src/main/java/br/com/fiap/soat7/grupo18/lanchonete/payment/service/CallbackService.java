package br.com.fiap.soat7.grupo18.lanchonete.payment.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorResponse;

@Service
class CallbackService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = Logger.getLogger("br.com.fiap.soat7.grupo18.lanchonete.payment.service.CallbackService");

    @Async
    public void invokeCallback(String url, PaymentProcessorResponse response){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            String bodyJson = new ObjectMapper().writeValueAsString(response.getPaymentResponseDto());

            HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info(String.format("POST request to %s", url));
        }catch(Exception e){
            log.log(Level.WARNING, String.format("Error when invoking '%s': %s", url, e.getMessage()));
        }

    }

}
