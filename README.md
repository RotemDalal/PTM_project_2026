# Computational Graph Infrastructure

A Java-based implementation of a **Publisher/Subscriber** infrastructure for complex computational graphs.

## 🛠 Design Patterns Implemented
This project demonstrates key architectural patterns:
* **Observer Pattern**: Core interaction between `Topic` and `Agent`.
* **Singleton Pattern**: Managed via `TopicManagerSingleton`.
* **Flyweight Pattern**: Efficient handling of `Topic` instances in the Manager.
* **Decorator & Active Object Patterns**: Implemented in `ParallelAgent` to handle concurrent message processing without blocking.

## 🚀 Key Features
* **Thread-Safe**: Uses `ConcurrentHashMap` and `ArrayBlockingQueue`.
* **Asynchronous Execution**: `ParallelAgent` decouples message reception from processing logic.

## 📋 Submission Info
* For automated testing, all classes are maintained in the `test` package.
