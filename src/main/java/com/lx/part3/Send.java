package com.lx.part3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * publish/subscribe(发布/订阅模式)
 * fanout exchange
 */
public class Send {
    private static final String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {
            // 声明exchange的模式为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = "Test fanout exchange";
            // 发送消息
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Send '" + message + "'");
        }
    }
}
