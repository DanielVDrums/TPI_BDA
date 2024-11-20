//package bda.tpi.gateway.filters;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class VehiculosFilter extends AbstractGatewayFilterFactory<VehiculosFilter.Config> {
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "http://127.0.0.1:8081/vehiculos",
//                    HttpMethod.GET,
//                    null,
//                    String.class
//            );
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                // Agregar lógica para usar los datos de la respuesta
//                exchange.getRequest().mutate().header("Vehiculos-Data", response.getBody()).build();
//                return chain.filter(exchange);
//            } else if (response.getStatusCode().is4xxClientError()) {
//                exchange.getResponse().setStatusCode(response.getStatusCode());
//                return exchange.getResponse().setComplete();
//            } else {
//                return chain.filter(exchange);
//            }
//        };
//    }
//
//    public static class Config {
//        // Configuración del filtro si es necesario
//    }
//}
