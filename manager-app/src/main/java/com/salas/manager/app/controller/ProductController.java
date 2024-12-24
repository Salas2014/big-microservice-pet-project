package com.salas.manager.app.controller;

import com.salas.manager.app.client.ProductRestService;
import com.salas.manager.app.controller.payload.UpdateProductPayload;
import com.salas.manager.app.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/catalogue/products/{productId:\\d+}")
public class ProductController {

    @Autowired
    private final ProductRestService productRestService;

    public ProductController(ProductRestService productRestService) {
        this.productRestService = productRestService;
    }

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productRestService.findProduct(productId).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @GetMapping()
    public String getProductById() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getEditProductById() {
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayload payload) {
        this.productRestService.updateProduct(product.id(), payload.title(), payload.details());
        return "redirect:/catalogue/products/%d".formatted(product.id());
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productRestService.deleteProduct(product.id());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", exception.getMessage());
        return "catalogue/products/errors/404";
    }
}
