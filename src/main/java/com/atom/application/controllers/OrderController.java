package com.atom.application.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.atom.application.dtos.OrderDTO;
import com.atom.application.models.Order;
import com.atom.application.models.Product;
import com.atom.application.models.WebUser;
import com.atom.application.services.OrderService;
import com.atom.application.services.ProductsService;
import com.atom.application.services.WebUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WebUserService webUserService;
    @Autowired
    private ProductsService productsService;

    @GetMapping
    public List<OrderDTO> getAllProducts() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream().map(entity -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(entity.getId());
            dto.setAddress(entity.getAddress());
            dto.setDeliveryMethod(entity.getDeliveryMethod());
            dto.setPaymentMethod(entity.getPaymentMethod());
            dto.setPhoneNumber(entity.getPhoneNumber());
            dto.setStage(entity.getStage());
            dto.setUserId(entity.getUser().getId());
            dto.setProductsIds(entity.getProducts().stream().map(product -> product.getId().toString())
                    .collect(Collectors.joining(",")));
            return dto;
        }).collect(Collectors.toList());
        return orderDTOs;
    }

    @PostMapping("/add")
    public void addNewProduct(@RequestBody OrderDTO orderToBeAdded) {

        List<String> productIds = Arrays.asList(orderToBeAdded.getProductsIds().split(","));

        List<Optional<Product>> optionalProducts = productsService.getProductsById(productIds);

        List<Product> products = new ArrayList<Product>();

        for (Optional<Product> product : optionalProducts) {
            products.add(product.get());
        }

        Optional<WebUser> optionalUser = webUserService.getUserById(orderToBeAdded.getUserId());
        WebUser user = optionalUser.get();
        Order order = new Order();

        order.setAddress(orderToBeAdded.getAddress());
        order.setProducts(products);
        order.setUser(user);
        order.setStage(orderToBeAdded.getStage());
        order.setPaymentMethod(orderToBeAdded.getPaymentMethod());
        order.setDeliveryMethod(orderToBeAdded.getDeliveryMethod());
        order.setPhoneNumber(orderToBeAdded.getPhoneNumber());
        orderService.addNewOrder(order);
    }

    @PutMapping(path = "/update", params = { "id" })
    public void updateExistingProd(@RequestParam Long id, @RequestBody OrderDTO editedOrder) {
        orderService.updateOrder(id, editedOrder);
    }

    @GetMapping(path = "/ordersByIds", params = { "IdsAtOnce" })
    public List<Order> getProductByIds(@RequestParam String IdsAtOnce) {
        List<String> ids = Arrays.asList(IdsAtOnce.split(","));

        return orderService.getOrdersById(ids);

    }

    @DeleteMapping(path = "/delete", params = { "IdsAtOnce" })
    public void deleteProductByIds(@RequestParam String IdsAtOnce) {
        List<String> ids = Arrays.asList(IdsAtOnce.split(","));

        orderService.deleteOrders(ids);

    }
}
