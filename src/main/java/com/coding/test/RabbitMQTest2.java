package com.coding.test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest2 {

    static ConnectionFactory connectionFactory;
    static Connection connection;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void producer() throws Exception {
        // 设置单条消息的过期时间的方法：
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding("UTF-8")
                .expiration("10000") // TTL，10秒后没有被消费则被发送到DLX
                .build();

        // 设置队列的消息过期时间的方法：
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-message-ttl", 6000); // TTL，6秒后没有被消费则被发送到DLX
        Channel channel = connection.createChannel();
        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, argss);

        // 注意：如果同时设置了消息的过期时间和队列的消息过期时间，则会取其中一个较小的值。比如消息设置5秒过期，队列设置消息10秒过期，则实际过期时间是5秒。
        // 基于消息TTL，我们来看一下如何利用死信队列（DLQ）实现延时队列：
        // 总体步骤：
        // 1）创建一个交换机
        // 2）创建一个队列，与上述交换机绑定，并且通过属性指定队列的死信交换机。
        // 3）创建一个死信交换机
        // 4）创建一个死信队列
        // 5）将死信交换机绑定到死信队列
        // 6）消费者监听死信队列

        String msg = "Hello world, Rabbit MQ, DLX MSG";
        // 发送消息
        channel.basicPublish("", "TEST_DLX_QUEUE", properties, msg.getBytes());

        // 消息的流转流程
        // 生产者——原交换机——原队列——（超过TTL之后）——死信交换机——死信队列——最终消费者
    }

    private static void consume() throws Exception {
        Channel channel = connection.createChannel();

        // 指定队列的死信交换机
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", "DLX_EXCHANGE");
        arguments.put("x-expires", "9000"); // 设置队列的TTL

        // durable：是否要持久化；exclusive：如果设置true的化，队列将变成私有的，只有创建队列的应用程序才能够消费队列消息；
        // queueDeclare(String queue, boolean durable, boolean exclusive, Map<String, Object> arguments);


        // 声明队列（默认交换机AMQP default，Direct）
        channel.queueDeclare("TEST_DLX_QUEUE", false, false, false, arguments);

        // 声明死信交换机
        channel.exchangeDeclare("DLX_EXCHANGE", "topic", false, false, false, null);

        // 声明死信队列
        channel.queueDeclare("DLX_QUEUE", false, false, false, null);

        // 绑定，此处 Dead letter routing key 设置为 #，代表路由所有消息
        channel.queueBind("DLX_QUEUE", "DLX_EXCHANGE", "#");
    }
}
