package com.min.someapp.aspect.test;

import java.util.Collection;

public interface Sample<T> {
	void sampleGenericMethod(T param);
    void sampleGenericCollectionMethod(Collection<T> param);
}
