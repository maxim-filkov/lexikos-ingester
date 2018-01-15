FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/representator-1.0.0.war representator.war
EXPOSE 8086
RUN sh -c 'touch /representator.war'
ENTRYPOINT [ "java", "-jar", "/representator.war" ]
