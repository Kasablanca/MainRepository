package com.min.concurrent.chapter4;

import java.util.HashSet;
import java.util.Set;

import com.min.annotation.GuardedBy;
import com.min.annotation.ThreadSafe;

@ThreadSafe
public class PersonSet {
	@GuardedBy("this") private final Set<Person> mySet = new HashSet<Person>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {
    }
}
