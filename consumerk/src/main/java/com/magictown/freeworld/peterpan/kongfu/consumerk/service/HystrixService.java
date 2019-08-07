/**
 * @create 2019-08-07 15:38
 * @desc hystrix service
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    //@CacheEvict("cacheGetTeacher")
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

}

