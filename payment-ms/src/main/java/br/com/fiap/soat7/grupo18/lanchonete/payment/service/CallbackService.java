package br.com.fiap.soat7.grupo18.lanchonete.payment.service;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
public class CallbackService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = Logger.getLogger("br.com.fiap.soat7.grupo18.lanchonete.payment.service.CallbackService");

    @Async
    public void invokeCallback(String url, PaymentProcessorResponse response){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            var responseDto = response.getPaymentResponseDto();
            var callBackDto = CallbackDto.builder()
                                .idPedido(responseDto.getIdPedido())
                                .transactionID(responseDto.getTransactionID())
                                .pagamento(responseDto.isPagamento())
                                .build();

            String bodyJson = new ObjectMapper().writeValueAsString(callBackDto);

            HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info(String.format("POST request to %s", url));
        }catch(Exception e){
            log.log(Level.WARNING, String.format("Error when invoking '%s': %s", url, e.getMessage()));
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonPropertyOrder({"id_pedido", "transactionID", "pagamento"})
    public static class CallbackDto implements Serializable{
    
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @JsonProperty("id_pedido")
            private String idPedido;

            private String transactionID;

            private boolean pagamento;
        
    }

}
