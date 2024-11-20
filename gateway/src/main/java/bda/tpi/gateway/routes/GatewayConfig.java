//package bda.tpi.gateway.routes;
//
//import bda.tpi.gateway.filters.VehiculosFilter;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator configurarRutas(RouteLocatorBuilder builder, VehiculosFilter vehiculosFilter) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/empleado")
//                        .uri("http://127.0.0.1:8082/empleados"))
//                .route(p -> p
//                        .path("/pruebas/add")
//                        .uri("http://127.0.0.1:8082/pruebas/add"))
//                .build();
//    }
//}
