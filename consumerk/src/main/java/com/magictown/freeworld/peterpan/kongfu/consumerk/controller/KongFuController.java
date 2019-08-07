/**
 * @create 2019-08-02 16:59
 * @desc kong fu skill
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.controller;

import com.magictown.freeworld.peterpan.kongfu.consumerk.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class KongFuController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private HystrixService hystrixService;


    @GetMapping(value = "/load")
    public String loadKongFu() {
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

    @GetMapping(value = "/downgrade")
    public List<String> downgrade() {
        List<String> response = hystrixService.downgrade();

        return response;
    }

    @GetMapping(value = "/cacheRequest")
    public List<String> cacheRequest() {
        List<String> response = hystrixService.getTeacher();

        return response;
    }

    @GetMapping(value = "/requestMerge")
    public String getMergeRequestTeacher() {
        Random random = new Random(1000);
        Future<String> teacher= hystrixService.mergeRequestMethd(random.nextInt());

        try {
            Thread.sleep(100);
            return teacher.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return " no data ";
    }

    @GetMapping(value = "/breakerMethod")
    public String getBreakerMethod() {

        return hystrixService.breakerMethod();
    }

}

