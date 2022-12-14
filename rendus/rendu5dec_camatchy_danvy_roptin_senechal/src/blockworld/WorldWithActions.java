package blockworld;

import java.util.*;

import planning.Action;
import planning.BasicAction;

import representation.Variable;

/**
 * Represents a world with constraint for the blockworld problem and build the actions possible in this world.
 * Extends {@link blockworld.WorldWithConstraint}.
 */
public class WorldWithActions extends WorldWithConstraint {

    /**
     * The actions possible in this world.
     */
    protected Set<Action> actions;

    /**
     * The actions possible in this world, grouped by the precondition fixed they require.
     * This is used to get the actions applicable in a state in a more efficient way.
     */
    protected Map<String, Set<Action>> actionsByPreconditionBlock;

    /**
     * Creates a new WorldWithActions.
     * @param nbBlocks The number of blocks.
     * @param nbPiles The number of piles.
     */
    public WorldWithActions(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.actions = new HashSet<>();
        this.actionsByPreconditionBlock = new HashMap<>();

        for(int i = 0; i < nbBlocks; i++) {
            String name = this.getBlocksFixed().get(i).getName();
            this.actionsByPreconditionBlock.put(name, new HashSet<>());
            for(int j = -nbPiles; j < nbBlocks; ++j) {
                if(i == j) {
                    continue;
                }
                
                for(int k = -nbPiles; k < nbBlocks; ++k) {
                    if(k == j || k == i)  {
                        continue;
                    }

                    Map<Variable, Object> pre = new HashMap<>();
                    Map<Variable, Object> post = new HashMap<>();
                    
                    pre.put(this.getBlocksFixed().get(i), false);
                    pre.put(this.getBlocksOn().get(i), k);
                    if(j < 0)
                        pre.put(this.getPiles().get(j), true);
                    else
                        pre.put(this.getBlocksFixed().get(j), false);
                    

                    post.put(this.getBlocksOn().get(i), j);
                    if(k < 0)
                        post.put(this.getPiles().get(k), true);
                    else
                        post.put(this.getBlocksFixed().get(k), false);
                    if(j < 0)
                        post.put(this.getPiles().get(j), false);
                    else
                        post.put(this.getBlocksFixed().get(j), true);

                    Action action = new BasicAction(pre, post, 1);
                    this.actions.add(action);
                    this.actionsByPreconditionBlock.get(name).add(action);
                }
            }            
        }
    }

    /**
     * Returns all the actions possible in this world.
     * @return All the actions possible in this world.
     */
    public Set<Action> getActions() {
        return this.actions;
    }

    /**
     * Returns all the actions possible in this world, grouped by the precondition fixed they require.
     * @return All the actions possible in this world, grouped by the precondition fixed they require.
     */
    public Map<String, Set<Action>> getActionsByPreconditionBlock() {
        return this.actionsByPreconditionBlock;
    }
    
}
