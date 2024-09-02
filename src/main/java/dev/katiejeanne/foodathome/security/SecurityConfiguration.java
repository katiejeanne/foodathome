package dev.katiejeanne.foodathome.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;




@Configuration
public class SecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;


    @Autowired
    public SecurityConfiguration (CustomUserDetailsService customUserDetailsService) {
        this.userDetailsService = customUserDetailsService;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers("/login", "/css/**", "/register").permitAll()
                        .anyRequest().authenticated())
                        .formLogin(form -> form
                                .loginPage("/login")
                                .permitAll())
                        .logout(LogoutConfigurer::permitAll);


        return http.build();

    }

}
