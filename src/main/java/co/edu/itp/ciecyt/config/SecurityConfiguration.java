package co.edu.itp.ciecyt.config;

import co.edu.itp.ciecyt.security.*;
import co.edu.itp.ciecyt.security.jwt.JWTFilter;
import co.edu.itp.ciecyt.security.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new TruncatingPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport))
            .headers(headers -> headers
                .contentSecurityPolicy(contentSecurityPolicy -> contentSecurityPolicy
                    .policyDirectives("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; img-src 'self' data: https://fonts.gstatic.com"))
                .referrerPolicy(referrerPolicy -> referrerPolicy
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                .frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/app/**").permitAll()
                .requestMatchers("/i18n/**").permitAll()
                .requestMatchers("/content/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/test/**").permitAll()
                .requestMatchers("/api/authenticate").permitAll()
                .requestMatchers("/api/register").permitAll()
                .requestMatchers("/api/activate").permitAll()
                .requestMatchers("/api/menus-no-page").permitAll()
                .requestMatchers("/api/menus-roles/**").permitAll()
                .requestMatchers("/api/account/reset-password/init").permitAll()
                .requestMatchers("/api/account/reset-password/finish").permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/management/health").permitAll()
                .requestMatchers("/management/info").permitAll()
                .requestMatchers("/management/prometheus").permitAll()
                .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN))
            .httpBasic(Customizer.withDefaults());
        // @formatter:on
        return http.build();
    }
}
