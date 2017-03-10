package org.contextual.dashboard;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by msalatino on 31/01/2017.
 */
@SpringBootApplication
public class Application {


    @Bean(name = "context-queue")
    Queue contextQueue() {
        return new Queue(System.getProperty("QUEUE_NAME", "context-queue"), false);
    }

    @Bean(name = "domain-queue")
    Queue domainQueue() {
        return new Queue(System.getProperty("QUEUE_NAME", "domain-queue"), false);
    }

    @Bean(name = "executor-queue")
    Queue executorQueue() {
        return new Queue(System.getProperty("QUEUE_NAME", "executor-queue"), false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean(name = "context-binding")
    Binding bindingContext(@Qualifier("context-queue")Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(System.getProperty("QUEUE_NAME", "context-queue"));
    }


    @Bean(name = "domain-binding")
    Binding bindingDomain(@Qualifier("domain-queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(System.getProperty("QUEUE_NAME", "domain-queue"));
    }

    @Bean(name = "executor-binding")
    Binding bindingExecutor(@Qualifier("executor-queue")Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(System.getProperty("QUEUE_NAME", "executor-queue"));
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(System.getProperty("QUEUE_NAME", "domain-queue"),
                System.getProperty("QUEUE_NAME", "context-queue"),
                System.getProperty("QUEUE_NAME", "executor-queue"));
        container.setMessageListener(listenerAdapter);
        return container;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }
}