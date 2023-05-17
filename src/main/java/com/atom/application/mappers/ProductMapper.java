package com.atom.application.mappers;

import com.atom.application.dtos.ProductDTO;
import com.atom.application.models.Product;


public class ProductMapper implements EntityDTOMapper<Product, ProductDTO>{

    @Override
    public ProductDTO mapToDto(Product entity) {
        ProductDTO dto = new ProductDTO();
       dto.setProductName(entity.getProductName());
       dto.setDescription(entity.getDescription());
       dto.setNewPrice(entity.getNewPrice());
       dto.setOldPrice(entity.getOldPrice());
       dto.setGpu(entity.getGpu());
       dto.setMotherBoard(entity.getMotherBoard());
       dto.setRam(entity.getRam());
       dto.setCpu(entity.getCpu());
        return dto;
    }

    @Override
    public Product mapToEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setNewPrice(dto.getNewPrice());
        entity.setOldPrice(dto.getOldPrice());
        entity.setRam(dto.getRam());
        entity.setGpu(dto.getGpu());
        entity.setMotherBoard(dto.getMotherBoard());
        entity.setCpu(dto.getCpu());
        return entity;
    }

  

    
}
