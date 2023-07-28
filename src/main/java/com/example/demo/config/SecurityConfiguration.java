package com.example.demo.config;

import com.example.demo.service.impl.CustomUserDetailsService;
import com.example.demo.utils.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(
prePostEnabled = true,
securedEnabled = true,
jsr250Enabled = true)
/*The prePostEnabled property enables Spring Security pre/post annotations.
The securedEnabled property determines if the @Secured annotation should be enabled.
The jsr250Enabled property allows us to use the @RoleAllowed annotation. */
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public CustomAuthenticationProvider myAuthProvider() throws Exception {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthProvider());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize) -> {
                    authorize
                        .requestMatchers("/user/login", "/user/test").permitAll()
                        .anyRequest().authenticated();
                        }
                );
        http.authenticationProvider(myAuthProvider());


        return http.build();
    }
}
