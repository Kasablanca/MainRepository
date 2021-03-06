package com.min.mina.statemachine;

public class TapeDeckHandler {
	@State public static final String EMPTY   = "Empty";
	@State public static final String LOADED  = "Loaded";
	@State public static final String PLAYING = "Playing";
	@State public static final String PAUSED  = "Paused";

	@Transition(on = "load", in = EMPTY, next = LOADED)
	public void loadTape(String nameOfTape) {
		System.out.println("Tape '" + nameOfTape + "' loaded");
	}

	@Transitions({
		@Transition(on = "play", in = LOADED, next = PLAYING),
		@Transition(on = "play", in = PAUSED, next = PLAYING)
	})
	public void playTape() {
		System.out.println("Playing tape");
	}

	@Transition(on = "pause", in = PLAYING, next = PAUSED)
	public void pauseTape() {
		System.out.println("Tape paused");
	}

	@Transition(on = "stop", in = PLAYING, next = LOADED)
	public void stopTape() {
		System.out.println("Tape stopped");
	}

	@Transition(on = "eject", in = LOADED, next = EMPTY)
	public void ejectTape() {
		System.out.println("Tape ejected");
	}
}
