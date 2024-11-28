package br.com.fiap.soat7.grupo18.lanchonete.payment.gateway;

import java.io.Serializable;

import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public abstract class AbstractPaymentGateway {

    public abstract PaymentProcessorResponse processPayment(PaymentProcessorRequest request);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentProcessorRequest implements Serializable{
    
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            private PaymentRequestDto paymentRequestDto;

            private String paymentDescription;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentProcessorResponse implements Serializable{/**
             *
             */
            private static final long serialVersionUID = 1L;

            private PaymentResponseDto paymentResponseDto;

            private String transactionID;

            private String paymentDescription;

    }
}
