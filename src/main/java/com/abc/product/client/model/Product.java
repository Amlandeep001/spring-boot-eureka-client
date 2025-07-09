package com.abc.product.client.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author amlandeep.nandi
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product
{
	@Valid
	@Min(value = 100000, message = "id must be 6 digit")
	@Max(value = 999999, message = "id must be 6 digit")
	Long id;

	@Valid()
	@NotBlank(message = "Product Name must not be null or blank")
	@Size(max = 20, message = "Product Name can't be more than 20 characters")
	String productname;

	@Valid()
	@NotNull(message = "Quantity can't be null")
	@Positive
	Integer quantity;

	@Valid()
	@NotBlank(message = "Brand must not be null or blank")
	@Size(max = 16, message = "Brand can't be more than 16 characters")
	String brand;

	@Valid()
	@NotNull(message = "Price can't be null")
	@DecimalMin(value = "0.99", inclusive = false)
	@Digits(integer = 3, fraction = 2)
	BigDecimal price;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Valid()
	LocalDate expiryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Valid()
	LocalDate manufacturedDate;
}
