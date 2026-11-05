package com.ecommerce.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.model.Brand;
import com.ecommerce.repository.BrandRepositoryImpl;
import com.ecommerce.repository.CategoryRepositoryImpl;
import com.ecommerce.repository.ProductRepositoryImpl;
import com.ecommerce.service.BrandService;
import com.ecommerce.service.CategoryService;



public class EcommerceApplication {

	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("STARTED");
		
		/*
		CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
		CategoryService categoryService = new CategoryService(categoryRepository);
		
		ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
		ProductService productService = new ProductService(productRepository);
		
		BrandRepositoryImpl brandRepository = new BrandRepositoryImpl();
		BrandService brandService = new BrandService(brandRepository);
		*/
		
		/*
		 * =========== PRODUCT TESTING =============  
		 */
		
		/*
		List<Category> categories = categoryService.getAllCategories();

		if (categories.isEmpty()) {
			System.out.println("No categories available. Add a category first.");
			return;
		}
		
		for (int i = 0; i < categories.size(); i++) {
		    System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
		}
		
		
		System.out.print("Select category: ");
		int choice = sc.nextInt();
		sc.nextLine();

		if (choice < 1 || choice > categories.size()) {
		    System.out.println("Invalid category selection.");
		    return;
		}
		
		Category selectedCategory = categories.get(choice - 1);

		System.out.print("Enter product name: ");
		String productName = sc.nextLine();

		System.out.print("Enter price: ");
		BigDecimal price = sc.nextBigDecimal();
		
		
		System.out.print("Enter quantity: ");
		int quantity = sc.nextInt();
		sc.nextLine();

	*/
		/*
		Category c = categoryService.getCategoryById(1);
		
		Product p = new Product(
			"iphone 18 pro",
			new BigDecimal(2_19_000.0),
			c,
			100
		);
		
		
		Product savedProduct = productService.addProduct(p);

		System.out.println("Product added successfully!");
		System.out.println("Product ID: " + savedProduct.getProductId());
		
		*/

		/*// read all products
		List<Product> products = productService.getAllProducts();
		for (Product p : products) {
		    System.out.println(p.getProductId());
		    System.out.println(p.getProductName());
		    System.out.println(p.getCategory().getCategoryName());
		    System.out.println(p.getPrice());
		    System.out.println(p.getQuantity());
		}
		*/
		
		/* // read a product by id 
		Product p = productService.getProductById(363L);
		System.out.println(p.getProductId());
	    System.out.println(p.getProductName());
	    System.out.println(p.getCategory().getCategoryName());
	    System.out.println(p.getPrice());
	    System.out.println(p.getQuantity());
	    */
		
		/* // update product name
		System.out.println(productService.updateProductName(363L, "iphone 18 pro max"));
		*/
		
		
		/* // update price
		System.out.println(productService.updateProductPrice(2L, new BigDecimal("199999.0")));
		*/
		
		/*
		// update category
//		 System.out.println(productService.updateProductCategory(2L, 1));
		
//		System.out.println(productRepository.updateCategory(2L, 3));
		*/
		
		/* // search by name 
		List<Product> products = productService.searchByName("iph");
		System.out.println(products.isEmpty());
		for (Product p : products) {
		    System.out.println(p.getProductId());
		    System.out.println(p.getProductName());
		    System.out.println(p.getCategory().getCategoryName());
		    System.out.println(p.getPrice());
		    System.out.println(p.getQuantity());
		}
		*/ 
		
		/*  // search by category
		List<Product> products = productService.searchByCategory(1);
		for (Product p : products) {
		    System.out.println(p.getProductId());
		    System.out.println(p.getProductName());
		    System.out.println(p.getCategory().getCategoryName());
		    System.out.println(p.getPrice());
		    System.out.println(p.getQuantity());
		}
		*/
		
		/* // Search product by price range
		List<Product> products = productService.getProductByPriceRange(null, new BigDecimal(500000.0));
//				productRepository.findByPriceRange(new BigDecimal(0.0), new BigDecimal(500000.0));
		System.out.println(products.isEmpty());
		for (Product p : products) {
		    System.out.println(p.getProductId());
		    System.out.println(p.getProductName());
		    System.out.println(p.getCategory().getCategoryName());
		    System.out.println(p.getPrice());
		    System.out.println(p.getQuantity());
		}
		*/
		
		/**/  // delete a product
//		System.out.println(productService.deleteProduct(4L));
//		System.out.println(productRepository.deleteById(10L));

		
		
		/*
		 * ============== CATEGORY TESTING ====================
		 */
		
		/*// add a category
		Category category1 = new Category("Test Category");
		Category c = categoryService.addCategory(category1);
		
		System.out.println(c.getCategoryId());
		System.out.println(c.getCategoryName());
		System.out.println(c.getCreatedAt());
		System.out.println(c.getUpdatedAt());
		*/
		
		/* // read all categories
		List<Category> categories = categoryService.getAllCategories();
		for (Category c : categories) {
			System.out.println(c.getCategoryId());
			System.out.println(c.getCategoryName());
			System.out.println(c.getCreatedAt());
			System.out.println(c.getUpdatedAt());
		}
		*/
		
		/* // read a category
		Category c = categoryService.getCategoryById(2);
		System.out.println(c.getCategoryId());
		System.out.println(c.getCategoryName());
		System.out.println(c.getCreatedAt());
		System.out.println(c.getUpdatedAt());
		*/
		
		
		// =====================================================
		/* ===== BRAND TESTING ===== */
		// ADD A BRAND
		/*
		Brand brand1 = new Brand("Alpha Brand");
		Brand brand = brandService.addBrand(brand1);
		
		System.out.println(brand.getBrandId());
		System.out.println(brand.getBrandName());
		System.out.println(brand.getCreatedAt());
		System.out.println(brand.getUpdatedAt());
		*/
	}

}
