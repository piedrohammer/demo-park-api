package com.hammer.demoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezoneConfig {

    @PostConstruct // -> vai fazer com que após a classe ser inicializada pelo Spring o metodo construtor dela é executado e após a execução do metodo construtor, o primeiro metodo que é executado é o timezoneConfig
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
