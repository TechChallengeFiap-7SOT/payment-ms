package br.com.fiap.soat7.grupo18.lanchonete.payment.test.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway.PaymentProcessorResponse;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.service.CallbackService;
import br.com.fiap.soat7.grupo18.lanchonete.payment.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CallbackService callbackService;

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void testInvokeCallback_Success() throws Exception {
        var request = PaymentRequestDto.builder()
                            .idPedido("abcd-1234")
                            .urlCallback("http://localhost:8080")
                            .build();

        var response = PaymentResponseDto.builder()
                            .idPedido("abcd-1234")
                            .pagamento(true)
                            .paymentTime(LocalDateTime.now())
                            .transactionID("xxxxx")
                            .build();

        assertTrue(paymentService.invokeCallback(request.getUrlCallback(), PaymentProcessorResponse.builder()
                                                                                        .paymentResponseDto(response)
                                                                                        .build()));
    }

    @Test
    void testInvokeCallback_SuccessII() throws Exception {
        var request = PaymentRequestDto.builder()
                            .idPedido("abcd-1234")
                            .urlCallback("http://localhost:8080")
                            .build();

        var response = PaymentResponseDto.builder()
                            .idPedido("abcd-1234")
                            .pagamento(true)
                            .paymentTime(LocalDateTime.now())
                            .transactionID("xxxxx")
                            .build();

        try{
            callbackService.invokeCallback(request.getUrlCallback(), PaymentProcessorResponse.builder()
                                                                            .paymentResponseDto(response)
                                                                            .build());
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void testInvokeCallback_ErrorHandling() throws Exception {
        var request = PaymentRequestDto.builder()
                            .idPedido("abcd-1234")
                            .urlCallback(null)
                            .build();

        var response = PaymentResponseDto.builder()
                            .idPedido("abcd-1234")
                            .pagamento(true)
                            .paymentTime(LocalDateTime.now())
                            .transactionID("xxxxx")
                            .build();

        assertFalse(paymentService.invokeCallback(request.getUrlCallback(), PaymentProcessorResponse.builder()
                                                                                        .paymentResponseDto(response)
                                                                                        .build()));
    }
}
