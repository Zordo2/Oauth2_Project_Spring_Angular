package com.example.oauth2last.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //success
    @Bean
    public UserDetailsService userDetailsService () {
        UserDetails user = User.withUsername("Zordok")
                .password("{noop}1234")
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain (HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        return http.build();

    }
    //    //success
    @Bean
    public JWKSource<SecurityContext> jwkSource () {
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);

    }
    //    //success
    private static KeyPair generateKeyPair () {

        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
    @Bean
    public JwtDecoder jwtDecoder (JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

////old
//
////    @Bean
////    public JWKSource<SecurityContext> jwkSource () {
////        KeyPair keyPair = generateKeyPair();
////        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
////        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
////
////        RSAKey rsaKey = new RSAKey.Builder(publicKey)
////                .privateKey(privateKey)
////                .keyID(UUID.randomUUID().toString())
////                .build();
////        JWKSet jwkSet = new JWKSet(rsaKey);
////        return new ImmutableJWKSet<>(jwkSet);
////
////    }
////old
////public RSAKey rsaKey() {
////    try {
////        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
////        g.initialize(2048);
////        var kp = g.generateKeyPair();
////        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
////        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
////        return new RSAKey.Builder(publicKey)
////                .privateKey(privateKey)
////                .keyID(UUID.randomUUID().toString())
////                .build();
////    } catch (NoSuchAlgorithmException e) {
////        throw new RuntimeException(":(", e);
////    }
////}
////@Bean
////public JWKSource<SecurityContext> jwkSource(){
////    JWKSet set=new JWKSet((JWK) rsaKey());
////    return (j,sc)->j.select(set);
////}
//
//
////    private static KeyPair generateKeyPair () {
////
////        KeyPair keyPair;
////
////        try {
////            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
////            keyPairGenerator.initialize(2048);
////            keyPair = keyPairGenerator.generateKeyPair();
////        } catch (Exception e) {
////            throw new IllegalStateException(e);
////        }
////        return keyPair;
////    }
}
