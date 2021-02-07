package springinaction.tacocloudexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * Controllers are the major players in Spring's MVC framework.
 * Their primary job is to handle HTTP requests and either hand a request off to a view to render HTML
 * or write data directly to the body of a response.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
