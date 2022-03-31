package org.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class RibbonConfig {

	// 负载均衡的核心实现，IRul路由网关，项目网关
	//
	@Bean
	public IRule myIRule() {
		// 使用随机
		 return new RandomRule();

		// 之前是调用IRule的随机，现在调用自己的负载均衡算法
//		return new MyRandomRule();

	}

}
