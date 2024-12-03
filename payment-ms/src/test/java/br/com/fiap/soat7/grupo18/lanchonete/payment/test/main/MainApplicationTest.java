package br.com.fiap.soat7.grupo18.lanchonete.payment.test.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.soat7.grupo18.lanchonete.payment.PaymentApplication;

@SpringBootTest
public class MainApplicationTest {


    @Test
    void testMainApplicationRun() throws Exception{
        var params = new String[0];
        PaymentApplication.main(params);
        assertTrue(params.length == 0);
    }
}
