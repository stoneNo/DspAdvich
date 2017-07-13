package com.markliu.local.main;

import java.lang.reflect.InvocationHandler;
import com.markliu.local.service.RemoteServiceProxyFactory;
import com.markliu.local.service.ServiceInvocationHandler;
import com.markliu.remote.service.Service;

public class LocalClient {

	public static void main(String[] args) {

		String host = "127.0.0.1";
		Integer port = 8001;
		Class<?> classType = com.markliu.remote.service.Service.class;
		InvocationHandler h = new ServiceInvocationHandler(classType, host, port);
		Service serviceProxy = (Service) RemoteServiceProxyFactory.getRemoteServiceProxy(h);
		String result = serviceProxy.getService("SunnyMarkLiu", 22);
		System.out.println("调用远程方法getService的结果：" + result);
	}
}
