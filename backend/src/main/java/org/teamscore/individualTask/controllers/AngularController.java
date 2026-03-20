package org.teamscore.individualTask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AngularController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
