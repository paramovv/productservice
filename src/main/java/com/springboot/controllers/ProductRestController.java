package com.springboot.controllers;

import com.springboot.dto.Coupon;
import com.springboot.model.Product;
import com.springboot.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    @Autowired
    ProductRepo repo;
    @Autowired
    RestTemplate restTemplate;
    @Value("${couponService.url}")
    String couponServiceUrl;

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product){
    Coupon coupon = restTemplate.getForObject(couponServiceUrl + product.getCouponCode(), Coupon.class);
    product.setPrice((product.getPrice().subtract(coupon.getDiscount())));
    return repo.save(product);
    }

}
