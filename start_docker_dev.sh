#!/bin/sh
#用法:
#sh start_docker.sh -s test-server -t 0.0.1-SNAPSHOT -p 9090 -d 1
#注：
#-s：服务名称
#-t：版本号
#-p：端口号(端口号配合Dockerfile一起使用,可以动态指定微服务的端口)
#-d：启动方式（1：重新部署；2：添加节点)
#环境设置
active="dev"
show_usage="args: [-s , -t , -d]\
	                                  [--server-name=, --server-tag=, --deploy-type=]"
show_useport="[-p] [--server-port]"
#参数
docker_repostory="registry-vpc.cn-beijing.aliyuncs.com/airohit/"

#服务名称
server_name=""
#版本号
server_tag=""
#端口号
server_port=""
#服务器IP
ipaddr=`ip a show dev eth0|grep -w inet|awk '{print $2}'|awk -F '/' '{print $1}'`
#部署类型，1：替换节点；2：添加节点
deploy_type=""
GETOPT_ARGS=`getopt -o s:t:p:d: -al server-name:,server-tag:,server-port:,deploy-type: -- "$@"`
eval set -- "$GETOPT_ARGS"
#获取参数
while [ -n "$1" ]
do
	case "$1" in
		-s|--server-name) server_name=$2; shift 2;;
		-t|--server-tag) server_tag=$2; shift 2;;
		-p|--server-port) server_port=$2; shift 2;;
		-d|--deploy-type) deploy_type=$2; shift 2;;
		--) break ;;
		*) echo $1,$2,$show_usage; break ;;
	esac
done
if [[ -z $server_name || -z $server_tag || -z $deploy_type ]]; then
	echo $show_usage
	exit 0
fi

#拼装镜像名
image_name="$server_name-$active:$server_tag"
echo "images name:$image_name";

function rep()
{
  echo "image pull:start[$docker_repostory$image_name]"
    docker pull "$docker_repostory$image_name"
    if (( $? ))
    then
      echo "image pull:failed"
      exit 0
    else
      echo "image pull:success"
    fi
    echo "start handle container"
	#关闭容器、删除容器
	echo $(docker ps -q --filter name="^/$server_name")|while read line
	do
		container_id=$line
		for id in $container_id
		do
			if [ -z $server_port ]; then
				$server_port=echo $(docker inspect $id | grep "SERVER_PORT=") | tr -cd "[0-9]"
			fi			
			if [ ! -z $id ]; then
				echo "running containers:$id";
				echo "stop container[$id]:start"
				docker stop $id
				echo "stop container[$id]:success"
				echo "rm container[$id]:start"
				docker rm $id
				echo "rm container[$id]:success"
			fi
		done
	done
	docker run --name "$server_name-$server_port" -v "/home/agriculture-cloud/logs/$server_name/$server_port":"/root/logs" -e "SERVER_NAME=$server_name" -e "SERVER_PORT=$server_port" -v /home/skywalking-agent:/home/skywalking-agent --restart=always -d -e "SERVER_NAME=$server_name" -e "SERVER_PORT=$server_port" -e "IP_ADDR=$ipaddr"  --network=host "$docker_repostory$image_name"
}

function add()
{
	if [ -z $server_port ]; then
		echo $show_usage
		exit 0
	fi
	docker run --name "$server_name-$server_port" -v "/home/agriculture-cloud/logs/$server_name/$server_port":"/root/logs" -e "SERVER_NAME=$server_name" -e "SERVER_PORT=$server_port" -v /home/skywalking-agent:/home/skywalking-agent --restart=always -d -e "SERVER_NAME=$server_name" -e "SERVER_PORT=$server_port" -e "IP_ADDR=$ipaddr"   --network=host "$docker_repostory$image_name"
}
if [ $deploy_type = "1" ]; then
	rep
elif [ $deploy_type = "2" ]; then
	add
fi
