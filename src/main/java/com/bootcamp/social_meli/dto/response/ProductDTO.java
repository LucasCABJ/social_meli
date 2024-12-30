package com.bootcamp.social_meli.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    @Min(1)
    private Long product_id;
    private String product_name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
