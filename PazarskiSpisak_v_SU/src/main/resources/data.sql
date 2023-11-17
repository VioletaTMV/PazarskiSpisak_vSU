--INSERT INTO users (`display_nickname`, `email`, `password`, `registered_on`)
--VALUES ('test1', '1@1.com', '7be572eb4b806e362abe1609705e1b9a5440f2bb72da48bf5a3caf486cf208e9f70c283f1cf554dac8a7cca8f76c6dbd', '2023-11-10'),
--       ('тест юзер', '2@1.com', '7be572eb4b806e362abe1609705e1b9a5440f2bb72da48bf5a3caf486cf208e9f70c283f1cf554dac8a7cca8f76c6dbd', '2023-11-10');
--
--INSERT INTO items_categories (`is_deleted`, `is_food`, `name`, `order_in_shopping_list`)
--VALUES (b'0', b'1', 'Снакс', '2'),
--        (b'0', b'1', 'Бонбони', '3'),
--        (b'0', b'0', 'Перииилни', '1'),
--         (b'0', b'1', 'Поодправки', '4');
--
--INSERT INTO ingredient_measures_values (`ingredient_id`, `alt_measure_value`, `alt_measure_unit`)
--VALUES ('1', '44', 'TEA_SPOON'),
--        ('1', '44', 'TABLE_SPOON');
--
--INSERT INTO items (`item_type`, `name`, `item_category_id`)
--VALUES ('item', 'кофаааа', '1'),
--        ('item', 'легееен', '1'),
--        ('ingredient', 'хляяяяб', 'GRAM', 'GRAM', '2'),
--        ('ingredient', 'брашнооо', 'GRAM', 'GRAM', '2'),
--         ('ingredient', 'соол', 'GRAM', 'GRAM', '4');
--
--INSERT INTO recipes (`cooking_steps`, `date_modified`, `date_published`, `is_deleted`, `is_visible`, `name`, `notes`, `servings`, `view_count`, `published_by`)
--VALUES ('Готвим готвим готвим', '2022-04-08 14:27:30.000000', '2022-04-08 14:27:30.000000', b'0', b'1', 'Ястие 1', 'бележка 1', '8', '1', '1'),
--        ('Готвим готвим готвим 2', '2022-04-08 14:27:30.000000', '2022-04-08 14:27:30.000000', b'0', b'1', 'Ястие 2', 'бележка 2', '3', '1', '1');
--
--

INSERT INTO user_user_roles(`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 3),
    (2, 1);