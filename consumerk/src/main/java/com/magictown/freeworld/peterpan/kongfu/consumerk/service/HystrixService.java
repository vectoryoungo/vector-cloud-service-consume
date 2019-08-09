/**
 * @create 2019-08-07 15:38
 * @desc hystrix service
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * add @CacheConfig instruct this class may be use cache
 */
@CacheConfig(cacheNames = {"vector.hystrix.cache"})
@Service
public class HystrixService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "serviceDowngradeFallback")
    public List<String> downgrade() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getSkill");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        List<String> result = responseEntity.getBody();

        return result;
    }

    //return sterotype data
    private List<String> serviceDowngradeFallback(){
        List<String> fallback = new ArrayList<>(16);
        fallback.add("skill one ");
        fallback.add("skill two ");
        fallback.add("skill three");

        return fallback;
    }

    @Cacheable("cacheGetTeacher")
    //@CacheEvict("cacheGetTeacher") non idempotent operation use this like insert delete update
    public List<String> getTeacher() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getTeacher");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        List<String> result = responseEntity.getBody();

        return result;
    }

    @HystrixCollapser(batchMethod = "mergeRequest",scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "10"),
                                   @HystrixProperty(name = "maxRequestsInBatch",value = "50")})
    public Future<String> mergeRequestMethd(Integer id){
        return null;
    }

    @HystrixCommand
    public List<String> mergeRequest(List<Integer> series) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getTeacher");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        List<String> result = responseEntity.getBody();

        return result;
    }

    @HystrixCommand(fallbackMethod = "breakerFallback",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,value = "10"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value = "50"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,value = "5000")
    })
    public String breakerMethod() {

        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getKongFu");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<String> typeReference = new ParameterizedTypeReference<String>() {
        };

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        String result = responseEntity.getBody();

        return result;
    }

    private String breakerFallback() {

        return "now in breakerFallback Method ....";
    }

    @HystrixCommand(groupKey = "vector-thread-quarantine",commandKey = "vectorThreadQuarantine",threadPoolKey = "vector-thread-quarantine",
                    threadPoolProperties = {
                            @HystrixProperty(name="coreSize" ,value = "5"),
                            @HystrixProperty(name="maxQueueSize" ,value = "10"),
                            @HystrixProperty(name="keepAliveTimeMinutes" ,value = "2"),
                            @HystrixProperty(name="queueSizeRejectionThreshold" ,value = "15")
                    },fallbackMethod = "threadQuarantineFallback")
    public List<String> getThreadTeacher(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getSkill");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        List<String> responseEntityBody = responseEntity.getBody();

        return responseEntityBody;
    }

    private List<String> threadQuarantineFallback() {
        List<String> response = new ArrayList<>(2);
        response.add("threadQuarantineFallback method....");
        return response;
    }


    @HystrixCommand(fallbackMethod = "semaphoreQuarantineFallback",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY,value = "SEMAPHORE"),
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,value = "100")
    })
    public List<String> getAnimals() {

        ServiceInstance serviceInstance = loadBalancerClient.choose("vector-cloud-service");
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("getAnimals");
        System.out.println(" now you are consume " + requestContext.toString());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(requestContext.toString(), HttpMethod.GET,null,typeReference);

        List<String> responseEntityBody = responseEntity.getBody();

        return responseEntityBody;
    }

    private List<String> semaphoreQuarantineFallback() {
        List<String> response = new ArrayList<>(8);
        response.add("NoData");

        return response;
    }
}

