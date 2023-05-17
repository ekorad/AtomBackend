package com.atom.application.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.atom.application.models.Product;
import com.atom.application.repos.ProductRepository;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository repo;

    /**
     * Retrieves all of the existing web products.
     * 
     * @return all of the existing web products, or an empty <code>List</code> if
     *         none exist
     */
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductByProductName(String requestedProductName) {
        Optional<Product> persistedRequestedProductOpt = repo.findByProductName(requestedProductName);
        if (!persistedRequestedProductOpt.isPresent()) {
            throw new EntityNotFoundException("No product found with product name: '" + requestedProductName + "'");
        }
        Product persistedRequestedProduct = persistedRequestedProductOpt.get();
        return persistedRequestedProduct;
    }

    public Product addNewProduct(Product newProduct) {
        return repo.save(newProduct);
    }

    public Optional<Product> getProductById(Long id) {

        Optional<Product> p = repo.findById(id);

        return p;
    }

    public List<Optional<Product>> getProductsById(List<String> ids) {

        List<Optional<Product>> products = new ArrayList<Optional<Product>>();

        List<Long> longIds = new ArrayList<>();

        for (String stringId : ids) {
            longIds.add(Long.valueOf(stringId));
        }

        for (Long productId : longIds) {
            Optional<Product> p = repo.findById(productId);
            products.add(p);
        }
        return products;
    }

    public Product updateProduct(long id, Product updatedroduct) {

        Product existingProductDb = getProductById(id).get();

        existingProductDb.setCpu(updatedroduct.getCpu());
        existingProductDb.setDescription(updatedroduct.getDescription());
        existingProductDb.setGpu(updatedroduct.getGpu());
        existingProductDb.setMotherBoard(updatedroduct.getMotherBoard());
        existingProductDb.setProductName(updatedroduct.getProductName());
        existingProductDb.setNewPrice(updatedroduct.getNewPrice());
        existingProductDb.setOldPrice(updatedroduct.getOldPrice());
        existingProductDb.setRam(updatedroduct.getRam());
        existingProductDb.setViews(updatedroduct.getViews());

        return repo.save(existingProductDb);
    }

    public void deleteProducts(List<String> idsProductsToBeDeleted) {
        List<Product> products = new ArrayList<Product>();
        List<Long> longIds = new ArrayList<>();

        for (String stringId : idsProductsToBeDeleted) {
            longIds.add(Long.valueOf(stringId));
        }
        for (Long productId : longIds) {
            Optional<Product> p = repo.findById(productId);
            products.add(p.get());
        }
        repo.deleteInBatch(products);
    }

    public List<Product> getProductsWithSimilarPrice(List<String> ids) {
        List<Product> requestedProducts = getProductsById(ids).stream().map(opt -> opt.get())
                .collect(Collectors.toList());
        Double avgPrice = requestedProducts.stream().map(product -> product.getNewPrice())
                .collect(Collectors.summingDouble(price -> Double.valueOf(price))) / ids.size();

        ArrayList<Product> allProducts = new ArrayList<>(getAllProducts());
        allProducts.removeAll(requestedProducts);
        Collections.sort(allProducts, (p1, p2) -> (int) (p1.getNewPrice() - p2.getNewPrice()));

        Product avgProd = null;

        for (int i = 0; i < allProducts.size() - 1; i++) {
            Product prod = allProducts.get(i);
            Product nextProd = allProducts.get(i + 1);
            if (prod.getNewPrice() <= avgPrice && nextProd.getNewPrice() >= avgPrice) {
                avgProd = prod;
                break;
            }
        }

        List<Product> returnedProds = new ArrayList<Product>();
        if (avgProd == allProducts.get(0)) {
            for (int i = 0; i < 3; i++) {
                returnedProds.add(allProducts.get(i));
            }
            return returnedProds;
        }

        if (avgProd == allProducts.get(allProducts.size() - 1)) {
            for (int i = allProducts.size() - 1; i >= allProducts.size() - 4; i--) {
                returnedProds.add(allProducts.get(i));
            }
            return returnedProds;
        }

        int index = allProducts.indexOf(avgProd);
        for (int i = index - 1; i < index + 2; i++) {
            returnedProds.add(allProducts.get(i));
        }
        return returnedProds;
    }

    public List<Product> getMostViewedProducts() {
        ArrayList<Product> allProducts = new ArrayList<>(getAllProducts());
        Collections.sort(allProducts, (p1, p2) -> (int) (p2.getViews() - p1.getViews()));
        List<Product> returnedProds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            returnedProds.add(allProducts.get(i));
        }
        
        return returnedProds;
    }

    public List<Product> getMostRatedProducts() {
        ArrayList<Product> allProducts = new ArrayList<>(getAllProducts());
        Collections.sort(allProducts,
                (p1, p2) -> (int) ((p2.getReviews().stream().map(rev -> rev.getGrade())
                        .collect(Collectors.summingDouble(grade -> Double.valueOf(grade))) / p2.getReviews().size())
                        - (p1.getReviews().stream().map(rev -> rev.getGrade()).collect(
                                Collectors.summingDouble(grade -> Double.valueOf(grade))) / p1.getReviews().size())));
        List<Product> returnedProds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            returnedProds.add(allProducts.get(i));
        }
        
        return returnedProds;
    }

}
