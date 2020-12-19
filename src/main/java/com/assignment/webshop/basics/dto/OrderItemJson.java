package com.assignment.webshop.basics.dto;

import com.assignment.webshop.basics.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemJson {

    private long id;
    private int quantity;

    @JsonProperty("product")
    private ProductDTO productDTO;

    public void setProductField(Product product) {
        this.productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPriceHrk(product.getPriceHrk());
        productDTO.setAvailable(product.isAvailable());
    }

}
