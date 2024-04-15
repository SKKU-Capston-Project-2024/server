package site.mutopia.server.domain.profile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/hi")
    public String index() {
        return "Hello";
    }

}
