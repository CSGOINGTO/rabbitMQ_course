package com.lx.part2;

import com.lx.constants.ConnectionsMessage;
import com.lx.constants.QueueName;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Work {

    private static final String QUEUE_NAME = QueueName.QUEUE_NAME_PART2;

    private static ConnectionFactory factory = new ConnectionFactory();
    private static Channel channel;


    static {
        try {
            factory.setHost(ConnectionsMessage.HOST);
            factory.setUsername(ConnectionsMessage.USER);
            factory.setPassword(ConnectionsMessage.PASSWORD);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 队列名，是否持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 同一时刻生产者之后发送一条消息给消费者
        channel.basicQos(1);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                Work2.doSomething(delivery);
                // 手动确认消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        // false:手动返回完成状态 true:表示自动
        // 队列名， 是否自动确认消息
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
