package cn.lm.interceptor;

public interface Lockable {
	void lock();
    void unlock();
    boolean locked();
}
