CREATE DATABASE ecommerce_jdbc;

USE ecommerce_jdbc;

CREATE TABLE category (
	category_id INT AUTO_INCREMENT,
	category_name VARCHAR(50) NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
		ON UPDATE CURRENT_TIMESTAMP,

	CONSTRAINT pk_category
		PRIMARY KEY (category_id),

	CONSTRAINT uq_category_name
		UNIQUE (category_name),

	CONSTRAINT chk_category_name_not_blank
		CHECK (category_name REGEXP '[^[:space:]]')
);


CREATE TABLE product (
	product_id BIGINT AUTO_INCREMENT,
	product_name VARCHAR(200) NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
	category_id INT NOT NULL,
	quantity INT NOT NULL DEFAULT 0,

	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
		ON UPDATE CURRENT_TIMESTAMP,


	CONSTRAINT pk_product
		PRIMARY KEY (product_id),

	CONSTRAINT uk_product_name
        UNIQUE (product_name),

	CONSTRAINT chk_product_name_not_blank
		CHECK (product_name REGEXP '[^[:space:]]'),

	CONSTRAINT chk_product_price_non_negative
		CHECK (PRICE >= 0),

	CONSTRAINT chk_product_quantity_non_negative
		CHECK (quantity >= 0),

	CONSTRAINT fk_product_category
		FOREIGN KEY (category_id)
		REFERENCES category(category_id)
		ON UPDATE CASCADE -- update in parent effects in child table too
		ON DELETE RESTRICT -- not possible to dlt a record if child table refers it
);


ALTER TABLE product
ADD CONSTRAINT uk_product_name UNIQUE (product_name);

-- BRAND TABLE
CREATE TABLE brand (
	brand_id INT AUTO_INCREMENT,
	brand_name VARCHAR(100) NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
		ON UPDATE CURRENT_TIMESTAMP,

	CONSTRAINT pk_brand
		PRIMARY KEY (brand_id),

	CONSTRAINT uq_brand_name
		UNIQUE (brand_name),

	CONSTRAINT chk_brand_name_not_blank
		CHECK (brand_name REGEXP '[^[:space:]]')
);


