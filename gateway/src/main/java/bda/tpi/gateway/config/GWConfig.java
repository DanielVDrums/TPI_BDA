package bda.tpi.gateway.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${api-gw.url-microservicio-agencia}") String uriAgencia,
                                        @Value("${api-gw.url-microservicio-vehiculos}") String uriVehiculos) {
        return builder.routes()
                .route(p -> p
                        .path("/pruebas/**")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/reportes/**")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/vehiculos/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/notificaciones/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/reportes/incidentes")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/reportes/kilometros/{id}")
                        .filters(f -> f.rewritePath("/reportes/kilometros/(?<id>.*)", "/vehiculos/kilometros/${id}"))
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/vehiculos/swagger-ui/**")
                        .uri("http://localhost:8083"))
                .route(p -> p
                        .path("/vehiculos/v3/api-docs/**")
                        .uri("http://localhost:8083"))
                .route(p -> p
                        .path("/agencia/swagger-ui/**")
                        .uri("http://localhost:8082"))
                .route(p -> p
                        .path("/agencia/v3/api-docs/**")
                        .uri("http://localhost:8082"))
                .build();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/agencia/swagger-ui/**", "/agencia/v3/api-docs/**", "/vehiculos/swagger-ui/**", "/vehiculos/v3/api-docs/**")
                        .permitAll()

                        .pathMatchers(HttpMethod.POST,"/pruebas/**")
                        .hasRole("EMPLEADO")
                        .pathMatchers(HttpMethod.GET,"/pruebas")
                        .access(this.hasUsername("g070-b"))


                        .pathMatchers(HttpMethod.GET,"/vehiculos/**")
                        .hasRole("VEHICULO")
                        .pathMatchers(HttpMethod.POST,"/vehiculos/**")
                        .hasRole("VEHICULO")

                        .pathMatchers(HttpMethod.POST,"/notificaciones/**")
                        .hasRole("EMPLEADO")

                        .pathMatchers(HttpMethod.GET,"/reportes/**")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/vehiculos/kilometros/**")
                        .hasRole("ADMIN")

                        .anyExchange()
                        .authenticated()

                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    private ReactiveAuthorizationManager<AuthorizationContext> hasUsername(String username) {
        return (authentication, context) -> authentication.map(auth -> {
            if (auth instanceof JwtAuthenticationToken jwtAuthToken) {
                Jwt jwt = jwtAuthToken.getToken(); // Obtén el token JWT
                String jwtUsername = jwt.getClaimAsString("preferred_username"); // Extrae el claim
                return new AuthorizationDecision(jwtUsername.equals(username));
            }
            return new AuthorizationDecision(false);
        });
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));

        return jwtAuthenticationConverter;
    }
}
