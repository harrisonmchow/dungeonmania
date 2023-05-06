package dungeonmania.entities.logicals;

public class LogicalFactory {
    public static LogicalStrategy constructLogicalStrategy(String logic, LogicalEntity e) {
        switch (logic) {
            case "and":
                return new LogicalAND(e);
            case "or":
                return new LogicalOR(e);
            case "xor":
                return new LogicalXOR(e);
            case "co_and":
                return new LogicalCOAND(e);
            default:
                return null;
        }
    }
}
