package com.pgalik.AdventOfCode.day04;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController("day04Controller")
@RequestMapping("/04")
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
