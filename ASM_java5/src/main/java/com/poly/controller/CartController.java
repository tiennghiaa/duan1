package com.poly.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.model.CartItem;

@Controller
public class CartController {

	@GetMapping("/cart")
	public String hienThiGioHang(HttpSession session, Model model) {
	    List<CartItem> gioHang = (List<CartItem>) session.getAttribute("gioHang");
	    if (gioHang == null) {
	        gioHang = new ArrayList<>();
	        session.setAttribute("gioHang", gioHang);
	    }

	    // Tính tổng tiền
	    double totalPrice = 0;
	    for (CartItem item : gioHang) {
	        totalPrice += item.getPrice() * item.getQuantity();
	    }

	    model.addAttribute("gioHang", gioHang);
	    model.addAttribute("totalPrice", totalPrice); // Gửi tổng tiền lên View
	    model.addAttribute("content", "view/cart"); // Đặt content để layout hiển thị đúng
	    return "view/layout"; // Trả về layout chung
	}


    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/cart/add")
    public String themVaoGioHang(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("image") String image,
            @RequestParam("size") String size,
            HttpSession session) {

        List<CartItem> gioHang = (List<CartItem>) session.getAttribute("gioHang");
        if (gioHang == null) {
            gioHang = new ArrayList<>();
        }

        // Kiểm tra sản phẩm đã tồn tại chưa
        boolean daTonTai = false;
        for (CartItem item : gioHang) {
            if (item.getName().equals(name) && item.getSize().equals(size)) {
                item.setQuantity(item.getQuantity() + 1);
                daTonTai = true;
                break;
            }
        }

        // Nếu chưa có, thêm mới vào giỏ hàng
        if (!daTonTai) {
            gioHang.add(new CartItem(name, price, image, size, 1));
        }

        session.setAttribute("gioHang", gioHang);
        return "redirect:/cart"; // Chuyển hướng về /cart thay vì view/cart
    }

    @PostMapping("/cart/remove")
    @ResponseBody
    public Map<String, String> xoaSanPham(@RequestParam("name") String name, 
                                          @RequestParam("size") String size, 
                                          HttpSession session) {
        List<CartItem> gioHang = (List<CartItem>) session.getAttribute("gioHang");
        Map<String, String> response = new HashMap<>();

        if (gioHang != null) {
            boolean daXoa = gioHang.removeIf(item -> item.getName().equals(name) && item.getSize().equals(size));

            if (daXoa) {
                session.setAttribute("gioHang", gioHang); // Cập nhật session
            }

            // Tính lại tổng tiền sau khi xóa
            double tongTien = gioHang.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
            response.put("totalPrice", String.format("%,.0f đ", tongTien));
            response.put("status", daXoa ? "success" : "not_found");
        } else {
            response.put("status", "empty_cart");
        }

        return response;
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public Map<String, String> capNhatSoLuong(@RequestParam("name") String name,
                                              @RequestParam("size") String size,
                                              @RequestParam("quantity") int quantity,
                                              HttpSession session) {
        List<CartItem> gioHang = (List<CartItem>) session.getAttribute("gioHang");
        double tongTien = 0.0;
        Map<String, String> response = new HashMap<>();

        if (gioHang != null) {
            gioHang.removeIf(item -> item.getName().equals(name) && item.getSize().equals(size) && quantity <= 0);

            for (CartItem item : gioHang) {
                if (item.getName().equals(name) && item.getSize().equals(size)) {
                    item.setQuantity(quantity);
                }
                tongTien += item.getPrice() * item.getQuantity();
            }
            session.setAttribute("gioHang", gioHang);
        }

        // Trả về JSON chứa tổng tiền & số lượng mới
        response.put("totalPrice", String.format("%,.0f đ", tongTien));
        response.put("quantity", String.valueOf(quantity));
        return response;
    }
    
    @PostMapping("/checkout")
    public String thanhToan(HttpSession session, Model model) {
        List<CartItem> gioHang = (List<CartItem>) session.getAttribute("gioHang");
        if (gioHang == null || gioHang.isEmpty()) {
            return "redirect:/cart";
        }

        // ✅ Kiểm tra có dữ liệu hình ảnh không
        for (CartItem item : gioHang) {
            System.out.println("Sản phẩm: " + item.getName() + " - Ảnh: " + item.getImage());
        }

        model.addAttribute("gioHang", gioHang);
        model.addAttribute("totalPrice", gioHang.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());

        return "view/checkout";
    }

    }






    

