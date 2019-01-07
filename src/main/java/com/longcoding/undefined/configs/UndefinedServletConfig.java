package com.longcoding.undefined.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.longcoding.undefined.interceptors.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by longcoding on 16. 4. 5..
 * Updated by longcoding on 18. 12. 26..
 */
@Configuration
public class UndefinedServletConfig implements WebMvcConfigurer {

    private static final String PATH_INTERNAL_API = "/internal/**";
    private static final String[] EXCLUDE_INTERCEPTOR_PATH = { PATH_INTERNAL_API };

    @Bean
    public InitializeInterceptor initializeInterceptor() { return new InitializeInterceptor(); }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() { return new AuthenticationInterceptor(); }
    @Bean
    public PathAndAppAndPrepareRedisInterceptor pathAndPrepareRedisInterceptor() { return new PathAndAppAndPrepareRedisInterceptor(); }
    @Bean
    public ServiceCapacityInterceptor serviceCapacityInterceptor() {
        return new ServiceCapacityInterceptor();
    }
    @Bean
    public ExecuteRedisValidationInterceptor executeRedisValidationInterceptor() { return new ExecuteRedisValidationInterceptor(); }
    @Bean
    public ExtractRequestInterceptor extractRequestInterceptor() { return new ExtractRequestInterceptor(); }
    @Bean
    public HeaderAndQueryValidationInterceptor headerAndQueryValidationInterceptor() { return new HeaderAndQueryValidationInterceptor(); }
    @Bean
    public PrepareProxyInterceptor prepareProxyInterceptor() { return new PrepareProxyInterceptor(); }
    @Bean
    public ApplicationRatelimitInterceptor applicationRatelimitInterceptor() { return new ApplicationRatelimitInterceptor(); }
    @Bean
    public ServiceContractValidationInterceptor serviceContractValidationInterceptor() { return new ServiceContractValidationInterceptor(); }
    @Bean
    public TransformRequestInterceptor transformRequestInterceptor() { return new TransformRequestInterceptor(); }

//    @Bean
//    public EhcacheConfigureFactory ehcacheConfigureFactory() { return new EhcacheConfigureFactory(); }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initializeInterceptor());
        registry.addInterceptor(authenticationInterceptor());
        registry.addInterceptor(pathAndPrepareRedisInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(serviceContractValidationInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(serviceCapacityInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(applicationRatelimitInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(executeRedisValidationInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(extractRequestInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(headerAndQueryValidationInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(transformRequestInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
        registry.addInterceptor(prepareProxyInterceptor()).excludePathPatterns(EXCLUDE_INTERCEPTOR_PATH);
    }

    @Bean
    MappingJackson2HttpMessageConverter converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
        mapper.disable( SerializationFeature.FAIL_ON_EMPTY_BEANS );
        mapper.disable( MapperFeature.DEFAULT_VIEW_INCLUSION );
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        converter.setObjectMapper( mapper );
        return converter;
    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        converters.add(converter());
    }

}
