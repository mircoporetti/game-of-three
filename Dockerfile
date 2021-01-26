FROM openjdk:11.0.2-jre-slim

ARG jarFile
ARG playerName
ARG opponentName
ARG mode
ARG port

ENV JAR_FILE=${jarFile}
ENV PLAYER_NAME=${playerName}
ENV OPPONENT_NAME=${opponentName}
ENV MODE=${mode}
ENV PORT=${port}

RUN export DEBIAN_FRONTEND=noninteractive

RUN apt-get install -y tzdata

RUN ln -fs /usr/share/zoneinfo/Europe/Zurich /etc/localtime

RUN dpkg-reconfigure --frontend noninteractive tzdata

ADD ./rabbit-amqp/target/${jarFile} /app/
ADD ./config/dev/application.yml /app/config/application.yml


ENTRYPOINT [ "sh", "-c", "java -Xmx1g -jar /app/${JAR_FILE} --game-of-three.player-name=${PLAYER_NAME} --game-of-three.opponent-name=${OPPONENT_NAME} --game-of-three.mode=${MODE} --server.port=${PORT} --spring.config.location=/app/config/application.yml" ]