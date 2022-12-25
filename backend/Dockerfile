FROM alpine:latest

ADD .ssh/ /root/.ssh/
ADD init_container.sh /
RUN apk update  \
    && apk add --no-cache bash curl tar git openssh unzip \
    && chmod 600 /root/.ssh/id_rsa

RUN apk add --no-cache openjdk11-jre

WORKDIR /gradle
RUN curl -L https://services.gradle.org/distributions/gradle-6.5.1-bin.zip -o gradle-6.5.1-bin.zip
RUN unzip gradle-6.5.1-bin.zip
ENV GRADLE_HOME=/gradle/gradle-6.5.1
ENV PATH=$PATH:$GRADLE_HOME/bin
RUN gradle --version

WORKDIR /

ENV MAVEN_VERSION 3.8.6
ENV BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# give permissions to execute
RUN chmod 777 init_container.sh
RUN ./init_container.sh
COPY build/libs/computernetworksproject-0.1-all.jar ./computernetworksproject-0.1-all.jar
COPY build/tmp/kapt3/classes/main/META-INF/swagger/ ./build/tmp/kapt3/classes/main/META-INF/swagger/
EXPOSE 8080
ENTRYPOINT java -jar ./computernetworksproject-0.1-all.jar