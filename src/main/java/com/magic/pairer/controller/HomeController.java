package com.magic.pairer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nweber on 4/1/19.
 */
@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "pairer is online!";
    }

}
