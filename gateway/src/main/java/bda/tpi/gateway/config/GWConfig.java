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
                        .path("/vehiculos/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/notificaciones/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/reportes/incidentes/**")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/reportes/empleado/**")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/reportes/kilometros/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/swagger-ui/index.html")
                        .uri("http://localhost:8080"))
                .route(p -> p
                        .path("/vehiculos/swagger-ui/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/v3/api-docs/**")
                        .uri(uriVehiculos))
                .route(p -> p
                        .path("/agencia/swagger-ui/**")
                        .uri(uriAgencia))
                .route(p -> p
                        .path("/v3/api-docs/**")
                        .uri(uriAgencia))
                .build();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/agencia/swagger-ui/**", "/v3/api-docs/**", "/vehiculos/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()

                        .pathMatchers(HttpMethod.POST,"/pruebas/**")
                        .access(this.hasUsername("g070-b"))
                        //.hasRole("EMPLEADO")
                        .pathMatchers(HttpMethod.GET,"/pruebas/**")
                        .access(this.hasUsername("g070-b"))


                        .pathMatchers(HttpMethod.GET,"/vehiculos/**")
                        .access(this.hasUsername("g070-c"))
                        .pathMatchers(HttpMethod.POST,"/vehiculos/**")
                        .access(this.hasUsername("g070-c"))

                        .pathMatchers(HttpMethod.POST,"/notificaciones/**")
                        .access(this.hasUsername("g070-b"))

                        .pathMatchers(HttpMethod.GET,"/reportes/**")
                        .access(this.hasUsername("g070-a"))
                        //.hasRole("ADMIN")

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
