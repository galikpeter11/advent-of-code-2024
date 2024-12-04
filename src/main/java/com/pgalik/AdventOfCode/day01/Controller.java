package com.pgalik.AdventOfCode.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("day01Controller")
@RequestMapping("/01")
@RequiredArgsConstructor
public class Controller {
    private final Service service;

    @GetMapping("/1")
    public String solveFirst() throws IOException {
        return service.solveFirst();
    }
    @GetMapping("/2")
    public String solveSecond() throws IOException {
        return service.solveSecond();
    }
}
