package com.manager.votemanager.config.security;

import com.manager.votemanager.models.v1.entity.User;
import com.manager.votemanager.models.v1.enums.RoleEnum;
import com.manager.votemanager.service.v1.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

    String ROLE_ADMIN = "ADMIN";
    String ROLE_COOPERATE = "COOPERATE";

    private final TokenSeervice tokenSeervice;

    private final UserService userService;

    @Autowired
    public SecurityConfigurations(TokenSeervice tokenSeervice, UserService userService) {
        this.tokenSeervice = tokenSeervice;
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.builder()
                .email("admin")
                .password(encoder().encode("admin"))
                .role(RoleEnum.ROLE_ADMIN).build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/v1/user/create").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/user/login").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/vote/vote").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/vote/vote").hasRole(ROLE_ADMIN)
                .antMatchers("/swagger-ui/#/").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("swagger-ui.html").permitAll()
                .antMatchers("/v1/**").hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationByTokenFilter(tokenSeervice, userService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/.html", "/v2/api-docs",
                        "/webjars/", "/configuration/", "/swagger-resources/","/swagger-ui/**");
    }
}
