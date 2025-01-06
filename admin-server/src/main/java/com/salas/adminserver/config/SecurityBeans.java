package com.salas.adminserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeans {

//    @Bean
//    public OauthHttpHeadersProvider oauthHttpHeadersProvider(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService) {
//        return new OauthHttpHeadersProvider(
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService)
//        );
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.oauth2Client(Customizer.withDefaults())
//                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
//                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(CsrfConfigurer::disable)
//                .build();
//    }
}
