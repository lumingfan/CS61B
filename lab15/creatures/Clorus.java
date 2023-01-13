package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    private static final int RED = 34;
    private static final int GREEN = 0;
    private static final int BLUE = 231;

    private static final double MOVE_LOSE_ENERGY = 0.03;
    private static final double STAY_LOSE_ENERGY = 0.01;

    private static final double REPLICATE_ENERGY = 1.0;
    private static final double REPLICATE_LOSE_ENERGY_FACTOR = 0.5;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public void move() {
        energy -= MOVE_LOSE_ENERGY;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Creature replicate() {
        energy *= REPLICATE_LOSE_ENERGY_FACTOR;
        return new Clorus(energy);
    }

    private boolean shouldReplicte() {
        return energy >= REPLICATE_ENERGY;
    }

    @Override
    public void stay() {
        energy -= STAY_LOSE_ENERGY;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        List<Direction> creatures = getNeighborsOfType(neighbors, "plip");
        if (creatures.size() != 0) {
            Direction attackDir = HugLifeUtils.randomEntry(creatures);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }

        if (shouldReplicte()) {
            Direction repDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, repDir);
        }

        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }

    @Override
    public Color color() {
        return color(RED, GREEN, BLUE);
    }
}
