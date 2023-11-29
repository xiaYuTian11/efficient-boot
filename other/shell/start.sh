#!/bin/bash

SERVICE_NAME="demo"
MAIN_CLASS="top.tanmw.demo.Application"
APP_BASE_PATH=$(cd `dirname $0`; pwd)
JAVA_OPTS="-Xms512m -Xmx1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${APP_BASE_PATH} "
SH_NAME="start.sh"
JAVA_HOME="/usr/local/software/jdk1.8.0_212/bin/java"
CP=${APP_BASE_PATH}/*:${APP_BASE_PATH}/lib/*:${APP_BASE_PATH}/config/
PID_FILE=${APP_BASE_PATH}/${SERVICE_NAME}.pid
SERVICE_FILE=/usr/lib/systemd/system/${SERVICE_NAME}.service

COMMAND="$1"
if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]] && [[ "$COMMAND" != "install" ]]; then
	echo "Usage: $0 start | stop | restart | install "
	exit 0
fi

function start()
{
    cd ${APP_BASE_PATH}
    nohup ${JAVA_HOME}  ${JAVA_OPTS}  -cp ${CP} ${MAIN_CLASS} -Duser.timezone=Asia/Shanghai -Dfile.encoding=utf8 >/dev/null 2>&1 &
    echo $! > "$PID_FILE"
    echo "${SERVICE_NAME} start success. pid=$(cat ${PID_FILE}) ."
}

function stop()
{
    PID=$(cat ${PID_FILE})
    kill -15 ${PID}
    SLEEP=30
    SLEEP_COUNT=1
    while [[ ${SLEEP} -ge 0 ]]; do
        kill -0 ${PID} >/dev/null 2>&1
        if [[ $? -gt 0 ]]; then
          rm -f ${PID_FILE} >/dev/null 2>&1
          if [[ $? != 0 ]]; then
            if [[ -w ${PID_FILE} ]]; then
              cat /dev/null > ${PID_FILE}
            fi
          fi
          break
        fi
        if [[ ${SLEEP} -gt 0 ]]; then
          sleep 1
        fi
        if [[ ${SLEEP} -eq 0 ]]; then
          kill -3 `cat ${PID_FILE}`
          kill -9 `cat ${PID_FILE}`
        fi
        SLEEP=`expr ${SLEEP} - 1`
        SLEEP_COUNT=`expr ${SLEEP_COUNT} + 1`
    done
     echo "${SERVICE_NAME} stop success. pid=${PID} ."
}

function install() {
    GB_SERVICE=`systemctl status ${SERVICE_NAME} | grep Active | awk '{print $3}' | cut -d "(" -f2 | cut -d ")" -f1`
    if [[ "$GB_SERVICE" == "running" ]];then
        echo "服务正在运行中,即将重新安装服务!"
        echo "停止服务"
        systemctl stop ${SERVICE_NAME}
        install_service
        echo "启动服务"
        systemctl start ${SERVICE_NAME}
    else
        install_service
        echo "安装完成!启动服务中!"
        systemctl start ${SERVICE_NAME}
    fi
}

function install_service() {
    sudo rm -rf ${SERVICE_FILE}
    SERVICE="[Unit]\nDescription=$SERVICE_NAME\nAfter=network.target\n\n[Service]\nType=forking\nExecStart=/bin/sh $APP_BASE_PATH/$SH_NAME start\nExecReload=/bin/sh $APP_BASE_PATH/$SH_NAME restart\nExecStop=/bin/sh $APP_BASE_PATH/$SH_NAME stop\nRestart=on-failure\n\n[Install]\nWantedBy=multi-user.target"
    echo "安装服务中..."
    sudo echo -e ${SERVICE} > ${SERVICE_FILE}
    echo "修改服务权限644中..."
    sudo chmod 644 ${SERVICE_FILE}
    echo "重载服务中..."
    systemctl daemon-reload
    echo "设置开机自启动..."
    systemctl enable ${SERVICE_NAME}
}


if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
    stop
elif [[ "$COMMAND" == "restart" ]]; then
    stop
    start
elif [[ "$COMMAND" == "install" ]]; then
    install
else
    echo "Usage: $0 start | stop | restart | install"
	exit 0
fi