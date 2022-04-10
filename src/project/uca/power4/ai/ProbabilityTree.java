package project.uca.power4.ai;

public class ProbabilityTree {

    enum Priority {
        Negligible /* Next step is Negligible */,
        Casual     /* Next step is Non-Decisive */,
        High       /* Next step can be decisive */,
        Highest /* Next step will be decisive */
    }

    static class Branch {

    }


    public ProbabilityTree() {

    }

    public ProbabilityTree(Branch focusMovement) {

    }

    public double getHigherWinRate() {
        return .0;
    }

    public double getLowestWinRate() {
        return .0;
    }

    public int nextMove(double minRate) {
        return 0;
    }
}
