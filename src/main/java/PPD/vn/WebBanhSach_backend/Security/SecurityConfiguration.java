package PPD.vn.WebBanhSach_backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select ten_dang_nhap, mat_khau, is_kich_hoat from nguoi_dung where ten_dang_nhap = ? ");
        manager.setAuthoritiesByUsernameQuery(
                "select nguoi_dung.ten_dang_nhap, quyen.ten_quyen \n" +
                        "from [dbo].[nguoi_dung] \n" +
                        "join [dbo].[nguoidung_quyen] on nguoi_dung.ma_nguoi_dung = nguoidung_quyen.ma_nguoi_dung \n" +
                        "join [dbo].[quyen] on quyen.ma_quyen = nguoidung_quyen.ma_quyen \n" +
                        "where ten_dang_nhap = ?"
        );
        System.out.println(manager.getUsersByUsernameQuery());


        return manager ;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        System.out.println("SecurityFilterChain đang được sử dụng...");

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
               .loginProcessingUrl("/kiemtradangnhap")
               .usernameParameter("accountname")
               .passwordParameter("password")
               .defaultSuccessUrl("/trang-chu",true)
               .failureUrl("/dang-nhap?error")
               .permitAll()
       ).logout(logout->logout
                       .invalidateHttpSession(true)
                       .logoutUrl("/dang-xuat")
                       .logoutSuccessUrl("/dang-nhap")
                       .deleteCookies("JSESSIONID", "remember-me")
                       .permitAll()
                ).rememberMe(rememberMe->rememberMe
                        .tokenValiditySeconds(86400)
                        .key("mySecretkey")
                )
       .httpBasic(Customizer.withDefaults());
       security.csrf(csrf -> csrf.disable());
       return  security.build();
    }

}
