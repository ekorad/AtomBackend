-- -- -- initial user permissions
-- -- -- hardcoded and read-only, cannot be altered by anyone except devs
INSERT INTO user_permissions (name, description) VALUES
    ('CREATE_SELF_COMMENTS',    'Utilizatorul poate scrie comentarii'), 
    ('MODIFY_SELF_COMMENTS',    'Utilizatorul isi poate edita propriile comentarii'), 
    ('DELETE_SELF_COMMENTS',    'Utilizatorul isi poate sterge propriile comentarii'),
    ('PLACE_ORDER',             'Utilizatorul poate plasa comenzi'), 
    ('MODIFY_ANY_ORDER',        'Utilizatorul poate modifica orice comanda a oricarui utilizator'), 
    ('DELETE_ANY_ORDER',        'Utilizatorul poate sterge orice comanda a oricarui utilizator'), 
    ('READ_ANY_ORDER',          'Utilizatorul poate accesa orice comanda a oricarui utilizator'), 
    ('MODIFY_ANY_COMMENT',      'Utilizatorul poate modifica orice comentariu al oricarui utilizator'), 
    ('DELETE_ANY_COMMENT',      'Utilizatorul poate sterge orice comentariu al oricarui utilizator'),
    ('ADD_NEW_PRODUCT',         'Utilizatorul poate adauga noi produse in baza de date'), 
    ('MODIFY_ANY_PRODUCT',      'Utilizatorul poate modifica orice produs din baza de date'), 
    ('DELETE_ANY_PRODUCT',      'Utilizatorul poate sterge orice produs din baza de date'),
    ('CREATE_NEW_USER',         'Utilizatorul poate crea noi conturi de utilizatori'), 
    ('MODIFY_ANY_USER',         'Utilizatorul poate modifica contul oricarui utilizator'), 
    ('DELETE_ANY_USER',         'Utilizatorul poate sterge contul oricarui utilizator'), 
    ('READ_ANY_USER',           'Utilizatorul poate accesa datele contului oricarui utilizator'),
    ('READ_ANY_ROLE',           'Utilizatorul poate accesa toate rolurile de utilizator'), 
    ('MODIFY_ANY_ROLE',         'Utilizatorul poate modifica toate rolurile de utilizator'),
    ('DELETE_ANY_ROLE',         'Utilizatorul poate sterge orice rol de utilizator'),
    ('CREATE_NEW_ROLE',         'Utilizatorul poate crea noi roluri de utilizatori'),
    ('READ_ANY_PERMISSION',     'Utilizatorul poate accesa toate permisiunile de utilizator'),
    ('ELEVATED',                'Utilizatorul are drepturi administrative si poate accesa sectiunile de administrare');

-- -- initial user roles
-- -- hardcoded and read-only, cannot be altered by anyone except devs
-- -- newly created user roles are mutable
INSERT INTO user_roles (name, description) VALUES
    ('USER', 'Utilizatorul poate posta, edita si sterge comentarii si plasa comenzi'), 
    ('MODERATOR', 'Utilizatorul are, pe langa privilegiile de utilizator obisnuit, acces complet asupra tuturor comentariilor si poate vizualiza conturi, permisiuni si roluri de utilizatori'), 
    ('ADMIN', 'Utilizatorul are toate privilegiile posibile - are acces complet asupra comentariilor, produselor, conturilor, rolurilor si permisiunilor de utilizatori');

-- -- initial user role permissions
-- -- hardcoded and read-only, cannot be altered by anyone except devs
-- -- newly created user roles are mutable and can have any associated permission

-- -- initial USER permissions
-- -- hardcoded and read-only, cannot be altered by anyone except devs
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'USER' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'CREATE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'USER' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'USER' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'USER' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'PLACE_ORDER' LIMIT 1));

-- -- initial MODERATOR permissions
-- -- hardcoded and read-only, cannot be altered by anyone except devs
-- -- MODERATOR has elevated privileges
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'CREATE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'PLACE_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_COMMENT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_COMMENT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_PERMISSION' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_ROLE' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_USER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'MODERATOR' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'ELEVATED' LIMIT 1));

-- -- initial ADMIN permissions
-- -- hardcoded and read-only, cannot be altered by anyone except devs
-- -- ADMIN has elevated privileges
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'CREATE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_SELF_COMMENTS' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'PLACE_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_COMMENT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_COMMENT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_ORDER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'ADD_NEW_PRODUCT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_PRODUCT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_PRODUCT' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'CREATE_NEW_USER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_USER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_USER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_USER' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_ROLE' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'READ_ANY_PERMISSION' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'CREATE_NEW_ROLE' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'MODIFY_ANY_ROLE' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'DELETE_ANY_ROLE' LIMIT 1));
INSERT INTO roles_permissions(role_id, permission_id) VALUES 
    ((SELECT id FROM user_roles WHERE name = 'ADMIN' LIMIT 1), 
        (SELECT id FROM user_permissions WHERE name = 'ELEVATED' LIMIT 1));

-- -- initial user data
INSERT INTO users(first_name, last_name, username, email, `password`, locked, activated, role) VALUES
    ('Marin', 'Ionut', 'marinionut', 'marin.ionut@gmail.com', 
    '$2y$10$D9e42wAyiyfam4mHB7liCOtbiw4UgJBRH32devLgvbfbvYhGfBrau', false, true,
        (SELECT id FROM user_roles WHERE name = 'ADMIN'));

INSERT INTO users(first_name, last_name, username, email, `password`, locked, activated, role) VALUES
    ('Gogu', 'Leustean', 'goguleustean', 'gogu.leustean@gmail.com', 
    '$2y$10$LKJJTBYRqIzcfDlYDRIB9uGknnr5eu99y6rTZSEfcVu8ggRfV02rC', false, false,
        (SELECT id FROM user_roles WHERE name = 'USER'));

INSERT INTO review(review , grade )
    VALUES ('caltlator f bun', 4);


-- initial products data
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('Asus X Series',             'Laptop',        1200,         4000,    'Intel 80486',          'GEFORCE RTX 3090',      '16GB 2333MHz CL14','GIGABYTE B550', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('Asus Rog',         'Laptop Gaming',        5900,      4000,    'Intel Pentium',          'GeForce GT 1000 20',      '32GB 2600MHz CL15','ASRock B450', 0);
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('Huawei Magic Book',           'Laptop',   4399,       4000,    'AMD ATHLON',          'GeForce GT 700',      '16GB 2333MHz CL14','MSI A320M', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('Huawei MateBook', 'Laptop',         3499,      4000,    'Intel Pentium Pro',          'GeForce GT 200',      '16GB 2333MHz CL14','ASRock B450', 0);
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('Asus TUF',         'Laptop Gaming',        1200,         4000,    'Intel P7',          'GeForce GTX 1000',      '16GB 2333MHz CL14','GIGABYTE B550', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('Acer ASPIRE',      'Laptop profesional',   2299,      4000,    'Intel Core Solo ',          'GeForce GTX 1600',      '32GB 2600MHz CL15','ASRock B450', 0);
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('Acer Nitro',             'Laptop Gaming',   5455,       4000,    'Intel Core Duo',          'GeForce RTX',      '16GB 2333MHz CL14','ASRock B450', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('Apple MacBook',    'Laptop Profesional',        5900,      4000,    'Intel Core i7',          'Sapphire Radeon RX 570 PULSE',      '16GB 2333MHz CL14','placa baza', 0);
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('HP Pavilion',               'Laptop',        1200,         4000,    'Intel Core i3',          'GIGABYTE GeForce GT 710',      '16GB 2333MHz CL14','GIGABYTE B550', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('HP 250 G7',        'Laptop',        5430,      4000,    'Intel Core 2 Duo',          'NVidia GeForce GT710 ',      '32GB 2600MHz CL15','MSI A320M', 0);
INSERT INTO products(product_name , description, new_price,old_price,cpu,gpu,ram,mother_board,views)
    VALUES ('Lenovo IdeaPad',           'Laptop',   4399,       4000,    'Intel Core i5',          'Palit GeForce GT 1030',      '32GB 2600MHz CL15','GIGABYTE B550', 0);
INSERT INTO products(product_name, description, new_price,old_price,cpu,gpu,ram,mother_board,views) 
    VALUES ('Lenovo Y50-70',    'Laptop Gaming',   4340,      4000,    'Intel Atom',          'Matrox M9120',      '32GB 2600MHz CL15','ASRock B450', 0);

