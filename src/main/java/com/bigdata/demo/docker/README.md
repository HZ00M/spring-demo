#服务编排
##运行指令
docker-compose up -d

#独立部署
##构建镜像
docker build -t traffic:1.0 .
##运行指令
docker run -d -p 8001:8001 -e "SPRING_PROFILES_ACTIVE=prod" -v /data/traffic/logs/:/data/traffic/app/logs  traffic
