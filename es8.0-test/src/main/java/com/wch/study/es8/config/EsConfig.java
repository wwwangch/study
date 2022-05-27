package com.wch.study.es8.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.wch.study.es8.interceptor.ElasticsearchInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @Author wch
 * @Date 2022/1/26 10:47
 * @Version 1.0
 */
@Configuration
@Slf4j
public class EsConfig {

    @Bean
    public ElasticsearchClient esWriteClient() {
        try {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "+=qH6_fok2BiUmo1dNKB"));

            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream is = EsConfig.class.getClassLoader().getResourceAsStream("http_ca.crt")) {
                trustedCa = factory.generateCertificate(is);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
            final SSLContext sslContext = sslContextBuilder.build();

            HttpHost httpHost = new HttpHost("172.16.10.198", 9200, "https");
            final ElasticsearchInterceptor elasticsearchInterceptor = new ElasticsearchInterceptor();
            RestClientBuilder builder = RestClient.builder(httpHost)
                    .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                            .setSSLContext(sslContext)
                            .setDefaultCredentialsProvider(credentialsProvider)
                            .addInterceptorLast(elasticsearchInterceptor));

            RestClient restClient = builder.build();
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            ElasticsearchClient elasticsearchClient = new ElasticsearchClient(transport);
            return elasticsearchClient;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("es客户端初始化失败");
        }
        return null;
    }
}
