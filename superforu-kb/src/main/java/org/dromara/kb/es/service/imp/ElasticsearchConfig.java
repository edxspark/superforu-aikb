package org.dromara.kb.es.service.imp;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Value("${elasticsearch.host}")
    private  String ELASTICSEARCH_HOST;

    @Value("${elasticsearch.port}")
    private  Integer ELASTICSEARCH_PORT;

    @Value("${elasticsearch.username}")
    private  String ELASTICSEARCH_USERNAME;

    @Value("${elasticsearch.password}")
    private  String ELASTICSEARCH_PASSWORD;

    @Bean
    public ElasticsearchClient esClient() {
        String serverUrl = "http://"+ELASTICSEARCH_HOST+":"+ELASTICSEARCH_PORT;
        String userName = ELASTICSEARCH_USERNAME;
        String password = ELASTICSEARCH_PASSWORD;

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
            AuthScope.ANY, new UsernamePasswordCredentials(userName, password)
        );

        RestClient restClient = RestClient
            .builder(HttpHost.create(serverUrl))
            .setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credsProv))
            .build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }


}
