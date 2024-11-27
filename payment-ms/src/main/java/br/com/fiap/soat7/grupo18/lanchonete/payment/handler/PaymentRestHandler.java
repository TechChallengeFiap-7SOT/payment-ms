package br.com.fiap.soat7.grupo18.lanchonete.payment.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
@Tag(name = "MS Pagamentos", description = "Processamento dos pagamentos")
public class PaymentRestHandler {


    @PostMapping
    @Operation(description = "Processa um pagamento")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PaymentResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<PaymentResponseDto> handlePayment(@Valid @RequestBody PaymentRequestDto paymentrequestBody) {
        
        return ResponseEntity.ok(PaymentResponseDto.builder()
                                    .idPedido(paymentrequestBody.getIdPedido())
                                    .pagamento(true)
                                    .build());
    }
}
