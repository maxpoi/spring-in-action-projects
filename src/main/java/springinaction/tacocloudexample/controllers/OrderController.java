package springinaction.tacocloudexample.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import springinaction.tacocloudexample.objects.Order;
import springinaction.tacocloudexample.repository.OrderRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) { this.orderRepo = orderRepo; }

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               Errors errors,
                               SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
