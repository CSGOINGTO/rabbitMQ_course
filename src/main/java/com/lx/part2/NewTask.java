package com.lx.part2;

import com.lx.constants.ConnectionsMessage;
import com.lx.constants.QueueName;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class NewTask {

    private static final String QUEUE_NAME = QueueName.QUEUE_NAME_PART2;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionsMessage.HOST);
        factory.setUsername(ConnectionsMessage.USER);
        factory.setPassword(ConnectionsMessage.PASSWORD);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner in = new Scanner(System.in)) {
            // 队列名，是否持久化
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append("hello");
            for (int i = 0; i < 100; i++) {
                // 默认的exchange， 队列名， 消息类型， 消息
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, (i + " : " + stringBuffer.append(".").toString() + ".").getBytes());
            }
        }
    }

}
