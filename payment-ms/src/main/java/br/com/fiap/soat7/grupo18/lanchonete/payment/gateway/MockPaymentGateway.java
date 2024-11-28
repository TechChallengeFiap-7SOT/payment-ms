package br.com.fiap.soat7.grupo18.lanchonete.payment.gateway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;

public class MockPaymentGateway extends AbstractPaymentGateway {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final int TRANSACTION_ID_LENGTH = 25;

    private final double REFUSED_PAYMENT_VALUE_MOCK = 555.55D;

    @Override
    public PaymentProcessorResponse processPayment(PaymentProcessorRequest request) {
        final String transactionID = getTransactionID();
        boolean successPayment = REFUSED_PAYMENT_VALUE_MOCK != Optional.ofNullable(request).map(PaymentProcessorRequest::getPaymentRequestDto).map(PaymentRequestDto::getValor)
                                                                        .orElse(0.01D);
        return PaymentProcessorResponse.builder()
                        .paymentDescription(request.getPaymentDescription())
                        .transactionID(transactionID)
                        .paymentResponseDto(
                            PaymentResponseDto.builder()
                                    .idPedido(request.getPaymentRequestDto().getIdPedido())
                                    .paymentTime(LocalDateTime.now())
                                    .pagamento(successPayment)
                                    .transactionID(transactionID)
                                    .build()
                        )
                        .build();
    }

    private String getTransactionID(){
        final String transactionID = ThreadLocalRandom.current()
                                        .ints(TRANSACTION_ID_LENGTH, 0, CHARS.length())
                                        .mapToObj(i -> String.valueOf(CHARS.charAt(i)))
                                        .collect(Collectors.joining());

        final String currentDateTime = LocalDateTime.now().format(formatter);

        return String.format("%s.%s", currentDateTime, transactionID);
    }

}
