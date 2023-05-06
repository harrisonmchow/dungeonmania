package dungeonmania.entities.logicals;

import dungeonmania.util.Position;

public class Wire extends LogicalEntity implements Conductor {

    public Wire(Position position) {
        super(position, "or");
    }
}
