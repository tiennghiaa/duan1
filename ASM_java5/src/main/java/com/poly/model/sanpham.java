package com.poly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class sanpham {
	 private int id;
	    private String name;
	    private double price;
	    private String image; // Đảm bảo thuộc tính này tồn tại
}
