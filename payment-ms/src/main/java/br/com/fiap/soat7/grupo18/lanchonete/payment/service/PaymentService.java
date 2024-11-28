package br.com.fiap.soat7.grupo18.lanchonete.payment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.soat7.grupo18.lanchonete.payment.exception.RequiredDataException;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorRequest;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorResponse;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;

@Service
public class PaymentService {


    private AbstractPaymentGateway paymentGateway;
    private CallbackService callbackService;
    
    //a implementação do gateway é determinada pela configuração em PaymentGatewayConfig
    public PaymentService(AbstractPaymentGateway paymentGateway, @Autowired CallbackService callbackService) {
        this.paymentGateway = paymentGateway;
        this.callbackService = callbackService;
    }

    public PaymentProcessorResponse processPayment(PaymentProcessorRequest paymentRequest){
        checkPaymentRequest(paymentRequest);
        final String urlCallback = Optional.ofNullable(paymentRequest)
                                        .map(PaymentProcessorRequest::getPaymentRequestDto).map(PaymentRequestDto::getUrlCallback).orElse("");
        var response = paymentGateway.processPayment(paymentRequest);
        if (!urlCallback.isEmpty()){
            callbackService.invokeCallback(urlCallback);
        }
        return response;
    }
        
    private void checkPaymentRequest(PaymentProcessorRequest paymentRequest) {
        final Optional<PaymentProcessorRequest> optPaymentRequest = Optional.ofNullable(paymentRequest);
        final Double MIN_PAYMENT_VALUE = 0.01D;
        if (!optPaymentRequest.map(PaymentProcessorRequest::getPaymentRequestDto).map(PaymentRequestDto::getIdPedido).isPresent()){
            throw new RequiredDataException("ID do pedido não informado");
        }

        var valorPedido = optPaymentRequest.map(PaymentProcessorRequest::getPaymentRequestDto).map(PaymentRequestDto::getValor).orElse(0.0D);
        if (valorPedido < MIN_PAYMENT_VALUE){
            throw new RequiredDataException("Valor do pedido inválido. Valor mínimo: 0.01");
        }
    }

}
