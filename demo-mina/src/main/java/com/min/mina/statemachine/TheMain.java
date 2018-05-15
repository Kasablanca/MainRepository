package com.min.mina.statemachine;

public class TheMain {

	public static void main(String[] args) {
		TapeDeckHandler handler = new TapeDeckHandler();
	    StateMachine sm = StateMachineFactory.getInstance(com.min.mina.statemachine.Transition.class).create(TapeDeckHandler.EMPTY, handler);
	    TapeDeck deck = new StateMachineProxyBuilder().create(TapeDeck.class, sm);

	    deck.load("The Knife - Silent Shout");
	    deck.play();
	    deck.pause();
	    deck.play();
	    deck.stop();
	    deck.eject();
	}
	
	static interface Transition {
		StateMachine create(String state, TapeDeckHandler handler);
	}

	static class MethodTransition implements Transition {
		@Override
		public StateMachine create(String state, TapeDeckHandler handler) {
			return null;
		}
	}
	
	static class StateMachineFactory {
		public static MethodTransition getInstance(Class<?> clazz) {
			return null;
		}
	}
	
	static class StateMachine {
		
	}

	static class StateMachineProxyBuilder {
		public TapeDeck create(Class<?> clazz, StateMachine sm) {
			return null;
		}
	}
	
}