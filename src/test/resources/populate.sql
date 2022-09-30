DELETE FROM CONTENT;
DELETE FROM MENU;
DELETE FROM MAIN_PAGE_IMG_URL;
DELETE FROM MAIN_PAGE_COLOR_SCHEME;
DELETE FROM MAIN_PAGE_SOCIALS;
DELETE FROM MAIN_PAGE_TEL_LIST;
DELETE FROM MAIN_PAGE;
DELETE FROM USR;

ALTER SEQUENCE glb_id_gen RESTART WITH 100000;

INSERT INTO main_page (address, email, title)
VALUES ('142701, обл. Московская, г. Видное, ул. Радужная, 10', 'adamov.m@acme-eg.ru',
        'Современные инженерные решения');

INSERT INTO main_page_img_url (main_page_id, img_url)
VALUES (1, '/img/0main/400w.jpeg'),
       (1, '/img/0main/800w.jpeg'),
       (1, '/img/0main/1500w.jpeg');

INSERT INTO main_page_color_scheme (main_page_id, color_scheme, color_scheme_key)
VALUES (1, '#0c0c0c', 'menuBg'),
       (1, '#a55c30', 'sectionTitle'),
       (1, '#232730', 'slideTitle'),
       (1, '#ffffff', 'introTitle'),
       (1, '#ffffff', 'menuColor'),
       (1, '#0c0c0c', 'footerBg');

INSERT INTO main_page_socials (main_page_id, socials, socials_key)
VALUES (1, 'mvadamov', 'telegram');

INSERT INTO main_page_tel_list (main_page_id, tel_list)
VALUES (1, '+7-910-449-05-67');

INSERT INTO menu (menu, name, queue, main_page_id)
VALUES ('o_nas', 'О нас', 1, 1),
       ('uslugi', 'Услуги', 2, 1),
       ('reshenia', 'Решения', 3, 1);

INSERT INTO usr (activation_code, active, email, first_name, last_name, psw)
VALUES (null, true, 'admin@admin.adm', 'admin', 'admin', '{noop}admin'),
       (null, true, 'user@user.usr', 'user', 'user', '{noop}user');

INSERT INTO user_role (user_id, roles)
VALUES (100000, 'USER'),
       (100000, 'ADMIN'),
       (100001, 'USER');

INSERT INTO content (menu_id, priority, active, title, content_data, author_id)
VALUES ('o_nas', 7, true, 'О компании', 'Ветер по морю гуляет
И кораблик подгоняет;
Он бежит себе в волнах
На поднятых парусах
Мимо острова крутого,
Мимо города большого:
Пушки с пристани палят,
Кораблю пристать велят.', 100000),
       ('uslugi', 4, true, 'Первая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 5, true, 'Вторая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 4, true, 'Третья услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 3, false, 'Четвёртая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 7, true, 'Пятая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 7, true, 'Шестая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('uslugi', 9, false, 'Седьмая услуга',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 1, false, 'Первое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 3, true, 'Второе решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 3, true, 'Третье решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 3, false, 'Четвёртое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 6, false, 'Пятое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 2, true, 'Шестое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 4, false, 'Седьмое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 2, true, 'Восьмое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000),
       ('reshenia', 2, true, 'Девятое решение',
        'Lorem ipsum dolor sit amet consectetur adipisicing elit. Nostrum expedita rerum minus esse voluptatum repudiandae sed corrupti, autem vel. Voluptatem iste eius natus facere dolor earum debitis rerum tempora architecto minus non facilis corrupti sunt ducimus nemo vitae quibusdam ullam aperiam ab iure, commodi omnis molestiae! Corrupti delectus tempora quo explicabo eligendi distinctio molestiae velit. Ducimus explicabo, eaque alias, quasi rem deleniti nam tempora repellat illum exercitationem dolorem? Inventore dolore voluptas molestias eius, pariatur ullam minima rerum error? Eum, ratione perferendis consequatur sint ad commodi dignissimos similique amet! Recusandae explicabo magnam voluptatum numquam quas culpa. Ipsum distinctio provident consequatur alias!',
        100000);

INSERT INTO content_img_url (content_id, img_url)
VALUES (100002, '/img/0content/400w.jpeg'),
       (100002, '/img/0content/800w.jpeg'),
       (100002, '/img/0content/1500w.jpeg'),
       (100003, '/img/0content/400w.jpeg'),
       (100003, '/img/0content/800w.jpeg'),
       (100003, '/img/0content/1500w.jpeg'),
       (100004, '/img/0content/400w.jpeg'),
       (100004, '/img/0content/800w.jpeg'),
       (100004, '/img/0content/1500w.jpeg'),
       (100005, '/img/0content/400w.jpeg'),
       (100005, '/img/0content/800w.jpeg'),
       (100005, '/img/0content/1500w.jpeg'),
       (100007, '/img/0content/400w.jpeg'),
       (100007, '/img/0content/800w.jpeg'),
       (100007, '/img/0content/1500w.jpeg'),
       (100008, '/img/0content/400w.jpeg'),
       (100008, '/img/0content/800w.jpeg'),
       (100008, '/img/0content/1500w.jpeg'),
       (100010, '/img/0content/400w.jpeg'),
       (100010, '/img/0content/800w.jpeg'),
       (100010, '/img/0content/1500w.jpeg'),
       (100011, '/img/0content/400w.jpeg'),
       (100011, '/img/0content/800w.jpeg'),
       (100011, '/img/0content/1500w.jpeg'),
       (100013, '/img/0content/400w.jpeg'),
       (100013, '/img/0content/800w.jpeg'),
       (100013, '/img/0content/1500w.jpeg'),
       (100014, '/img/0content/400w.jpeg'),
       (100014, '/img/0content/800w.jpeg'),
       (100014, '/img/0content/1500w.jpeg'),
       (100017, '/img/0content/400w.jpeg'),
       (100017, '/img/0content/800w.jpeg'),
       (100017, '/img/0content/1500w.jpeg'),
       (100018, '/img/0content/400w.jpeg'),
       (100018, '/img/0content/800w.jpeg'),
       (100018, '/img/0content/1500w.jpeg');




