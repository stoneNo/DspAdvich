package com.markliu.remote.service;

/**
 * Service接口。代理类和被代理类抖需要实现该接口
 * @author markliu
 *
 */
public interface Service {
	public String getService(String name, int number);
}
