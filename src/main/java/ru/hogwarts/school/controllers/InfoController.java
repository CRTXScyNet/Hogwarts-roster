package ru.hogwarts.school.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;

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

        return LongStream.iterate(1L, a -> a + 1).limit(1_000_000).reduce(0L,Long::sum);
    }
    //теперь занимаемое время: 0.012, что в два раза меньше

}
