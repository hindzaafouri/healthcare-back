package tn.esprit.healthcare.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/message")
    public String getMessage () {
        return "Healthcare springBoot deployed" ;
    }
}
