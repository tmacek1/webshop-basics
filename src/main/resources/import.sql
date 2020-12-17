INSERT INTO public.customer (id, email, first_name, last_name) VALUES (1, 'tomislav.macek4@gmail.com', 'Tomislav', 'Macek');
INSERT INTO public.product (id, code, description, is_available, "name", price_hrk) VALUES (1, '12345', 'productDescription', true, 'productA', 10.00);
INSERT INTO public.webshop_order (id, status, total_price_eur, total_price_hrk, customer_id) VALUES(1, 'DRAFT', 0, 0, 1);
