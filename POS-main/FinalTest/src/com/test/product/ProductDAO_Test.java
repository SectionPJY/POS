package com.test.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.PayType;
import com.java.pos.db.ProductDAO;
import com.java.pos.db.ProductDTO;

public class ProductDAO_Test {
	
	@Test
	void findAll() {
		// given
		ProductDAO dao = new ProductDAO();
		
		// when
		List<ProductDTO> products = dao.findAll();
		
		// then
		assertNotNull(products);
	}

}
