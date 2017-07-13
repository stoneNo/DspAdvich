package com.markliu.remote.main;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import com.markliu.local.bean.Call;
import com.markliu.remote.service.Service;
import com.markliu.remote.serviceimpl.RemoteService;

public class RemoteServer {

	private Service remoteService;
	public RemoteServer() {
		remoteService  = new RemoteService();
	}
	public static void main(String[] args) throws Exception {
		RemoteServer server = new RemoteServer();
		System.out.println("远程服务器启动......DONE!");
		server.service();
	}

	public void service() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(8001);
		while (true) {
				Socket socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				ObjectInputStream objIn = new ObjectInputStream(in);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(out);
				Call call = (Call) objIn.readObject();
				System.out.println("客户端发送的请求对象：" + call);
				call = getCallResult(call);
				objOut.writeObject(call);
				objIn.close();
				in.close();
				objOut.close();
				out.close();
				socket.close();
		}
	}

	/**
	 * 通过反射机制调用call中指定的类的方法，并将返回结果设置到原call对象中
	 * @param call
	 * @return
	 * @throws Exception
	 */
	private Call getCallResult(Call call) throws Exception {
		String className = call.getClassName();
		String methodName = call.getMethodName();
		Object[] params = call.getParams();
		Class<?>[] paramsTypes = call.getParamTypes();
		
		Class<?> classType = Class.forName(className);
		// 获取所要调用的方法
		Method method = classType.getMethod(methodName, paramsTypes);
		Object result = method.invoke(remoteService, params);
		call.setResult(result);
		return call;
	}
}
