package com.project.CourseCraft.config;

import com.project.CourseCraft.security.JWTAuthenticationEntryPoint;
import com.project.CourseCraft.security.JwtAuthenticationFilter;
import com.project.CourseCraft.security.UserDetailServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableCaching
public class SecurityConfig {

    @Autowired
    private UserDetailServiceCustom userDetailServiceCustom;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain for handling HTTP security.
     * This method is responsible for defining the security rules that control access to different endpoints and requests within the application.
     * It specifies the authentication requirements and authorization roles for each endpoint, determining who can access which resources.
     *
     * @param http the HttpSecurity object used to configure the security filters
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("Reached At Security Config !");

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow all requests to "/auth/**" without authentication
                .requestMatchers(HttpMethod.GET, AppConstants.URL_ADMIN_ONLY_GET).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, AppConstants.URL_ADMIN_ONLY_POST).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, AppConstants.URL_ADMIN_ONLY_PUT).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, AppConstants.URL_ADMIN_ONLY_DELETE).hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, AppConstants.URL_ADMIN_STAFF_GET).hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.POST, AppConstants.URL_ADMIN_STAFF_POST).hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.PUT, AppConstants.URL_ADMIN_STAFF_PUT).hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.DELETE, AppConstants.URL_ADMIN_STAFF_DELETE).hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.GET, AppConstants.URL_ADMIN_STUDENT_GET).hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers(HttpMethod.POST, AppConstants.URL_ADMIN_STUDENT_POST).hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers(HttpMethod.PUT, AppConstants.URL_ADMIN_STUDENT_PUT).hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers(HttpMethod.DELETE, AppConstants.URL_ADMIN_STUDENT_DELETE).hasAnyRole("ADMIN", "STUDENT")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();

        return http.build();
    }

    /**
     * Configures the authentication manager to use the custom user details service and password encoder.
     *
     * @param auth the AuthenticationManagerBuilder object for configuring the authentication manager
     * @throws Exception if an error occurs during configuration
     */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailServiceCustom).passwordEncoder(passwordEncoder());
    }

    /**
     * Creates a BCrypt password encoder bean.
     *
     * @return the BCryptPasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an AuthenticationManager bean.
     *
     * @param configuration the AuthenticationConfiguration object for obtaining the authentication manager
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs during bean creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Creates a DaoAuthenticationProvider bean for authentication.
     *
     * @return the DaoAuthenticationProvider bean
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailServiceCustom);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
