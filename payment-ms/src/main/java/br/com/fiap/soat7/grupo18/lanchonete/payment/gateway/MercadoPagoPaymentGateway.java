package br.com.fiap.soat7.grupo18.lanchonete.payment.gateway;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentResponseDto;
import reactor.core.publisher.Mono;

public class MercadoPagoPaymentGateway extends AbstractPaymentGateway {

    private static final String USER_ID = "116238642";//gerado previamente na configuração da API
    private static final String EXTERNAL_STORE_ID = "MATRIZ001"; //gerado previamente na configuração da API
    private static final String URL_GENERATE_QR = String.format("https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs",
                                                    USER_ID, EXTERNAL_STORE_ID);

    private static final String NOTIFICATION_URL = "https://www.yourserver.com/notifications";

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                                            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
                                                            .appendOffset("+HH:MM", "+00:00")
                                                            .toFormatter();

    
    private String testAccessToken;

    public MercadoPagoPaymentGateway(String testAccessToken){
        this.testAccessToken = testAccessToken;
    }

    @Override
    public PaymentProcessorResponse processPayment(PaymentProcessorRequest request){
        ZonedDateTime expirationDate = ZonedDateTime.now(ZoneId.of("UTC"))
                                                .plusMinutes(30L); // o pedido ficará pendente por 30 minutos até expirar (caso não pago).

        WebClient webClient = WebClient.builder().build();
        try{
            final var item = new MercadoPagoOrderItem();
            item.unitPrice = Double.valueOf(request.getPaymentRequestDto().getValor());
            item.totalAmount = Double.valueOf(request.getPaymentRequestDto().getValor());

            final var mapaRequest = Map.of(
                                        "description", request.getPaymentDescription(),
                                        "external_reference", request.getPaymentRequestDto().getIdPedido(),
                                        "items", List.of(item),
                                        "expiration_date", expirationDate.format(formatter),
                                        "notification_url", NOTIFICATION_URL,
                                        "title", request.getPaymentDescription(),
                                        "total_amount", request.getPaymentRequestDto().getValor()
                                        );
            var apiResponse = webClient.post()
                                .uri(URI.create(URL_GENERATE_QR))
                                .header("Authorization", String.format("Bearer %s", testAccessToken))
                                .body(Mono.just(mapaRequest), Map.class)
                                .retrieve()
                                .bodyToMono(MercadoPagoApiResponse.class)
                                .block();
            return PaymentProcessorResponse.builder()
                            .paymentResponseDto(PaymentResponseDto.builder().pagamento(apiResponse != null).build()).build();
        }catch(WebClientResponseException ex){
            throw new RuntimeException("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
        }
    }

    public static class MercadoPagoApiResponse{
        
        @JsonProperty("qr_data")
        private String qrData;

        @JsonProperty("in_store_order_id")
        private String storeOrderId;

        public String getQrData() {
            return qrData;
        }

        public void setQrData(String qrData) {
            this.qrData = qrData;
        }

        public String getStoreOrderId() {
            return storeOrderId;
        }

        public void setStoreOrderId(String storeOrderId) {
            this.storeOrderId = storeOrderId;
        }

        
    }

    @SuppressWarnings("unused")
    public static class MercadoPagoOrderItem{

        private String category = "marketplace";

        private String title = "Pedido lanchonete";

        private String description = "Pedido lanchonete";

        @JsonProperty("unit_price")
        private Double unitPrice;

        private int quantity = 1;

        @JsonProperty("unit_measure")
        private String unitMeasure = "unit";

        @JsonProperty("total_amount")
        private Double totalAmount;

        public String getCategory() {
            return category;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getUnitMeasure() {
            return unitMeasure;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setUnitMeasure(String unitMeasure) {
            this.unitMeasure = unitMeasure;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        @Override
        public String toString() {
            return "MercadoPagoOrderItem [getCategory()=" + getCategory() + ", getTitle()=" + getTitle()
                    + ", getDescription()=" + getDescription() + ", getUnitPrice()=" + getUnitPrice()
                    + ", getQuantity()=" + getQuantity() + ", getUnitMeasure()=" + getUnitMeasure()
                    + ", getTotalAmount()=" + getTotalAmount() + "]";
        }

        
        

    }

}
