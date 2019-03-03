package com.example.curatordemo.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * bootstrap.servers：该属性指定broker的地址清单，地址的格式为host：port。清单里不需要包含所有的broker地址，生产者会从给定的broker里查询其他broker的信息。不过最少提供2个broker的信息，一旦其中一个宕机，生产者仍能连接到集群上。
 * key.serializer:生产者接口允许使用参数化类型，可以把Java对象作为键和值传broker，但是broker希望收到的消息的键和值都是字节数组，所以，必须提供将对象序列化成字节数组的序列化器。key.serializer必须设置为实现org.apache.kafka.common.serialization.Serializer的接口类，默认为
 *
 * org.apache.kafka.common.serialization.StringSerializer，也可以实现自定义的序列化器。
 * value.serializer:同上。
 *
 * 可选参数：
 *
 * acks：指定了必须要有多少个分区副本收到消息，生产者才会认为写入消息是成功的，这个参数对消息丢失的可能性有重大影响。
 *
 * acks=0：生产者在写入消息之前不会等待任何来自服务器的响应，容易丢消息，但是吞吐量高。
 *
 * acks=1：只要集群的首领节点收到消息，生产者会收到来自服务器的成功响应。如果消息无法到达首领节点（比如首领节点崩溃，新首领没有选举出来），生产者会收到一个错误响应，为了避免数据丢失，生产者会重发消息。不过，如果一个没有收到消息的节点成为新首领，消息还是会丢失。默认使用这个配置。
 *
 * acks=all：只有当所有参与复制的节点都收到消息，生产者才会收到一个来自服务器的成功响应。延迟高。
 *
 * buffer.memory:设置生产者内存缓冲区的大小，生产者用它缓冲要发送到服务器的消息。
 *
 * max.block.ms:指定了在调用send()方法或者使用partitionsFor()方法获取元数据时生产者的阻塞时间。当生产者的发送缓冲区已满，或者没有可用的元数据时，这些方法就会阻塞。在阻塞时间达到max.block.ms时，生产者会抛出超时异常。
 *
 * batch.size:当多个消息被发送同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。当批次内存被填满后，批次里的所有消息会被发送出去。
 *
 * retries:指定生产者可以重发消息的次数。
 *
 * receive.buffer.bytes和send.buffer.bytes:指定TCP socket接受和发送数据包的缓存区大小。如果它们被设置为-1，则使用操作系统的默认值。如果生产者或消费者处在不同的数据中心，那么可以适当增大这些值，因为跨数据中心的网络一般都有比较高的延迟和比较低的带宽。
 *
 * linger.ms:指定了生产者在发送批次前等待更多消息加入批次的时间
 */

public class KafkaProducerTest {
    public static void main(String[] args) throws InterruptedException{
        Properties p = new Properties();
        p.put("bootstrap.servers", "39.96.85.85:9092");
        p.put("acks","all");
        p.put("retries",0);
        p.put("batch.size",16384);
        p.put("linger.ms",1);
        p.put("buffer.memory", 33554432);
        p.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        p.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        KafkaProducer<String,String> producer = new KafkaProducer<String,String>(p);
    }
}
