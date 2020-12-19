INSERT INTO public.customer (id, email, first_name, last_name) VALUES (1, 'tomislav.macek4@gmail.com', 'Tomislav', 'Macek');
INSERT INTO public.product (id, code, description, is_available, name, price_hrk) VALUES (1, '123456', 'productDescription', true, 'productB', 50.00);
INSERT INTO public.product (id, code, description, is_available, name, price_hrk) VALUES (3, '1234567', 'productDescription', true, 'productC', 20.00);
INSERT INTO public.webshop_order (id, status, total_price_eur, total_price_hrk, customer_id) VALUES(1, 'DRAFT', 0, 0, 1);
INSERT INTO public.webshop_order_item (id, quantity, order_id, product_id) VALUES(1, 1, 1, 1);
