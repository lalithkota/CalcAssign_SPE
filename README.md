# CalcAssign_server
This repo is part of the Assigment od Software Production Engineering Course. (To understand DevOps toolchain.)
It is a calculator app. It basically hosts a server, where clients can connect and ask it results for math expression.
It uses simple java sockets and threads.
(It uses JS Nashorn Engine to eval exprs, so it should be able to run any such exprs.)
(This repo is basically for the server side of it. But client java file is also included, for usage purposes, as Main_client.java)

(A docker image is also available on docker-hub at [calc_assign_server](https://hub.docker.com/repository/docker/lalithkota/calc_assign_server))

(A ref jenkins file is also added, something that can be used in jenkins pipeline.)

(Also see, [Releases](https://github.com/lalithkota/CalcAssign_SPE/releases))

## 1. Usage
- Build and running jars:
  - To build, use maven:
  `mvn compile`
  and to get the exec file (jar file in this case) use:
  `mvn install`
  and to test, use:
  `mvn test`
  - Or use this to clean, build, install, and test:
    - `mvn clean package`
  - To run server, use:
    - `java -jar target/<the-installed-jar-file.jar> <port-to-listen-to>`
- For docker:
  - To build a docker image, (Assuming daemon is running.) use:
    - `docker build -t <repo-tag> .`
  - To run the image, (The port argument is directly passed to the jar execution, see above.) use:
    - `docker run -p <ServerIP>:<ServerPort>:<SomePortNo> <repo-tag-that-is-used-to-build>`
    - Now the server can be reached from this `<ServerIP>` and `<ServerPort>`.
    The `<SomePortNo>` is; to what the `<ServerPort>` is published to; something that the user doesn't need to care.
    Any valid port no can be provided.
    See [publishing](https://docs.docker.com/engine/reference/commandline/run/#publish-or-expose-port--p---expose)
- (Though not a part of this); To run client, use:
  - `java -jar <the-clien-jarfile.jar> <ServerIP> <ServerPort>`
