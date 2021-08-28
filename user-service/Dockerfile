# build app with maven
#FROM maven:3-jdk-11
#ADD . /userservice
#WORKDIR /userservice
#RUN mvn clean install
#
## use build artifact
#ARG JAR_FILE=target/*.jar
#
## add Spring Boot app.jar to Container
#COPY --from=0 "/userservice/target/user-service-*-SNAPSHOT.jar" app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#
## Fire up our Spring Boot app by default
#CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

#FROM adoptopenjdk:11-jre-hotspot as builder
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
#
#FROM adoptopenjdk:11-jre-hotspot
#COPY --from=builder dependencies/ ./
#COPY --from=builder snapshot-dependencies/ ./
#COPY --from=builder spring-boot-loader/ ./
#COPY --from=builder application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


# docker build -t nameOfImage .
#FROM openjdk:17-jdk
#ARG JAR_FILE=user-service-*-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM maven:3-jdk-11
ADD . /userservice
WORKDIR /userservice
RUN ls -l

# Run Maven build
RUN mvn clean install

FROM openjdk:17-jdk


VOLUME /tmp

COPY --from=0 "/userservice/target/user-service-*-SNAPSHOT.jar" app.jar

# Add Spring Boot app.jar to Container
CMD [ "sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
