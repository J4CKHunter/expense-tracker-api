package com.erdemnayin.expensetrackerapi.config;

import com.erdemnayin.expensetrackerapi.model.Role;
import com.erdemnayin.expensetrackerapi.service.JpaUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeyProperties;

    public SecurityConfig(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    public AuthenticationManager authenticationManager(JpaUserDetailsService jpaUserDetailsService){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jpaUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))) /*TODO: BUNA BAKILACAK*/
                .cors(Customizer.withDefaults()) /*TODO: BUNA BAKILACAK*/
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/transactions/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling((ex) ->
                        ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();

        decoder.setJwtValidator(tokenValidator());

        return decoder;
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSet){
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(){
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey())
                .privateKey(rsaKeyProperties.privateKey())
                .build();

        return new ImmutableJWKSet<>(new JWKSet(jwk));
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities"); // JwtClaimSet içindeki claim() in ismi
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");      // authentication içinde nasıl bahsedileceği

        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    public OAuth2TokenValidator<Jwt> tokenValidator(){
        final List<OAuth2TokenValidator<Jwt>> tokenValidators = List.of(
                new JwtTimestampValidator(Duration.ofSeconds(15L)),
                new JwtIssuerValidator("http://erdemnayin.com"),
                audienceValidator()
        );

        return new DelegatingOAuth2TokenValidator<Jwt>(tokenValidators);
    }

    public OAuth2TokenValidator<Jwt> audienceValidator(){
        return new JwtClaimValidator<List<String>>(OAuth2TokenIntrospectionClaimNames.AUD, aud -> aud.contains("erdem"));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI customOpenAPI(){

        SecurityScheme jwtSchema = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("jwt",jwtSchema))
                .info(new Info()
                        .title("ExpenseTrackerAPI")
                        .version("1.0")
                        .description("A demo web-app app for Innova Hackathon")
                        .license(new License().name("Expense-Tracker-API")))
                ;

    }
}
