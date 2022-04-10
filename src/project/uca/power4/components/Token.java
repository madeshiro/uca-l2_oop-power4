package project.uca.power4.components;

public enum Token {
    Red('R'),
    Yellow('Y'),
    Empty('E');

    private final char initial;

    public final char getInitial() {
        return initial;
    }

    Token(char c) {
        this.initial = c;
    }
}
