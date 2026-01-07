# Computational Graph Infrastructure

A robust Java-based implementation of a **Publisher/Subscriber** infrastructure, designed to handle complex computational graphs with concurrent processing.

## 🛠 Design Patterns Implemented
This project demonstrates advanced software architecture principles:
* **Observer Pattern**: Core interaction between `Topic` and `Agent`.
* **Singleton & Flyweight**: Efficiently managing `Topic` instances to avoid redundancy via `TopicManagerSingleton`.
* **Decorator & Active Object**: Implemented in `ParallelAgent` to decouple message reception from processing using a `BlockingQueue`.
* **Strategy Pattern**: Utilized in `BinOpAgent` via Java's `BinaryOperator` lambda expressions to allow dynamic computation logic.

## 📊 Graph Features & Algorithms
* **Graph Construction**: Automatically builds a directed graph structure from existing topics and agents.
* **Cycle Detection**: Implemented a recursive Depth-First Search (DFS) algorithm in the `Node` class to identify cycles, ensuring system stability.
* **Optimization**: Uses `HashSet` and `HashMap` to maintain $O(V+E)$ time complexity for graph building and cycle detection.

## 🚀 Key Features
* **Thread-Safe**: Uses `ConcurrentHashMap` and `ArrayBlockingQueue` for safe concurrent operations.
* **Asynchronous Processing**: `ParallelAgent` ensures that slow subscribers do not block the entire system.
* **Extensible**: Designed to support complex math operations and custom configurations.

## 📋 How to Use
1. Clone the repository.
2. For automated testing, ensure all classes are in the `test` package.
3. Use `TopicManagerSingleton.get()` to manage topics and `Graph.createFromTopics()` to analyze the infrastructure.