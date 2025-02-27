package pl.javastart.mailapp.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/").permitAll()
                .requestMatchers("/styles/**").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/confirmation").permitAll()
                .requestMatchers("/confirm").permitAll()
                .requestMatchers("/password-reset").permitAll()
                .requestMatchers("/password-reset-form").permitAll()
                .requestMatchers("/change-password").permitAll()
                .anyRequest().authenticated());
        http.formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll());
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                .logoutSuccessUrl("/login?logout").permitAll()
        );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

