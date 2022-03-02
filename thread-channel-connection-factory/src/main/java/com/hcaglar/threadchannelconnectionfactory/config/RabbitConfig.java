package com.hcaglar.threadchannelconnectionfactory.config;

import com.hcaglar.threadchannelconnectionfactory.utilty.Queries;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Configuration
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

    @Value("${sp.rabbit.producer.exchange}")
    private String exchange;

    @Value("${sp.rabbit.producer.queue}")
    private String queue;

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(Queries.ROUTING_KEY);
    }

    @Bean
    public TaskExecutor exec () {
        final var t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(50);
        t.setAllowCoreThreadTimeOut(true);
        t.setKeepAliveSeconds(120);
        return t;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicy(){
        final var ebop = new ExponentialBackOffPolicy();
        ebop.setInitialInterval(500L);
        ebop.setMaxInterval(10000L);
        ebop.setMaxInterval(10L);
        return ebop;
    }

    @Bean
    public RetryTemplate retryTemplate(ExponentialBackOffPolicy backOffPolicy){
        final var retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

    @Bean
    public ThreadChannelConnectionFactory threadChannelConnectionFactory(ConnectionFactory tcf){
        return new ThreadChannelConnectionFactory(tcf);
    }

    @Bean
    public ConnectionFactory tcf(){
        final var connection = new ConnectionFactory();
        connection.setHost(host);
        connection.setPort(port);
        connection.setUsername(username);
        connection.setPassword(password);
        return connection;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RetryTemplate retryTemplate, ThreadChannelConnectionFactory threadChannelConnectionFactory){
        final var rb = new RabbitTemplate();
        rb.setRetryTemplate(retryTemplate);
        rb.setConnectionFactory(threadChannelConnectionFactory);
        return rb;
    }
}
