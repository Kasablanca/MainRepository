package cn.lm.aspect;

import java.util.Collection;

public interface Sample<T> {
	void sampleGenericMethod(T param);
    void sampleGenericCollectionMethod(Collection<T> param);
}
