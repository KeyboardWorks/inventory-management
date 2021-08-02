CREATE TABLE IF NOT EXISTS product_category (

    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    active VARCHAR(1),
    note TEXT,
    fk_product_category_parent VARCHAR(36),

    UNIQUE(code),
    PRIMARY KEY(id)

);

CREATE TABLE IF NOT EXISTS product (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    fk_product_category VARCHAR(36),

    UNIQUE(code),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS product_packaging (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    quantity_to_base DECIMAL(19,2) DEFAULT 1,
    fk_product VARCHAR(36),
    fk_unit_of_measure VARCHAR(36),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS unit_of_measure (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50),

    UNIQUE(code),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS goods_receipt (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    code VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    note TEXT,

    UNIQUE(code),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS goods_receipt_item (
    id VARCHAR(36) NOT NULL,
    price DECIMAL(19,2) DEFAULT 0,
    fk_goods_receipt VARCHAR(36),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS inventory_transaction_item (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    receipted DECIMAL(19,2) DEFAULT 0,
    issued DECIMAL(19,2) DEFAULT 0,
    fk_product VARCHAR(36),
    fk_product_packaging VARCHAR(36),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS product_in_out_transaction (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    date DATE NOT NULL,
    type VARCHAR(3) NOT NULL,
    quantity DECIMAL(19,2) DEFAULT 0,
    quantity_left DECIMAL(19,2) DEFAULT 0,
    price DECIMAL(19,2) DEFAULT 0,
    fk_product VARCHAR(36),
    fk_product_packaging VARCHAR(36),
    fk_inventory_transaction_item VARCHAR(36),
    fk_product_in_out_transaction_in_from VARCHAR(36),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS product_average_price (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    quantity DECIMAL(19,2) DEFAULT 0,
    price DECIMAL(19,2) DEFAULT 0,
    fk_product VARCHAR(36),
    fk_product_packaging VARCHAR(36),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS goods_issue (
    id VARCHAR(36) NOT NULL,
    created_by VARCHAR(50),
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date_time TIMESTAMP,
    code VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    note TEXT,

    UNIQUE(code),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS goods_issue_item (
    id VARCHAR(36) NOT NULL,
    fk_goods_issue VARCHAR(36),

    PRIMARY KEY(id)
);