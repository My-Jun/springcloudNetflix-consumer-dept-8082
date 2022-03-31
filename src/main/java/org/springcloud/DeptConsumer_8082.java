package org.springcloud;

import org.springcloud.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
// 在微服务启动时候就对SPRINGCLOUD-PROVIDER-DEPT进行Ribbon负载均衡改造，改造配置类RibbonConfig.class
@RibbonClient(name = "SPRINGCLOUD-PROVIDER-DEPT", configuration = RibbonConfig.class)
public class DeptConsumer_8082 {

	public static void main(String[] args) {
		SpringApplication.run(DeptConsumer_8082.class, args);
	}

}
