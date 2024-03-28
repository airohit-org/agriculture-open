#父镜像
FROM youmoni/jdk11:11.0.9
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/* && mkfontscale && mkfontdir && fc-cache

#作者信息
MAINTAINER zyg <375379617@qq.com>

ENV SERVER_PORT 48081

#将jar包添加到容器中
ADD target/agriculture-module-system-app.jar app.jar

#复制时区文件 java:8
#RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#&& echo 'Asia/Shanghai' >/etc/timezone

#复制时区文件 frolvlad/alpine-oraclejdk8:slim
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#暴露端口
EXPOSE $SERVER_PORT
#定义jvm参数变量
ENV JAVA_OPTS="-Xms128m -Xmx256m -javaagent:/home/skywalking-agent/skywalking-agent.jar  -Dskywalking.collector.backend_service=172.25.229.29:11800"
#运行命令
ENTRYPOINT java -Dserver.port=${SERVER_PORT} ${JAVA_OPTS} -Dskywalking.agent.service_name=${SERVER_NAME}-${IP_ADDR}-${SERVER_PORT}  -Djava.security.egd=file:/dev/./urandom -jar /app.jar