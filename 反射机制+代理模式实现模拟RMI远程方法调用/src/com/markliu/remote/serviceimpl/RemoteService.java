package com.markliu.remote.serviceimpl;

import com.markliu.remote.service.Service;

/**
 * 服务器端目标业务类，被代理对象
 * @author markliu
 */
public class RemoteService implements Service {
	@Override
	public String getService(String name, int number) {
		return name + ":" + number;
	}
}
