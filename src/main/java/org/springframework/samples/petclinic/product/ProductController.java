package org.springframework.samples.petclinic.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private static final String VIEWS_PRODUCT_CREATE_FORM = "products/createOrUpdateProductForm";
    private static final String VIEWS_PRODUCT_LIST = "products/productList";

    @ModelAttribute("productTypes")
    public List<ProductType> populateProductTypes() {
        return this.productService.findAllProductTypes();
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView showProductList() {
        ModelAndView mav = new ModelAndView(VIEWS_PRODUCT_LIST);
        mav.addObject("products", productService.getAllProducts());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView mav = new ModelAndView(VIEWS_PRODUCT_CREATE_FORM);
        mav.addObject("product", new Product());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView processCreationForm(@Valid Product product, BindingResult result) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView(VIEWS_PRODUCT_CREATE_FORM);
            mav.addObject("product", product);
            mav.addObject("types", productService.findAllProductTypes());
        } else {
            this.productService.save(product);
            mav = new ModelAndView("welcome");
        }
        return mav;
    }
}