package org.springcloud.controller;

import java.util.List;

import org.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeptConsumerController {

	// 在这边没有service接口，
	// 理解：消费者 应不应该有service 结果是不需要执行访问就好
	// 因为需要restful 风格 肯定有RestTempLate spring会提供模板，供我们调用使用

	/**
	 * 注入RestTemplate bean 参数 url，request请求：一般用map，class<T>,
	 */
	@Autowired
	private RestTemplate restTemplate;// 提供多种便捷访问远程http服务的方法，简单的restful服务模板

	// ribbon 现在这个地址，应该是一个变量，不应该是固定地址，因为已经通过ribbon实现负载均衡
	//	private static final String REST_URL = "http://localhost:8081";
	// 应该访问eureka中的Application
	private static final String REST_URL = "http://SPRINGCLOUD-PROVIDER-DEPT";

//	@Override
//	@Nullable
//	public URI postForLocation(String url, @Nullable Object request, Map<String, ?> uriVariables)
//			throws RestClientException {
//		RequestCallback requestCallback = httpEntityCallback(request);
//		HttpHeaders headers = execute(url, HttpMethod.POST, requestCallback, headersExtractor(), uriVariables);
//		return (headers != null ? headers.getLocation() : null);
//	}

	// 将请求和处理请求的控制器方法关联起来，建立映射关系
	// 标识类：设置映射请求的请求路径的初试信息
	// 表示方法：设置映射请求的请求路径的具体信息
	@RequestMapping("/consumer/dept/get/{id}")
	public Dept get(@PathVariable("id") Long id) {
//		服务消费者需要调用返回但是没有service 所以需要一个访问地址
//		如：http://localhost:8081/dept/queryById/1
		return restTemplate.getForObject(REST_URL + "/dept/queryById/" + id, Dept.class);
	}

	@RequestMapping("/consumer/dept/add")
	public Boolean add(Dept dept) {
		System.err.println("消费者："+dept);
		return restTemplate.postForObject(REST_URL + "/dept/add/", dept, Boolean.class);
	}

	@RequestMapping("/consumer/dept/list")
	public List<Dept> list() {
		return restTemplate.getForObject(REST_URL + "/dept/queryList", List.class);
	}

}
