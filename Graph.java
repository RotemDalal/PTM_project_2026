package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph extends ArrayList<Node> {

    public boolean hasCycles() {
        for (Node node : this) {
            if (node.hasCycles()) return true;
        }
        return false;
    }

    public void createFromTopics() {
        this.clear();
        Map<String, Node> nodeMap = new HashMap<>();
        Set<Node> addedToGraph = new HashSet<>();

        Collection<Topic> topics = TopicManagerSingleton.get().getTopics();

        for (Topic t : topics) {
            Node tNode = nodeMap.computeIfAbsent("T" + t.name, name -> new Node(name));
            if (addedToGraph.add(tNode)) this.add(tNode);

            for (Agent a : t.getSubs()) {
                Node aNode = nodeMap.computeIfAbsent("A" + a.getName(), name -> new Node(name));
                if (addedToGraph.add(aNode)) this.add(aNode);
                tNode.addEdge(aNode);
            }

            for (Agent a : t.getPubs()) {
                Node aNode = nodeMap.computeIfAbsent("A" + a.getName(), name -> new Node(name));
                if (addedToGraph.add(aNode)) this.add(aNode);
                aNode.addEdge(tNode);
            }
        }
    }
}