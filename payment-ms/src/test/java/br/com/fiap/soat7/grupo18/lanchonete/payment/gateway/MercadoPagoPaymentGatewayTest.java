package br.com.fiap.soat7.grupo18.lanchonete.payment.gateway;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorRequest;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;

@SpringBootTest
public class MercadoPagoPaymentGatewayTest {


    private MercadoPagoPaymentGateway gateway;

    @Value("${app.payment.gateway.mercadopago.test-accesstoken}") 
    private String mercadoPagoAccessToken;


    @BeforeEach
    void setUp(){
        gateway = new MercadoPagoPaymentGateway(mercadoPagoAccessToken);
    }

    @Test
    void testProcessPayment() throws Exception{
        PaymentProcessorRequest request = PaymentProcessorRequest.builder()
                                                .paymentDescription("description")
                                                .paymentRequestDto(
                                                    PaymentRequestDto.builder()
                                                            .idPedido("abcd-1234")
                                                            .valor(50.5D)
                                                            .build()
                                                )
                                                .build();

        assertNotNull(gateway.processPayment(request));
    }

}
