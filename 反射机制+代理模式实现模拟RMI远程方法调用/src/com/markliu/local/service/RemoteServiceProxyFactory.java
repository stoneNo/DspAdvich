package com.markliu.local.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态创建RemoteService代理类的工厂
 * @author markliu
 *
 */
public class RemoteServiceProxyFactory {

	public static Object getRemoteServiceProxy(InvocationHandler h) {
		Class<?> classType = ((ServiceInvocationHandler) h).getClassType();
		Object proxy = Proxy.newProxyInstance(classType.getClassLoader(), 
				new Class[]{classType}, h);
		return proxy;
	}
}
