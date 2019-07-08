FROM centos
RUN yum install -y java
VOLUME /tmp
ADD /target/spring-0.0.1-SNAPSHOT.jar spring.jar
RUN sh -c 'touch /spring.jar'
ENTRYPOINT ["java","-jar","spring.jar"]
