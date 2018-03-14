package com.min.mina.statemachine;

public interface TapeDeck {
	void load(String nameOfTape);
    void eject();
    void play();
    void pause();
    void stop();
}
