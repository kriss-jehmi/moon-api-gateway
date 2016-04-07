package com.longcoding.undefined.controllers;

import com.longcoding.undefined.services.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by longcoding on 16. 4. 5..
 */
@RestController
@RequestMapping(value = "/**")
public class ResponseController {

    @Autowired
    ProxyService proxyService;

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity> responseHttpResult() {

        DeferredResult deferredResult = new DeferredResult();
        proxyService.requestProxyService(deferredResult);

        return deferredResult;
    }
}