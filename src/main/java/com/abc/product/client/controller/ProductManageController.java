package com.abc.product.client.controller;

import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.abc.product.client.model.Product;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author amlandeep.nandi
 *
 */
@Controller
@Slf4j
public class ProductManageController
{
	private final RestTemplate restTemplate;

	ProductManageController(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	@GetMapping("/")
	public String loadHomePage(Model model)
	{
		return showProductList(model);
	}

	@GetMapping("/existingProducts")
	public String showProductList(Model model)
	{
		Product[] products = restTemplate.getForObject("http://PRODUCT-SERVICE/products", Product[].class);
		model.addAttribute("products", Arrays.asList(products));
		return "index";
	}

	@GetMapping("/addProduct")
	public String showAdditionForm(Product product)
	{
		return "add-product";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@Valid @ModelAttribute Product product, BindingResult result, Model model)
	{
		if(result.hasErrors())
		{
			return "add-product";
		}
		else
		{
			if(product.getId() == null)
			{
				Random r = new Random();
				long id = r.nextInt(99999) + 100000;
				log.info("No id came through input, hence created the same, id: {}, id length: {}", id, String.valueOf(id).length());
				product.setId(id);
			}
			Product createdProduct = restTemplate.postForObject("http://PRODUCT-SERVICE/products", product, Product.class);
			log.info("New Product Created is: {}", createdProduct.toString());
			return "redirect:/existingProducts";
		}
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable long id, Model model)
	{
		Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + id, Product.class);
		log.info("Product requested for an update is: {}", product.toString());
		model.addAttribute("product", product);
		return "update-product";
	}

	@PostMapping("/update/{id}")
	public String updateProduct(@PathVariable long id, @Valid Product product, BindingResult result,
			Model model)
	{
		log.info("inside update product call, id: {} & product name: {}", id, product.getProductname());
		if(result.hasErrors())
		{
			log.error("some error happened, errors: {}", result.getAllErrors());
			product.setId(id);
			return "update-product";
		}
		else
		{
			Product updatedProduct = restTemplate.postForObject("http://PRODUCT-SERVICE/update/" + id, product, Product.class);
			log.info("Updated Product: {}", updatedProduct.toString());
			return "redirect:/existingProducts";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable long id, Model model)
	{
		log.info("inside delete product call, id: {} ", id);
		restTemplate.getForObject("http://PRODUCT-SERVICE/delete/" + id, Boolean.class);
		return "redirect:/existingProducts";
	}
	/*
	 * @RequestMapping(value = "/productList", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Product[] productList() { Product[] result =
	 * restTemplate.getForObject("http://PRODUCT-SERVICE/products",
	 * Product[].class); return result; }
	 * 
	 * @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Product addProduct(@RequestBody Product product) {
	 * 
	 * product.setId(1111L); product.setProductname("Rubber");
	 * product.setQuantity(1); product.setBrand("Nataraj"); product.setPrice(new
	 * BigDecimal("101.00")); product.setExpiryDate(LocalDate.of(2025, 06, 30));
	 * product.setManufacturedDate(LocalDate.now());
	 * 
	 * Product addedProduct =
	 * restTemplate.postForObject("http://PRODUCT-SERVICE/products", product,
	 * Product.class);
	 * 
	 * return addedProduct; }
	 * 
	 * @RequestMapping(value = "/editProduct/{id}", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Product editProduct(@RequestBody Product
	 * product, @PathVariable Long id) {
	 * 
	 * Product updatedProduct =
	 * restTemplate.postForObject("http://PRODUCT-SERVICE/update/"+id, product,
	 * Product.class);
	 * 
	 * return updatedProduct; }
	 * 
	 * @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Boolean deleteProduct(@PathVariable Long id) {
	 * 
	 * Boolean isRemoved =
	 * restTemplate.getForObject("http://PRODUCT-SERVICE/delete/"+id,
	 * Boolean.class);
	 * 
	 * return isRemoved; }
	 */
}
