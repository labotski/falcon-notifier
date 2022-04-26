

# First stage: complete build environment
FROM maven:3.8.5-jdk-11-slim AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package

# Second stage: minimal runtime environment
FROM adoptopenjdk/openjdk11


# copy jar from the first stage
COPY --from=builder target/falcon-notifier-0.0.1-SNAPSHOT.jar falcon-notifier-0.0.1-SNAPSHOT.jar


CMD ["java", "-jar", "falcon-notifier-0.0.1-SNAPSHOT.jar"]
