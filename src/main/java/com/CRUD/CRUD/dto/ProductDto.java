package com.CRUD.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 3, message = "product name at least consist 3 character")
    private String name;
}
