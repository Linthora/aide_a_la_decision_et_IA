package planning;

import java.util.*;

import representation.Variable;

public class DFSPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    protected int exploredNodes;

    public DFSPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.exploredNodes = 0;
    }

    public List<Action> plan() {
        Stack<Action> plan = new Stack<Action>();
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();
        closed.add(initialState);
        //        List<Action> res = searching(this.initialState, plan, closed);
        return searching(this.initialState, plan, closed);
    }

    private Stack<Action> searching(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {
        this.exploredNodes++;
        if(this.goal.isSatisfiedBy(instantiation))
            return plan;
        for(Action a : this.actions) {
            if(a.isApplicable(instantiation)) {
                Map<Variable, Object> next = a.successor(instantiation);
                if(!(closed.contains(next))) {
                    plan.push(a);
                    closed.add(next);
                    Stack<Action> subplan = searching(next, plan, closed);
                    if(subplan != null)
                        return subplan;
                    else
                        plan.pop();
                }
            }
        }

        return null;
    }


    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public int getExploredNode() {
        return this.exploredNodes;
    }

    public Goal getGoal() {
        return goal;
    }
}