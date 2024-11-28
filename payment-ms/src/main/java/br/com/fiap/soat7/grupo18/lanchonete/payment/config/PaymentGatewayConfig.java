package br.com.fiap.soat7.grupo18.lanchonete.payment.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.AbstractPaymentGateway;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.MercadoPagoPaymentGateway;
import br.com.fiap.soat7.grupo18.lanchonete.payment.gateway.MockPaymentGateway;

@Configuration
public class PaymentGatewayConfig {


    @Bean
    @ConditionalOnProperty(name = "app.payment.gateway", havingValue = "mercadopago")
    public AbstractPaymentGateway getMercadoPagoGateway(){
        return new MercadoPagoPaymentGateway();
    }

    @Bean
    @ConditionalOnProperty(name = "app.payment.gateway", havingValue = "mock", matchIfMissing = true)
    public AbstractPaymentGateway getMockGateway(){
        return new MockPaymentGateway();
    }

}
