package com.hcaglar.choosingconnectionfactorypool.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 2.03.2022
 */
@Configuration
@Slf4j
public class RabbitConfig {
    /**
     * @see @ConfigurationProperties {@link <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.external-config.typesafe-configuration-properties">@</a>}
     * can be used as type-safe Configuration Properties.It is possible to write an expression with the @Value spell.
     * but it reduces readability.
     */
    @Value("${sp.rabbit.host}")
    private String host;
    @Value("${sp.rabbit.port}")
    private int port;
    @Value("${sp.rabbit.username}")
    private String username;
    @Value("${sp.rabbit.password}")
    private String password;

    @Value("${sp.rabbit.producer.routing-key}")
    private String routingKey;
    @Value("${sp.rabbit.producer.exchange}")
    private String exchange;
    @Value("${sp.rabbit.producer.queue}")
    private String queue;

    public RabbitConfig() {}

    @Bean
    public PooledChannelConnectionFactory pcf(){
        return poolConfigurer(connection());
    }

    @Bean
    public RetryTemplate retryTemplate(){
        return new RetryTemplate();
    }

    @Bean
    public ExponentialBackOffPolicy exponentialBackOff(){
       return backOffPolicy();
    }

    @Bean("template")
    public RabbitTemplate rabbitTemplate(PooledChannelConnectionFactory pcf){
        return new RabbitTemplate(pcf);
    }

    @Bean
    public Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplate template, RetryTemplate retryTemplate,
                                         ExponentialBackOffPolicy exponentialBackOff){
        retryTemplate.setBackOffPolicy(exponentialBackOff);
        template.setRetryTemplate(retryTemplate);
        return template;
    }

    private ConnectionFactory connection(){
        final var connection = new ConnectionFactory();
        connection.setHost(host);
        connection.setPort(port);
        connection.setUsername(username);
        connection.setPassword(password);
        return connection;
    }

    private PooledChannelConnectionFactory poolConfigurer(ConnectionFactory connection){
        final var pccf = new PooledChannelConnectionFactory(connection);
        pccf.setPoolConfigurer((pool, tx) -> {
            if (tx){
                log("transactional pool");
            }else {
                log("non-transactional pool");
            }
        });
        return pccf;
    }

    private ExponentialBackOffPolicy backOffPolicy(){
        final var ebop = new ExponentialBackOffPolicy();
        ebop.setInitialInterval(500L);
        ebop.setMaxInterval(10000L);
        ebop.setMaxInterval(10L);
        return ebop;
    }

    private void log(String message){
         log.info(String.format("%s %s",getClass(), message.trim()));
    }
}
