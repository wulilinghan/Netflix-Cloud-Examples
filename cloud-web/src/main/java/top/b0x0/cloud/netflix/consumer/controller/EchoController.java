package top.b0x0.cloud.netflix.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.b0x0.cloud.netflix.common.util.ServiceList;
import top.b0x0.cloud.netflix.common.vo.R;
import top.b0x0.cloud.netflix.consumer.feignclient.Echo1Client;
import top.b0x0.cloud.netflix.consumer.feignclient.Echo2Client;

import java.net.URI;
import java.util.List;

/**
 * 回声测试
 *
 * @author ManJiis
 * @date 2021-07-16
 * @since jdk1.8
 */
@RestController
public class EchoController {
    private final Logger log = LoggerFactory.getLogger(EchoController.class);

    @Autowired
    private Echo1Client echo1Client;

    @Autowired
    private Echo2Client echo2Client;

    @GetMapping("/p1SayHello")
    public R p1SayHello(String name) {
        return echo1Client.sayHello(name);
    }

    @GetMapping("/p1bonjour")
    public R p1bonjour(String name) {
        return echo1Client.bonjour(name);
    }

    @GetMapping("/p2SayHello")
    public R p2SayHello(String name) {
        return echo2Client.sayHello(name);
    }

    @GetMapping("/p2bonjour")
    public R p2bonjour(String name) {
        return echo2Client.bonjour(name);
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String currentAppName;

    /**
     * 获取注册中心数据对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/v2/echo/app-name")
    public String echoAppName() {
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
//        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceList.PROVIDER_1);
//        log.info("consumerAppName : {}", consumerAppName);
//        log.info("ServiceInstance : {}", serviceInstance);
//        String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), consumerAppName);


        List<String> services = discoveryClient.getServices();
        log.info("discoveryClient.getServices: {}", services); // [b0x0-cloud-consumer, b0x0-cloud-provider1, b0x0-cloud-provider2]

        // 通过服务名获取到注册到nacos的ip+端口地址信息（集群会获取多个）
        List<ServiceInstance> instances = discoveryClient.getInstances(ServiceList.PROVIDER_1);
        ServiceInstance serviceInstance = instances.get(0);
        URI uri = serviceInstance.getUri();
        // http://192.168.8.106:8091
        log.info("uri = {}", uri);
        String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), currentAppName);

//        String tmpUrl = "http://" + ServiceList.PROVIDER_1 + "/echo/%s";
//        String url = String.format(tmpUrl, currentAppName);


        log.info("currentAppName : {}", currentAppName); // b0x0-cloud-consumer
        log.info("ServiceInstance : {}", serviceInstance); // com.alibaba.cloud.nacos.NacosServiceInstance@6e10c0ea
        log.info("request url: {}", url); // http://192.168.8.106:8091/echo/b0x0-cloud-consumer
        return restTemplate.getForObject(url, String.class);
    }

}
