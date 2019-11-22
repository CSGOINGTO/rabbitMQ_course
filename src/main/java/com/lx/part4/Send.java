package com.lx.part4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 路由器模式
 * direct exchange
 */
public class Send {
    public static final String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String message = null;
            Scanner scanner = new Scanner(System.in);
            while (!"bebe".equals(message)) {
                message = scanner.nextLine();
                if (message.contains("d")) {
                    channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes(StandardCharsets.UTF_8));
                }
                if (message.contains("u")) {
                    channel.basicPublish(EXCHANGE_NAME, "update", null, message.getBytes(StandardCharsets.UTF_8));
                }
                System.out.println("[x] Send '" + message + "'");
            }
        }

    }
}
