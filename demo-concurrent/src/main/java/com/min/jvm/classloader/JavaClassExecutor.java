package com.min.jvm.classloader;

import java.lang.reflect.Method;

public class JavaClassExecutor {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String execute(byte[] classByte) {
		HackSystem.clearBuffer();
		ClassModifier cm = new ClassModifier(classByte);
		byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/min/jvm/classloader/HachSystem");
		HotSwapClassLoader loader = new HotSwapClassLoader();
		Class clazz = loader.loadByte(modiBytes);
		try {
			Method method = clazz.getMethod("method", new Class[] {String[].class});
			method.invoke(null, (Object)new String[] { null });
		} catch (Throwable e) {
			e.printStackTrace(HackSystem.out);
		}
		return HackSystem.getBufferString();
	}
	
}
