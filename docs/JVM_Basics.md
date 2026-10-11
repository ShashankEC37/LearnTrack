# JVM Basics

## JDK vs JRE vs JVM

JVM (Java Virtual Machine) is what actually runs the java program. It loads the .class files and executes the bytecode on the machine. It also handles memory and garbage collection.

JRE (Java Runtime Environment) is JVM + the standard libraries (java.lang, java.util etc). This is what you need to run a java program.

JDK (Java Development Kit) is JRE + tools for development like javac (compiler), jar, debugger. This is what you need to write and compile programs.

So JDK contains JRE and JRE contains JVM.

## Bytecode

When we compile with javac, it doesn't create machine code for windows or mac. It creates a .class file which has bytecode. Bytecode is an intermediate code that only the JVM understands. The JVM then interprets it or converts it to machine code while running (JIT compiler).

## Write once, run anywhere

Since the compiled output is bytecode and not machine specific code, the same .class file can run on any OS that has a JVM installed. We don't have to compile again for each OS. The JVM is different for windows, linux and mac but the bytecode stays the same. That's why java is called "write once, run anywhere".
