package com.salas.manager.app.controller;

import com.salas.manager.app.client.ProductRestService;
import com.salas.manager.app.controller.payload.NewProductPayload;
import com.salas.manager.app.entity.Product;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
@RequestMapping("/catalogue/products")
public class ProductsController {

    @Autowired
    private final ProductRestService productService;

    public ProductsController(ProductRestService productService) {
        this.productService = productService;
    }

    @GetMapping("list")
    public String getProductList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("products", productService.findProducts(filter));
        model.addAttribute("filter", filter);
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(NewProductPayload payload) {
        Product newProduct = productService.createProduct(payload.title(), payload.details());
        return "redirect:/catalogue/products/list";
    }

}
