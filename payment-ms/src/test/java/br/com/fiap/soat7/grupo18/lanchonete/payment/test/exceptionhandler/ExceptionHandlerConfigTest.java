package br.com.fiap.soat7.grupo18.lanchonete.payment.test.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.fiap.soat7.grupo18.lanchonete.payment.config.GlobalExceptionHandlerConfig;

@SpringBootTest
public class ExceptionHandlerConfigTest {

    @Autowired
    private GlobalExceptionHandlerConfig exceptionHandlerConfig;

    @Test
    void testHandleNotFoundException() throws Exception{
        var exception = new Exception("Test");
        ResponseEntity<String> responseEntity = exceptionHandlerConfig.handleNotFoundException(exception);
        int expectedSttus = ResponseEntity.status(500).build().getStatusCode().value();
        assertEquals(expectedSttus, responseEntity.getStatusCode().value());
    }
}
