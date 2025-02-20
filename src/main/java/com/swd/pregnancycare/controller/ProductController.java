package com.swd.pregnancycare.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "Quản lý sản phẩm")
public class ProductController {

    @GetMapping
    @Operation(summary = "Lấy danh sách sản phẩm", description = "Trả về danh sách tất cả sản phẩm")
    public List<String> getProducts() {
        return Arrays.asList("Laptop", "Smartphone", "Tablet");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin sản phẩm theo ID")
    public String getProductById(@PathVariable int id) {
        return "Sản phẩm " + id;
    }
}

