/**
 * @create 2019-08-02 16:59
 * @desc kong fu skill
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.controller;

import com.magictown.freeworld.peterpan.kongfu.consumerk.message.Broadcaster;
import com.magictown.freeworld.peterpan.kongfu.consumerk.message.LogMessage;
import com.magictown.freeworld.peterpan.kongfu.consumerk.service.HystrixService;
import com.magictown.freeworld.peterpan.kongfu.consumerk.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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
    @Autowired
    private Broadcaster broadcaster;
    @Value("${app.zuul.serviceID}")
    private String zuulServiceID;
    @Value("${app.vector-cloud-service.serviceID}")
    private String remoteServiceID;
    @Autowired
    private StreamService streamService;


    @GetMapping(value = "/load")
    public String loadKongFu() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(zuulServiceID);
        StringBuilder requestContext = new StringBuilder(16);
        requestContext.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("/"+remoteServiceID+"/getKongFu");
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

    @GetMapping(value = "/threadQuarantine")
    public List<String> getThreadTeacher() {
        return hystrixService.getThreadTeacher();
    }

    @GetMapping(value = "/getAnimals")
    public List<String> getAnimals() {
        return hystrixService.getAnimals();
    }

    @GetMapping(value = "/sendMessage")
    public void send() {
        LogMessage logMessage = new LogMessage();
        logMessage.setCreateTime(new Date());
        logMessage.setId(1111L);
        logMessage.setLogLevel("Error");
        logMessage.setMsg("This is free message from Arbitrary china because empire modify the constitution");
        logMessage.setServiceType("9");
        logMessage.setUserId(8888L);
        broadcaster.send(logMessage);
    }

    @GetMapping(value = "/sendMany")
    public void sendMany() {
        String message = "This is arbitrary message send from arbitrary china ";
        broadcaster.sendWithManyString(message);
    }

    @GetMapping(value = "/sendFanout")
    public void sendFanout() {
        String message = "China President is Fucking Stupid Pig !!! China communist party is a group stupid pig ";
        broadcaster.sendWithFanout(message);
    }

    @PostMapping("/sendStreamMsg")
    public String sendStreamMsg(String msg) {

        streamService.sendMsg(msg);

        return "success";
    }

}

