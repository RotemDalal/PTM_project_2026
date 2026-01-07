# Computational Graph Infrastructure

A robust Java-based implementation of a **Publisher/Subscriber** infrastructure, designed to handle complex computational graphs with concurrent processing and web-based interaction.

## 🛠 Design Patterns Implemented
This project demonstrates advanced software architecture principles:
* **Observer Pattern:** The core interaction mechanism between `Topic` and `Agent`.
* **Singleton & Flyweight:** Efficiently managing `Topic` instances via `TopicManagerSingleton` to ensure resource optimization.
* **Decorator & Active Object:** Implemented in `ParallelAgent` to decouple message reception from processing using a `BlockingQueue` and dedicated threads.
* **Strategy Pattern:** Utilized in `BinOpAgent` via Java's `BinaryOperator` lambda expressions for dynamic computation logic.
* **Command/Servlet Pattern:** Used in the `HTTPServer` and `Servlet` infrastructure to handle web requests.

## 📊 Graph Features & Algorithms
* **Graph Construction:** Automatically builds a directed graph structure from existing topics and agents.
* **Cycle Detection:** Implemented a recursive **Depth-First Search (DFS)** algorithm in the `Node` class to identify cycles and ensure system stability.
* **Optimization:** Uses `HashSet` and `HashMap` to maintain $O(V+E)$ time complexity for graph building and cycle detection.

## 🚀 Key Features
* **Thread-Safe:** Uses `ConcurrentHashMap` and `ArrayBlockingQueue` for safe concurrent operations in a multi-threaded environment.
* **Asynchronous Processing:** `ParallelAgent` ensures that slow subscribers do not block the system's execution flow.
* **Web Server Integration:** A custom-built `HTTPServer` and `Servlet` system for remote monitoring and interaction.
* **Dynamic Configuration:** The `GenericConfig` class utilizes **Java Reflection** to instantiate agents dynamically based on configuration files.

## 📋 How to Use
1. **Clone the repository.**
2. **Run Tests:** Compile and run `MainTrain.java` to execute the automated test suite and verify the infrastructure.
3. **Configuration:** Use `Config.java` or `GenericConfig.java` to set up your specific computational graph.
4. **Integration:** Use `TopicManagerSingleton.get()` to manage topics and `Graph.createFromTopics()` to analyze the system's structure.