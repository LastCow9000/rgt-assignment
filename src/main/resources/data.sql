-- Owner 유저 1명 생성
INSERT INTO user (username, password, role, created_at, updated_at)
VALUES ('owner1', '$2a$10$TXzqxhXqD/GRXcPYsJnlBeFpNSqGPxT7wPcQVOHMHg/Km2UGmIqxW', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 가게 1개 생성
INSERT INTO store (name, address, owner_id, created_at, updated_at)
VALUES ('이태리에 빠지다', '서울시 강남구 역삼동 22', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 테이블 2개 생성
INSERT INTO tables (store_id, number)
VALUES 
(1, 1),
(1, 2);

-- 메뉴 4개 생성
INSERT INTO menu (store_id, name, description, price, created_at, updated_at)
VALUES 
(1, '치즈 버거', '치즈가 듬뿍 들어간 햄버거', 12000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, '루꼴라 피자', '어디에서도 쉽게 볼 수 없는 피자', 21000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, '알리올리오', '마늘과 올리브 오일이 환상적인 맛', 11000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, '리코타 치즈 샐러드', '최고의 에피타이져', 9900, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 테이블 아이템(카트) 4개 생성
INSERT INTO table_item (tables_id, menu_id, quantity, created_at, updated_at)
VALUES 
(1, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);