FROM amazoncorretto:21-alpine AS builder
ADD . /build
WORKDIR /build
RUN ./gradlew build -x test

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /build/build/libs/backend.jar /app
COPY --from=builder /build/resources /app/resources
EXPOSE 8080/tcp
VOLUME /app/resources
ENTRYPOINT ["java", "-jar", "backend.jar"]
