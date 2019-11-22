package com.lx.part1;

import com.lx.constants.ConnectionsMessage;
import com.lx.constants.QueueName;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv {

    private static final String QUEUE_NAME = QueueName.QUEUE_NAME_PART1;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionsMessage.HOST);
        factory.setUsername(ConnectionsMessage.USER);
        factory.setPassword(ConnectionsMessage.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
