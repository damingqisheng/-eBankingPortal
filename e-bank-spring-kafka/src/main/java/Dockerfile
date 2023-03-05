# 使用一个 OpenJDK 8 镜像作为基础镜像
FROM openjdk:12-jdk-alpine

# 在容器内创建一个 /app 目录
RUN mkdir /ebankingbackend

# 将容器的工作目录设置为 /ebankingbackend
WORKDIR /ebankingbackend

# 将本地的 JAR 文件复制到容器的 /app 目录下
COPY . .

# 对外暴露的端口号
EXPOSE 8080

# 启动容器时运行的命令
CMD ["java", "-jar", "ebankingbackend.jar"]