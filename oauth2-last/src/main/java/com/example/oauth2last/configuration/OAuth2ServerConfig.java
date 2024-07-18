package com.example.oauth2last.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.util.UUID;
@Configuration
public class OAuth2ServerConfig {
    //old
    //this below code is old code i used it in this class OAuth2Config
//    package com.example.oauth2last.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.web.SecurityFilterChain;
//
//    @Configuration
//    public class OAuth2Config {
//        @Bean
//        public ClientRegistrationRepository clientRegistrationRepository(){
//            return new InMemoryClientRegistrationRepository(this.clientRegistration());
//        }
//        private ClientRegistration clientRegistration(){
////      return ClientRegistration.withRegistrationId("github")
////              .clientId("Ov23liF2C0JmEdSnSzGq")
////              .clientSecret("21a8cc17972e9ddb517d6085ca2f7064e47fee8d")
////              .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////              .scope("read")
////              .authorizationUri("https://github.com/login/oauth/authorize")
////              .tokenUri("https://github.com/login/oauth/access_token")
////              .userInfoUri("https://api.github.com/user")
////              .userNameAttributeName("id")
////              .clientName("Coding Techniques")
////              .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////              .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
////              .build();
//            //try with common auth provider
//            return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                    .clientId("Ov23liF2C0JmEdSnSzGq")
//                    .clientSecret("21a8cc17972e9ddb517d6085ca2f7064e47fee8d")
//                    .build();
//        }
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//            http.oauth2Login(c -> c.defaultSuccessUrl("/home"));
//            http.authorizeHttpRequests(r ->r.anyRequest().authenticated());
//            return http.build();
//        }
//    }
    //success
    @Bean
    public RegisteredClientRepository registeredClientRepository () {
        return new InMemoryRegisteredClientRepository(this.registeredClient());
    }
    private RegisteredClient registeredClient () {
        return	RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://spring.io")
                .redirectUri("http://127.0.0.1:8080/home")
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain (HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class);

        httpSecurity.exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML))).oauth2ResourceServer(r -> r.jwt(Customizer.withDefaults()));
        return	httpSecurity.build()
                ;
    }

//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings () {
//        return AuthorizationServerSettings.builder().build();
//    }
   }
