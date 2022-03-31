package org.springcloud.config;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class MyRandomRule extends AbstractLoadBalancerRule {

	// 自定义负载均衡策略需求
	// 每个服务，访问3次，换下一个访问（服务器为2）

	// 需要一个值来记录访问次数，total，默认为0，如果当total=3，就下一个服务
	// index = 0 ,默认为0，如果total=3，则index+1
	/**
	 * 被调用的次数
	 */
	private int total = 0;
	/**
	 * 当前服务
	 */
	private int divIndex = 0;

	/**
	 * Randomly choose from all living servers
	 */
	// @edu.umd.cs.findbugs.annotations.SuppressWarnings(value =
	// "RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE")
	public Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
			return null;
		}
		Server server = null;

		while (server == null) {
			if (Thread.interrupted()) {
				return null;
			}
			// 获得可获得的服务器 翻译直译
			// get获取，Reachable 可获得的，Servers 服务
			List<Server> upList = lb.getReachableServers();
			// 获取所有的服务
			List<Server> allList = lb.getAllServers();

			int serverCount = allList.size();
			if (serverCount == 0) {
				return null;
			}

			// 注释掉随机算法，编辑自定义算法
			// 从所有的服务中数量中， 生成区间随机数
			// int index = chooseRandomInt(serverCount);
			// 从可获得活着的服务中，以随机数获取改服务，进行服务判断
			// server = upList.get(index);

			// ---------------------------------------------
			// div 算法，每个服务访问3次，换下一个

			if (total < 3) {
				// 如果次数小于3，则返回当前,并次数+1
				server = upList.get(divIndex);
				total++;
			}else {
				// 如果次数大于3则，重置次数为0；选择下一个服务divIndex+1
				total=0;
				divIndex++;
				// 如果当前服务大于可选择服务数量，则从0从新开始
				if(divIndex>upList.size()-1) {
					divIndex=0;
				}
				// 从活着的服务中获取调用服务
				server = upList.get(divIndex);
			}

			// ------------------------------------------

			if (server == null) {
				Thread.yield();
				continue;
			}

			if (server.isAlive()) {
				return (server);
			}
			server = null;
			Thread.yield();
		}

		return server;

	}

	protected int chooseRandomInt(int serverCount) {
		return ThreadLocalRandom.current().nextInt(serverCount);
	}

	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	public void initWithNiwsConfig(IClientConfig clientConfig) {
		// TODO Auto-generated method stub

	}

}
