package com.zerobase.cms.order.domain.product;

import com.zerobase.cms.order.domain.model.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductItemDto> items;

    public static ProductDto from(Product product) {
        List<ProductItemDto> items = product.getProductItems()
                .stream().map(ProductItemDto::from).toList();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .items(items)
                .build();
    }
}
