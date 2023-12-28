package com.thinklogics_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;
import java.util.List;

@Configuration

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // Define lists for different URL patterns
    private final List<String> permitAllUrls = Arrays.asList(
            "/user/login",
            "/user/register",
            "/user/verify-otp",

            "/attendees/create",
            "/attendees/existsByNumber",


            "/visitors/create",

            "/visitors/checkEmail",

            "/enquiries/create",

            "/jobApplications/create",

            "/jobDescriptions/getAll",

            "/Admin/api/sessions/list"


    );

    private  final List<String> userOnlyUrls = Arrays.asList(
          "/user/findById/{userId}"
    );
    private final List<String> adminOnlyUrls = Arrays.asList(
             "/Admin/api/sessions/create",
            "/Admin/api/sessions/**",
            "/Admin/api/sessions/update/{id}",
            "/Admin/api/sessions/delete/{id}",

            "/attendees/list",
            "/attendees/getById/{id}",

            "/visitors/updateById/{id}",
            "/visitors/deleteById/{id}",
            "/visitors/list" ,

            "/jobApplications/list",
            "/jobApplications/updateById/{id}",
            "/jobApplications/getById/{id}",
            "/jobApplications/deleteById/{id}",

            "/jobDescriptions/getById/{id}",
            "/jobDescriptions/update/{id}",
            "/jobDescriptions/delete/{id}",

            "/enquiries/list",
            "/enquiries/{id}",

            "/user/all",
            "/user/findById/{userId}"

    );
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*")); // Allow all origins, you can restrict it to specific origins
        config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())); // Allow specific HTTP methods
        config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class) // Add CORS filter
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(
                                    request -> permitAllUrls.contains(request.getServletPath())
                            ).permitAll()
                            .requestMatchers(
                                    request -> adminOnlyUrls.contains(request.getServletPath())
                            ).hasAuthority("ADMIN")
                            .requestMatchers(
                                    request -> userOnlyUrls.contains(request.getServletPath())
                            ).hasAuthority("USER")
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
