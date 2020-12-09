package com.manulife.ap.config;

import com.manulife.ap.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weidejiang
 */
@Configuration
public class CoreConfig {

//    @Bean
//    public WrapRequestFilter wrapRequestFilter(){
//        return new WrapRequestFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(WrapRequestFilter wrapRequestFilter){
//        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//        registrationBean.setFilter(wrapRequestFilter);
//        registrationBean.setName("wrapRequestFilter");
//        registrationBean.setOrder(-10001);
//        return registrationBean;
//    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

}
