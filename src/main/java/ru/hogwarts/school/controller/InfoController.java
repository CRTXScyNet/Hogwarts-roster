package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        return port;
    }

    @GetMapping("/random-code")
    public Long getRandom(){
        long in = 0;
        for (int i = 1; i <= 1_000_000; i++) {
            in += i;
        }
        return in;
    }
    //при использовании стрима метод завершается за 0.024с, а при цикле за 0.003
    //замеры проводил в отдельном файле

}
