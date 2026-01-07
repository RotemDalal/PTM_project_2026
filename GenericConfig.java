package test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GenericConfig implements Config {
    private String confFile;
    private final List<Agent> agents = new ArrayList<>();
    public void setConfFile(String file) { this.confFile = file; }
    @Override public void create() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(confFile));
            for (int i = 0; i < lines.size(); i += 3) {
                Agent a = (Agent) Class.forName(lines.get(i))
                        .getConstructor(String[].class, String[].class)
                        .newInstance((Object) lines.get(i+1).split(","), (Object) lines.get(i+2).split(","));
                agents.add(new ParallelAgent(a));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    @Override public String getName() { return "GenericConfig"; }
    @Override public int getVersion() { return 1; }
    @Override public void close() { for (Agent a : agents) a.close(); }
}