# 添加 Java 8 镜像来源
FROM java:8
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 添加参数
ARG JAR_FILE
# 添加 Spring Boot 包
ADD target/${JAR_FILE} app.jar
# 执行启动命令
ENTRYPOINT ["sh","-c" ,"java  -Xms256m -Xmx512m -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar  /app.jar"]