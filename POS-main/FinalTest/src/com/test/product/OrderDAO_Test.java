package com.test.product;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.PayType;

public class OrderDAO_Test {
	@Test
	void findAll() {
		OrderDAO orderDao = new OrderDAO();
		List<OrderDTO> findAll = orderDao.findAll();
	}
	
	@Test
	void save() {
		// given
		List<OrderDetailDTO> detailDTOs = Arrays.asList(new OrderDetailDTO(1, 1, 23),new OrderDetailDTO(1, 1, 23));
		OrderDTO orderDTO = new OrderDTO(PayType.CARD, "aaa", 423423, detailDTOs);

		// when
		OrderDAO orderDao = new OrderDAO();
		orderDao.save(orderDTO);
	}
}
