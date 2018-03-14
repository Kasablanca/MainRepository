package com.min.concurrent.chapter16;

import java.util.HashMap;
import java.util.Map;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class SafeStates {
	private final Map<String, String> states;

	public SafeStates() {
		states = new HashMap<String, String>();
		states.put("alaska", "AK");
		states.put("alabama", "AL");
		/*...*/
		states.put("wyoming", "WY");
	}

	public String getAbbreviation(String s) {
		return states.get(s);
	}
}
