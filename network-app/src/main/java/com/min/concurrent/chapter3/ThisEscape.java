package com.min.concurrent.chapter3;

import java.awt.Event;

public class ThisEscape {
	private int id;
	private String name;
	
	public ThisEscape(EventSource source,int id, String name){
		this.id = id;
		source.registerListener(new EventListener(){
			@Override
			public void onEvent(Event e) {
				//do something...
				System.out.println(ThisEscape.this.id + "," + ThisEscape.this.name);
			}
		});
		this.name = name;
	}
	
	public static void main(String[] args) throws InterruptedException {
		class EventSourceImpl implements EventSource{
			private EventListener eventListener;

			@Override
			public void registerListener(EventListener listener) {
				eventListener = listener;
			}
			
			public EventListener getEventlistener(){ return eventListener; }
			
		}
		final EventSourceImpl eventSource = new EventSourceImpl();
		
		final Thread t2 = new Thread(){
			public void run() {
				new ThisEscape(eventSource,1,"name");
				System.out.println("t2 finish");
			}
		};
		Thread t1 = new Thread(){
			public void run() {
				try {
					t2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EventListener listener = eventSource.getEventlistener();
				System.out.println(listener);
				listener.onEvent(new Event(eventSource, 1, eventSource){
					private static final long serialVersionUID = 1L;
				});
				System.out.println("t1 finish");
			}
		};
		
		t2.start();
		t1.start();
	}
}

interface EventSource {
	void registerListener(EventListener listener);
}

interface EventListener {
	void onEvent(Event e);
}
