package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    private static final int PLIP_RED = 99;
    private static final int PLIP_BULE = 76;
    private static final int MIN_PLIP_GREEN = 63;

    private static final double MOVE_LOSE_ENERGY = 0.15;
    private static final double STAY_GAIN_ENERGY = 0.2;
    private static final double REPLICATE_ENERGY = 1.0;
    private static final double REPLICATE_ENERGY_FACTOR = 0.5;
    private static final double MAX_ENERGY = 2.0;
    private static final int GREEN_FACTOR = (int) ((255 - MIN_PLIP_GREEN) / MAX_ENERGY);

    private static final double ESCAPE_PROBABILITY = 0.5;


    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        r = PLIP_RED;
        b = PLIP_BULE;
        g = getColorByEnergy(energy);
        return color(r, g, b);
    }

    private int getColorByEnergy(double energy) {
        return MIN_PLIP_GREEN + (int) (GREEN_FACTOR * energy);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= MOVE_LOSE_ENERGY;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy += STAY_GAIN_ENERGY;
        if (checkEnergyFull()) {
            setFullEnergy();
        }
    }

    private boolean checkEnergyFull() {
        return energy > MAX_ENERGY;
    }

    private void setFullEnergy() {
        energy = MAX_ENERGY;
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        energy = energy * REPLICATE_ENERGY_FACTOR;
        return new Plip(energy);
    }

    private boolean shouldReplicate() {
        return energy > REPLICATE_ENERGY;
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (shouldReplicate()) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        }

        List<Direction> creatures = getNeighborsOfType(neighbors, "clorus");
        if (creatures.size() != 0) {
            if (HugLifeUtils.random() < ESCAPE_PROBABILITY) {
                return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(empties));
            }
        }

        return new Action(Action.ActionType.STAY);
    }

}
