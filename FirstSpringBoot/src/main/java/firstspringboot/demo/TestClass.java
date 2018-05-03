package firstspringboot.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {
    @RequestMapping("/")
    public String index(){


        return "hello world";

    }
}
