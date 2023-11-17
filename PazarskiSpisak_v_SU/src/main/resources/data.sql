INSERT INTO users (`display_nickname`, `email`, `is_deleted`, `legacy_id`, `password`, `registered_on`)
VALUES ('testUser', 'test@test.com', b'0', '5', '501c32ba9676733eae5236cf5ee7084e85ef7a3535c769d1dd6683f2d5d48eb42eaac31250693e30732a2e2151969304', '2023-11-17'),
       ('testUser2', 'test2@test.com', b'0', '6', '685d132a7aa641663cfb4a14a5d9072bd167604615872993bc0bda45755a79973ab860d8aea7f0575cbea5c4abf20682', '2023-11-17');


--INSERT INTO user_user_roles(`user_id`, `role_id`)
--VALUES
--    (1, 1),
--    (1, 3),
--    (2, 1);