package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by yangyu on 13/8/17.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(accountDataSource())
                .usersByUsernameQuery("select username, password, enabled from user_account where username=?")
                .authoritiesByUsernameQuery("select username, role from user_account_roles where username=?");
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin().loginPage("/login")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutSuccessUrl("/login?logout")
//                .and()
//                .csrf();
//    }

/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/login", "/user/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .permitAll()
//                .loginPage("/login")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//                .csrf().disable();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("*").permitAll();

    }
    */


//    @Autowired
//    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
//        // @formatter:off
//
//
//        auth.inMemoryAuthentication()
//                .withUser("john").password("123").roles("USER").and()
//                .withUser("tom").password("111").roles("ADMIN").and()
//                .withUser("user1").password("pass").roles("USER").and()
//                .withUser("admin").password("nimda").roles("ADMIN");
//    }// @formatter:on

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll();
        // @formatter:on
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }

    public DataSource accountDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/users");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("123456");
        return driverManagerDataSource;
    }


}
