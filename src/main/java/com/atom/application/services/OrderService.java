package com.atom.application.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.dtos.OrderDTO;
import com.atom.application.models.Order;
import com.atom.application.models.Product;
import com.atom.application.repos.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;
    @Autowired
    private ProductsService prodService;

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public Order addNewOrder(Order newOrder) {
        return repo.save(newOrder);
    }

    public Optional<Order> getOrderById(Long id) {

        Optional<Order> order = repo.findById(id);

        return order;
    }

    public Order updateOrder(long id, OrderDTO updatedOrder) {

        Order existingOrderDb = getOrderById(id).get();

        List<Optional<Product>> optProds = prodService
                .getProductsById(Arrays.asList(updatedOrder.getProductsIds().split(",")));
        List<Product> prods = optProds.stream().map(opt -> opt.get()).collect(Collectors.toList());

        existingOrderDb.setProducts(prods);
        existingOrderDb.setAddress(updatedOrder.getAddress());
        existingOrderDb.setDeliveryMethod(updatedOrder.getDeliveryMethod());
        existingOrderDb.setPaymentMethod(updatedOrder.getPaymentMethod());
        existingOrderDb.setPhoneNumber(updatedOrder.getPhoneNumber());
        existingOrderDb.setStage(updatedOrder.getStage());

        return repo.save(existingOrderDb);
    }

    public List<Order> getOrdersById(List<String> ids) {

        List<Order> orders = new ArrayList<Order>();

        List<Long> longIds = new ArrayList<>();

        for (String stringId : ids) {
            longIds.add(Long.valueOf(stringId));
        }

        for (Long orderId : longIds) {

            try {
                orders.add(getOrderById(orderId).get());
            } catch (Exception e) {

                throw new EntityNotFoundException(" Nu exista order cu unul dintre aceste id-uri ");
            }

        }
        return orders;
    }

    public void deleteOrders(List<String> idsOrdersToBeDeleted) {
        List<Order> orders = new ArrayList<Order>();
        List<Long> longIds = new ArrayList<>();

        for (String stringId : idsOrdersToBeDeleted) {
            longIds.add(Long.valueOf(stringId));
        }
        for (Long orderId : longIds) {
            Optional<Order> p = repo.findById(orderId);
            orders.add(p.get());
        }
        repo.deleteInBatch(orders);
    }
}
