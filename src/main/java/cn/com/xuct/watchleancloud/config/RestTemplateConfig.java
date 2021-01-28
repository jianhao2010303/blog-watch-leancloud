/**
 * Copyright (C), 2015-2020, xuct.net
 * FileName: RestTemplateConfig
 * Author:   xutao
 * Date:     2020/11/18 15:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.watchleancloud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author xutao
 * @create 2020/11/18
 * @since 1.0.0
 */
@Configuration
public class RestTemplateConfig {

    @Value("${ok.http.connect-timeout:1}" )
    private Integer connectTimeout;

    @Value("${ok.http.read-timeout:3}")
    private Integer readTimeout;

    @Value("${ok.http.write-timeout:3}")
    private Integer writeTimeout;

    /* 连接池中整体的空闲连接的最大数量 */
    @Value("${ok.http.max-idle-connections:200}")
    private Integer maxIdleConnections;

    /* 连接空闲时间最多为 300 秒*/
    @Value("${ok.http.keep-alive-duration:300}")
    private Long keepAliveDuration;


    /**
     * 声明 RestTemplate
     */
    @Bean
    public RestTemplate httpRestTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", Charset.defaultCharset()),
                        new MediaType("text", "javascript", Charset.defaultCharset())));
            }
        }
        // 可以增加拦截器
        //restTemplate.setInterceptors(...);
        return restTemplate;
    }


    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
    }

    public OkHttpClient okHttpConfigClient() {
        return new OkHttpClient().newBuilder()
                .connectionPool(pool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    public ConnectionPool pool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }
}