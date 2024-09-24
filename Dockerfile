FROM amazoncorretto:21-alpine AS builder
ADD ./ /.
CMD ./gradlew build -x test

FROM amazoncorretto:21-alpine
COPY --from=builder /build/libs/*.jar /
COPY --from=builder /resources /resources
EXPOSE 8080/tcp
VOLUME /resources
ENTRYPOINT java -jar /backend.jar
