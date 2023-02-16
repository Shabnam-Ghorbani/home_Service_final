package ir.maktab.home_service.config;

import ir.maktab.home_service.data.model.repository.PersonRepository;
import ir.maktab.home_service.exception.EmailAddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(PersonRepository personRepository, BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/signup/**").permitAll()
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .requestMatchers("/expert/**").hasRole("EXPERT")
                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(
                        emailAddress -> personRepository
                                .findByEmailAddress(emailAddress)
                                .orElseThrow(
                                        () -> new EmailAddressNotFoundException(
                                                String.format("this emailAddress not exist", emailAddress))))
                .passwordEncoder(passwordEncoder);
    }
}
