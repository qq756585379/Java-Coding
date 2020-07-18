package com.coding.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {

    private static ConnectionFactory connectionFactory;
    private static Connection connection;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        producer();

        Thread thread = new Thread(() -> {
            try {
                consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private static void producer() throws Exception {
        Channel channel = connection.createChannel();//创建一个channel，不管是生产数据，还是消费数据，都是通过channel去操作的
        channel.exchangeDeclare("orderExchange", "direct", true);//定义一个交换机，路由类型为direct，所有的订单会塞给此交换机
        channel.exchangeDeclare("orderDelayExchange", "direct", true);//定义一个交换机，路由类型为direct，延迟的订单会塞给此交换机

        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "orderDelayExchange");//申明死信交换机是名称为orderDelayExchange的交换机
        //定义一个名称为order_queue的队列，绑定上面定义的参数，这样就告诉rabbit此队列延迟的消息，发送给orderDelayExchange交换机
        channel.queueDeclare("order_queue", true, false, false, arguments);
        //定义一个名称为order_delay_queue的队列
        channel.queueDeclare("order_delay_queue", true, false, false, null);

        //order_queue和orderExchange绑定，路由为delay。路由也为delay的消息会通过orderExchange进入到order_queue队列
        channel.queueBind("order_queue", "orderExchange", "delay");
        //order_delay_queue和orderDelayExchange绑定
        channel.queueBind("order_delay_queue", "orderDelayExchange", "delay");

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.expiration("15000");//设置消息TTL（消息生存时间）
        builder.deliveryMode(2);//设置消息持久化
        AMQP.BasicProperties properties = builder.build();

        Thread productThread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                String order = "order" + i;
                try {
                    //通过channel，向orderExchange交换机发送路由为delay的消息，这样就可以进入到order_queue队列
                    channel.basicPublish("orderExchange", "delay", properties, order.getBytes());
                    String str = "现在时间是" + new Date().toString() + "  " + order + "  的消息产生了";
                    System.out.println(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                channel.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        productThread.start();
    }

    private static void consume() throws Exception {
        Channel channel = connection.createChannel();//创建一个channel，不管是生产数据，还是消费数据，都是通过channel去操作的
        //消费名称为order_delay_queue的队列，且关闭自动应答，需要手动应答
        channel.basicConsume("order_delay_queue", false, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();//消息的标记，应答的时候需要传入这个参数
                String str = "现在时间是" + new Date().toString() + "  " + new String(body) + "  的消息消费了";
                System.out.println(str);
                channel.basicAck(deliveryTag, false);//手动应答，代表这个消息处理完成了
            }
        });
    }
}

