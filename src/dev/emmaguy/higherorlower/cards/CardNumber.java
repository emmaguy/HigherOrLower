package dev.emmaguy.higherorlower.cards;

public enum CardNumber {
    ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

    private final int number;

    private CardNumber(int number) {
	this.number = number;
    }

    public int getNumber() {
	return number;
    }
}
