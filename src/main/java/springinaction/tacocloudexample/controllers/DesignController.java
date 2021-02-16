package springinaction.tacocloudexample.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springinaction.tacocloudexample.objects.Ingredient;
import springinaction.tacocloudexample.objects.Ingredient.Type;
import springinaction.tacocloudexample.objects.Order;
import springinaction.tacocloudexample.objects.Taco;
import springinaction.tacocloudexample.repository.IngredientRepository;
import springinaction.tacocloudexample.repository.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SessionAttributes("order")
@Controller
@RequestMapping("/design")
public class DesignController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignController(IngredientRepository ingredientRepo,
                            TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @GetMapping
    /***
     * Model is an object that ferries data between a controller and whatever view is charged
     * with rendering that data. Ultimately, data that's placed in Model attributes is copied into
     * the servlet response attributes, where the view can find them.
     */
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }


    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @PostMapping
    /***
     * Need to add this @ModelAttribute("design").
     * Because previously we add "design" (a new Taco()) as an attribute into the model
     * and in the html we refer the object indeed as "design".
     * However, with just @Valid, it automatically asks for a "Taco" attribute from the model, which is of course undefined.
     */
    public String processDesign(@Valid Taco design,
                                Errors errors,
                                @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
