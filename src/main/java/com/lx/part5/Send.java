package com.lx.part5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Topics (通配符模式)
 * exchange: topic
 */
public class Send {
    static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String message = null;
            Scanner scanner = new Scanner(System.in);
            while (!"byebye".equals(message)) {
                message = scanner.nextLine();
                // exchange_name, routing key, 类型， 消息
                channel.basicPublish(EXCHANGE_NAME, "delete.xxx", null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[x] Send '" + message + "'");
            }
        }
    }
}
