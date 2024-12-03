package br.com.fiap.soat7.grupo18.lanchonete.payment.test.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.MercadoPagoPaymentGateway;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.service.CallbackService;

@SpringBootTest
public class DtoTest {


    @Test
    void testPaymentResponseDtoCreate() throws Exception{
        var response = new PaymentResponseDto();
        response.setIdPedido("abc-1234");
        response.setTransactionID("xxxxx");
        response.setPagamento(true);
        response.setPaymentTime(LocalDateTime.now());


        assertNotNull(response.getIdPedido());
        assertNotNull(response.getTransactionID());
        assertTrue(response.isPagamento());
        assertNotNull(response.getPaymentTime());
        assertNotNull(PaymentResponseDto.builder().toString());
        assertNotNull(PaymentResponseDto.builder().build().toString());
    }

    @Test
    void testPaymentRequestDtoCreate() throws Exception{
        var request = PaymentRequestDto.builder()
                        .idPedido("abcde")
                        .valor(50.5)
                        .build();

        assertNotNull(request.getIdPedido());
        assertNotNull(request.getValor());
        assertNotNull(PaymentRequestDto.builder().toString());

    }

    @Test
    void testMercadoPagoOrderItemCreate() throws Exception{
        var orderItem = new MercadoPagoPaymentGateway.MercadoPagoOrderItem();
        orderItem.setCategory("catA");
        orderItem.setDescription("description");
        orderItem.setQuantity(10);
        orderItem.setTitle("title");
        orderItem.setTotalAmount(50.0);
        orderItem.setUnitMeasure("UN");
        orderItem.setUnitPrice(50.0);

        assertNotNull(orderItem.toString());
    }

    @Test
    void testPaymentProcessorResponseCreate() throws Exception{
        var payment = new AbstractPaymentGateway.PaymentProcessorResponse();
        payment.setPaymentDescription("description");
        payment.setPaymentResponseDto(PaymentResponseDto.builder().build());
        payment.setTransactionID("XXXXXX");

        assertNotNull(payment.getPaymentDescription());
        assertNotNull(payment.getPaymentResponseDto());
        assertNotNull(payment.getTransactionID());
    }

    @Test
    void testCallbackDtoCreate() throws Exception{
        var callback = new CallbackService.CallbackDto();
        callback.setIdPedido("abcdef-1234");
        callback.setPagamento(true);
        callback.setTransactionID("XXXXXXX");

        assertNotNull(callback.toString());
        assertNotNull(callback.builder().toString());
    }
}
