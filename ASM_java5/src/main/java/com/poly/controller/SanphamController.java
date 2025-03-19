package com.poly.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.poly.model.sanpham;

@Controller
public class SanphamController {
	@RequestMapping("/sanpham")
    public String danhSachSanPham(Model model) {
        List<sanpham> danhSachSanPham = Arrays.asList(
            new sanpham(1, "HADES STORM RIDER TEE", 460000, "/images/iphone1.webp"),
            new sanpham(2, "Áo Levents", 350000, "/images/iphone2.webp"),
            new sanpham(3, "Áo Thun", 300000, "/images/iphone3.webp"),
            new sanpham(4, "Áo Hoodie", 500000, "/images/iphone4.webp")
        );

        model.addAttribute("sanPhams", danhSachSanPham);
        return "view/sanpham";
    }
}
