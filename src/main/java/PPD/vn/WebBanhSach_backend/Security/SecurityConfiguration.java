package PPD.vn.WebBanhSach_backend.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.Arrays;


@Configuration
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //cấu hình mã hóa mật khẩu
    @Bean
    public JdbcUserDetailsManager authenticationProvider(DataSource dataSource){
        JdbcUserDetailsManager daoAuthenticationProvider = new JdbcUserDetailsManager(dataSource);
        daoAuthenticationProvider.setUsersByUsernameQuery("");
        daoAuthenticationProvider.setAuthoritiesByUsernameQuery("");
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
       security.authorizeHttpRequests(
               configurer->configurer
                       .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENPOINT).permitAll()
                       .requestMatchers(HttpMethod.PUT, Endpoints.PUBLIC_GET_ENPOINT).permitAll()
                       .requestMatchers(HttpMethod.POST, Endpoints.User_POST_ENPOINT_DangKi).hasAuthority("User")
                       .requestMatchers(HttpMethod.POST,Endpoints.PUBLIC_POST_ENPOINT_DangKi).permitAll()
                       .requestMatchers(HttpMethod.DELETE,Endpoints.PUBLIC_POST_ENPOINT_DangKi).permitAll()
                       .requestMatchers(HttpMethod.POST,Endpoints.ADMIN_POST_ENPOINT_DangKi).hasAuthority("Admin")
                       .anyRequest().authenticated()
       ).formLogin(form->form
               .loginPage("/dang-nhap")
               .permitAll()
       );
       security.csrf(csrf -> csrf.disable());
       return  security.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
