FROM amazoncorretto:21-alpine AS builder
ADD ./ /build
WORKDIR /build
CMD ./gradlew build -x test

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar /app
COPY --from=builder /build/resources /app/resources
EXPOSE 8080/tcp
VOLUME /app/resources
ENTRYPOINT java -jar /app/backend.jar
