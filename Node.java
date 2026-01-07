package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private String name;
    private List<Node> edges = new ArrayList<>();
    private Message msg;

    public Node(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Node> getEdges() { return edges; }
    public void setEdges(List<Node> edges) { this.edges = edges; }
    public Message getMessage() { return msg; }
    public void setMessage(Message msg) { this.msg = msg; }

    public void addEdge(Node node) {
        if (!edges.contains(node)) edges.add(node);
    }

    public boolean hasCycles() {
        return hasCyclesRecursive(this, new HashSet<Node>());
    }

    private boolean hasCyclesRecursive(Node startNode, Set<Node> visited) {
        for (Node neighbor : this.edges) {
            if (neighbor == startNode) return true;
            if (visited.add(neighbor)) {
                if (neighbor.hasCyclesRecursive(startNode, visited)) return true;
            }
        }
        return false;
    }
}