package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by yangyu on 13/8/17.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");

//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/403").setViewName("403");

    }
    */

//    @Bean(name="dataSource")
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/test");
//        driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword("123456");
//        return driverManagerDataSource;
//    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                super.addCorsMappings(registry);
                registry.addMapping("/api/*").allowedOrigins("http://localhost:3000");
//                registry.addMapping("/api/profile_delete").allowedOrigins("http://localhost:3000");
            }
        };
    }

//    @Bean
//    public InternalResourceViewResolver viewREsolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/jsp");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }


}
