package com.poly.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.sanpham;

@Controller
public class indexController {
    private List<sanpham> danhSachSanPham = Arrays.asList(
        new sanpham(1, "IPHONE X", 12000000, "/images/iphone1.webp"),
        new sanpham(2, "IPHONE 12 PRO MAX ", 35000000, "/images/iphone2.webp"),
        new sanpham(3, "OPPO RENO13 F", 3000000, "/images/oppo1.webp"),
        new sanpham(4, "OPPO RENO12 5G", 3500000, "/images/oppo2.webp"),
        new sanpham(5, "OPPO A79 5G", 4200000, "/images/oppo7.webp"),
        new sanpham(6, "OPPO A38 ", 3800000, "/images/oppo8.webp"),
        new sanpham(7, "SAMSUNG GALAXY A55 5G", 7000000, "/images/samsung7.webp"),
        new sanpham(8, "SAMSUNG GALAXY A16 5G", 250000, "/images/samsung8.webp"),
        new sanpham(9, "XIAOMI RED NOTE 14", 8700000, "/images/xiaomi2.webp"),
        new sanpham(10, "XIAOMI POCO M6", 7800000, "/images/xiaomi5.webp")
    );

    @RequestMapping("/home/index")
    public String home(Model model) {
        model.addAttribute("sanPhams", danhSachSanPham);
        model.addAttribute("content", "view/home");
        return "view/layout";
    }
    @RequestMapping("/home/admin")
    public String admin(Model model) {
       
        model.addAttribute("content", "view/admin");
        return "view/layout";
    }
    
    @RequestMapping("/home/login")
    public String login(Model model) {
        
        model.addAttribute("content", "view/login");
        return "view/layout";
    }
    @RequestMapping("/home/register")
    public String register(Model model) {
        
        model.addAttribute("content", "view/register");
        return "view/layout";
    }

    @RequestMapping("/home/sanpham")
    public String sanpham(Model model) {
        model.addAttribute("sanPhams", danhSachSanPham);
        model.addAttribute("content", "view/sanpham");
        return "view/layout";
    }

    // Route chi tiết sản phẩm
    @RequestMapping("/home/sanpham/{id}")
    public String chiTietSanPham(@PathVariable("id") int id, Model model) {
        Optional<sanpham> sp = danhSachSanPham.stream()
                                              .filter(p -> p.getId() == id)
                                              .findFirst();
        if (sp.isPresent()) {
            model.addAttribute("sanpham", sp.get());
            model.addAttribute("content", "view/sanphamchitiet"); // Gọi trang chi tiết sản phẩm
            return "view/layout";
        } else {
            return "redirect:/home/sanpham"; // Nếu không tìm thấy, quay lại danh sách sản phẩm
        }
    }

    @RequestMapping("/home/about")
    public String about(Model model) {
        model.addAttribute("content", "view/about");
        return "view/layout";
    }

    @RequestMapping("/home/contact")
    public String contact(Model model) {
        model.addAttribute("content", "view/contact");
        return "view/layout";
    }
}
