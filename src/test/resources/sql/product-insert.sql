INSERT INTO product(id, code, name, fk_product_category)
VALUES('001', 'Product Code 01', 'Product Name 01', '001'),
('002', 'Product Code 02', 'Product Name 02', '002');

INSERT INTO product_packaging(id, quantity_to_base, fk_unit_of_measure, fk_product)
VALUES('001', 1, '001', '001'),
('002', 2, '002', '002');