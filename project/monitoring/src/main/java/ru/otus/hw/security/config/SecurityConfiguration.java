package ru.otus.hw.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    final private UserDetailsService appUserDetailsService;

    public SecurityConfiguration(UserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/admin").hasRole("ADMIN")//permitAll()
                                .requestMatchers("/auth").authenticated()

                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/actuator/health/*").access(hasRole("HEAD"))
                                // защита перенесена в контроллер
                                // .requestMatchers("/api/v1/incident/*").hasAnyRole("ADMIN", "OPERATOR")
                                //.requestMatchers(HttpMethod.PATCH, "/api/v1/incident/*").hasRole("OPERATOR")
                                //.anyRequest().denyAll()
                                .anyRequest().authenticated()
                )
                .httpBasic();  // делаем PATCH из постмана
                //.formLogin(form -> form.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
