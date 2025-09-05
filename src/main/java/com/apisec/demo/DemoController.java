package com.apisec.demo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Controller("/demo")
public class DemoController {
    private static final Logger logger =  LoggerFactory.getLogger(DemoController.class);
    @Get(uri="/", produces="text/plain")
    public String message() {
        return "Example Response";
    }

    @Get(uri="/timeout", produces="text/plain")
    public CompletableFuture<String> returnTimeout() {

        logger.info("waiting for 2 min");
        return CompletableFuture.supplyAsync(
                () -> {
                    logger.info("returning response after 2 min");
                    return "Response after 2mins"; },
                CompletableFuture.delayedExecutor(2, TimeUnit.MINUTES)
        );
    }

    @Get(uri="/serverError", produces="text/plain")
    public MutableHttpResponse<String> returnServerError() {

        return HttpResponse.serverError("Internal server error");
    }



}