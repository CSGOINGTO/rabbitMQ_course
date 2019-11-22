package com.lx.part1;

import com.lx.constants.ConnectionsMessage;
import com.lx.constants.QueueName;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = QueueName.QUEUE_NAME_PART1;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionsMessage.HOST);
        factory.setUsername(ConnectionsMessage.USER);
        factory.setPassword(ConnectionsMessage.PASSWORD);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner in = new Scanner(System.in)) {
            // 队列声明
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = in.next();
            while (!"再见".equals(message)) {
                // 发送到默认的exchange， queue的名字就是queue_name，消息类型，消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                message = in.next();
            }
            System.out.println(" [x] Send '" + message + "'");
        }

    }
}
