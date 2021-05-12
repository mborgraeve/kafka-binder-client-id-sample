package com.mborgraeve.samples.spring.cloud.stream.kafka.binder.clientid.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
public class Controller {

    @Bean
    public Function<Flux<String>, Flux<String>> process() {
        return data -> data.map(String::toLowerCase);
    }
}
