package br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id_pedido", "transactionID", "pagamento", "paymentTime"})
public class PaymentResponseDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("id_pedido")
    private String idPedido;

    private String transactionID;

    private boolean pagamento;

    @Builder.Default
    private LocalDateTime paymentTime = LocalDateTime.now();

}
