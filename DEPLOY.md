1、mysql docker 部署

    docker run --name=mysql --env=MYSQL_ROOT_PASSWORD=密码 --volume=服务器目录:/etc/mysql/conf.d --volume=服务器目录:/var/lib/mysql --privileged -p 映射端口:3306 --restart=no --runtime=runc --detach=true mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci

2、redis docker 部署

    docker run --name=redis --env=REDIS_PASSWORD=密码 --volume=服务器目录:/data --privileged -p 映射端口:6379 --restart=no --runtime=runc --detach=true redis:5.0

3、rocketmq docker 部署
 （1）拉取rocketMQ最新版本镜像 

    docker pull rocketmqinc/rocketmq
    
 (2) 创建nameserver数据存储目录
    rocketMQ 分为nameserver和broker两部分，在启动时应该先启动nameserver，因此我们现在先创建nameserver的日志和数据存放目录。这个目录可由我们自己定义路径，这里我将其放到data路径下:

    mkdir -p /data/namesrv/logs /data/namesrv/store

（3）构建namesrv容器并启动
        
    docker run -d  --restart=always --name rmqnamesrv -p 9876:9876  -v  /data/namesrv/logs:/root/logs  -v /data/namesrv/store:/root/store  -e "MAX_POSSIBLE_HEAP=100000000"  rocketmqinc/rocketmq sh mqnamesrv
 
   执行完nameserver容器启动命名后，可以通过docker ps查看是否启动成功

（4）创建broker数据存储路径
       
     mkdir -p /data/broker/logs  /data/broker/store /data/broker/conf

（5）创建broker配置文件

    cd /data/broker/conf
    vi broker.conf

  请将一下信息复制到broker.conf文件中，请注意修改brokerIP1，改为您服务器的IP地址！！！！请注意修改namesrvAddr，改为您nameserver的IP地址！！！！

        # Licensed to the Apache Software Foundation (ASF) under one or more
        # contributor license agreements.  See the NOTICE file distributed with
        # this work for additional information regarding copyright ownership.
        # The ASF licenses this file to You under the Apache License, Version 2.0
        # (the "License"); you may not use this file except in compliance with
        # the License.  You may obtain a copy of the License at
        #
        #     http://www.apache.org/licenses/LICENSE-2.0
        #
        #  Unless required by applicable law or agreed to in writing, software
        #  distributed under the License is distributed on an "AS IS" BASIS,
        #  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        #  See the License for the specific language governing permissions and
        #  limitations under the License.
        
        
        # 所属集群名字
        brokerClusterName=DefaultCluster
        # broker 名字，注意此处不同的配置文件填写的不一样，如果在 broker-a.properties 使用: broker-a,
        # 在 broker-b.properties 使用: broker-b
        brokerName=broker-a
        # 0 表示 Master，> 0 表示 Slave
        brokerId=0
        # nameServer地址，分号分割
        namesrvAddr=rmqserver01:9876
        # 启动IP,如果 docker 报 com.alibaba.rocketmq.remoting.exception.RemotingConnectException: connect to <192.168.0.120:10909> failed
        # 解决方式1 加上一句 producer.setVipChannelEnabled(false);，解决方式2 brokerIP1 设置宿主机IP，不要使用docker 内部IP
        #producer.setVipChannelEnabled=false
        brokerIP1=192.168.101.220
        
        # 在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
        defaultTopicQueueNums=4
        # 是否允许 Broker 自动创建 Topic，建议线下开启，线上关闭 ！！！这里仔细看是 false，false，false
        autoCreateTopicEnable=true
        # 是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
        autoCreateSubscriptionGroup=true
        # Broker 对外服务的监听端口
        listenPort=10911
        # 删除文件时间点，默认凌晨4点
        deleteWhen=04
        # 文件保留时间，默认48小时
        fileReservedTime=120
        # commitLog 每个文件的大小默认1G
        mapedFileSizeCommitLog=1073741824
        # ConsumeQueue 每个文件默认存 30W 条，根据业务情况调整
        mapedFileSizeConsumeQueue=300000
        # destroyMapedFileIntervalForcibly=120000
        # redeleteHangedFileInterval=120000
        # 检测物理文件磁盘空间
        diskMaxUsedSpaceRatio=88
        # 存储路径
        # storePathRootDir=/home/ztztdata/rocketmq-all-4.1.0-incubating/store
        # commitLog 存储路径
        # storePathCommitLog=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/commitlog
        # 消费队列存储
        # storePathConsumeQueue=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/consumequeue
        # 消息索引存储路径
        # storePathIndex=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/index
        # checkpoint 文件存储路径
        # storeCheckpoint=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/checkpoint
        # abort 文件存储路径
        # abortFile=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/abort
        # 限制的消息大小
        maxMessageSize=65536
        # flushCommitLogLeastPages=4
        # flushConsumeQueueLeastPages=2
        # flushCommitLogThoroughInterval=10000
        # flushConsumeQueueThoroughInterval=60000
        # Broker 的角色
        # - ASYNC_MASTER 异步复制Master
        # - SYNC_MASTER 同步双写Master
        # - SLAVE
        brokerRole=ASYNC_MASTER
        # 刷盘方式
        # - ASYNC_FLUSH 异步刷盘
        # - SYNC_FLUSH 同步刷盘
        flushDiskType=ASYNC_FLUSH
        # 发消息线程池数量
        # sendMessageThreadPoolNums=128
        # 拉消息线程池数量
        # pullMessageThreadPoolNums=128

 （6）构建broker容器并启动
        
    docker run -d --restart=always --name rmqbroker01  --link rmqnamesrv:namesrv -p 10911:10911 -p 10909:10909  -v /data/broker/logs:/root/logs -v /data/broker/store:/root/store  -v  /data/broker/conf/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf  -e "NAMESRV_ADDR=namesrv:9876"  -e "MAX_POSSIBLE_HEAP=200000000"  rocketmqinc/rocketmq sh mqbroker -c /opt/rocketmq-4.4.0/conf/broker.conf 
 
  通过docker ps可以看到nameserver和broker都已经启动成功，此时rocketmq已经安装完成。如果您需要一个可视化界面查看信息，可以再安装一个rocketmq-console。


