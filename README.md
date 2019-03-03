# CuratorDemo
zookeeper curator demo
包含Curator的基本操作，包含增加、删除以及相关查看等操作，还有相关的监视
zk相关命令：zkCli.sh –server 10.77.20.23:2181
create /zk myData
ls /
get /zk
delete /zk
bin/zkServer.sh start
bin/zkServer.sh status
bin/zkServer.sh stop
bin/zkServer.sh restart 

kafka
kafka生产消息，使用kafkalister接受消息demo实现

kafka相关命令：
bin/kafka-topics.sh --create --topic test0 --zookeeper 192.168.187.146:2181 --config max.message.bytes=12800000 --config flush.messages=1 --partitions 5 --replication-factor 1
bin/kafka-topics.sh --list --zookeeper 192.168.187.146:2181
bin/kafka-topics.sh --describe --zookeeper 192.168.187.146:2181  --topic test0
./bin/kafka-server-start.sh ./config/server.properties
./bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic test
./bin/kafka-console-consumer.sh --zookeeper 127.0.0.1:2181 --topic test --from-beginning


