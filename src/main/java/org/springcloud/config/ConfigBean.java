package org.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// 相当于spring 中的 applicationContext.xml
//指示一个类声明一个或多个@Bean方法，并且可以由Spring容器处理，以便在运行时为这些bean生成BeanDefinition和服务请求
@Configuration
public class ConfigBean {

//	原来是<bean></bean>

//	现在使用spring注解

	/**
	 * RestTemplate中没有@Bean，容器中没有该类，需要new一个新类，
	 * 所以需要手动配置一个bean，这样容器中就有改类，就可以使用@Autowired进行注入 也就是说通过@Bean 将该类交给spring进行管理及ioc
	 */
	// 配置负载均衡实现，现在是通过RestTemplate进行调用所以将它实现负载均衡就可以
	@Bean
	// @LoadBalanced本身本没有做做什么，只是开启了负载均衡
	@LoadBalanced // ribbon基于客服端实现负载均衡
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	

}
