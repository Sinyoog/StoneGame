package Application;

import java.util.Map;

public class EnhanceStage {
    private final Map<String, Double> nextStages;
    private final Integer cost;

    public EnhanceStage(Map<String, Double> nextStages, Integer cost) {
        this.nextStages = nextStages;
        this.cost = cost;
    }

    public Map<String, Double> getNextStages() {
        return nextStages;
    }

    public Integer getCost() {
        return cost;
    }
}
