# Setup Instructions

## JDK version

```
> java -version
openjdk version "25.0.4.1" 2026-08-18 LTS
OpenJDK Runtime Environment Temurin-25.0.4.1+1 (build 25.0.4.1+1-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.4.1+1 (build 25.0.4.1+1-LTS, mixed mode, sharing)

> javac -version
javac 25.0.4.1
```

Installed Temurin JDK from adoptium.net. While installing I selected the options to set JAVA_HOME and add it to PATH, then checked with `java -version` in a new terminal.

## Hello World

HelloWorld.java (in this docs folder):

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Ran it like this:

```
> javac HelloWorld.java
> java HelloWorld
Hello, World!
```

`javac` compiles the .java file into HelloWorld.class (bytecode). Then `java HelloWorld` starts the JVM which loads the class and runs the main method.
