package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.exception.CustomException;
import com.zerobase.cms.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;

    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }

    public Product getByProductId(Long id){
        return productRepository.findWithProductItemsById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }

    public List<Product> getListByProductIds(List<Long> productIds){
        return productRepository.findAllByIdIn(productIds);
    }

}
