package com.abc.product.client.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author amlandeep.nandi
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

	@Valid
	@Min(value = 100000, message ="id must be 6 digit")
	@Max(value = 999999, message ="id must be 6 digit")
	Long id;

	@Valid()
	@NotBlank(message = "Product Name can't be blank")
	@NotNull(message = "Product Name can't be null")
	@Size(max=20, message="Product Name can't be more than 20 characters")
	String productname;

	@Valid()
	@NotNull(message = "Quantity can't be null")
	@Positive
	Integer quantity;

	@Valid()
	@NotBlank(message = "Brand can't be blank")
	@NotNull(message = "Brand can't be null")
	@Size(max=16, message="Brand can't be more than 16 characters")
	String brand;

	@Valid()
	@NotNull(message = "Price can't be null")
	@DecimalMin(value = "0.99", inclusive = false)
    @Digits(integer=3, fraction=2)
	BigDecimal price;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Valid()
	LocalDate expiryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Valid()
	LocalDate manufacturedDate;

}
