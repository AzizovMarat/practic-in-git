package sample;

public enum Numbers {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    ZERO("0");

    private final String val;

    Numbers(String val) {
        this.val = val;
    }

    public String getValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return this.val;
    }
}
