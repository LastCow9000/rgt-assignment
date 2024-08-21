# RGT-Assignment

## ERD

[ERD Link](https://www.erdcloud.com/d/iumcj35JuTadAReor)

![erd_rgt_assignment](https://github.com/user-attachments/assets/fa7af9ad-a61e-4fb3-8cb8-36109e973535)

## API Docs

<details>
<summary>접기/펼치기</summary>

### 손님 사용자가 플랫폼에 로그인하기 위한 API

- POST /login
- 손님 사용자가 로그인을 합니다.
- Body
  - id: [아이디]
  - password: [비밀번호]
  - ex) { "id": "rgtGo", "password": "1q2w3e!"}
- Response

```json
## Headers
'Authorization': Bearer [access-token]
'Set-Cookie': REFRESH=[refresh-token]
## Body
{
  "message": [로그인 성공 여부]
}
```

- 응답 예시)

```json
## Headers
'Authorization': Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiYWNjZXNzIiwidXNlcm5hbWUiOiJsYXN0Y293Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE3MjQxMzczMjYsImV4cCI6MTcyNDEzNzkyNn0.QGV4lcJ1SXRyGHPpELCVc10iToceuyTAvxZjVGCp25Y
'Set-Cookie': REFRESH=eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoicmVmcmVzaCIsInVzZXJuYW1lIjoibGFzdGNvdyIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzI0MTM3MjUxLCJleHAiOjE3MjQyMjM2NTF9.2isr2Po-9rkfpsf8o3lroVyRmJwMioxzyQ6ciKHjrl4; Max-Age=86400; Expires=Wed, 21 Aug 2024 07: 00: 51 GMT; HttpOnly

## Body
{
  "message": "OK"
}
```

### 손님 사용자가 식당의 메뉴 리스트를 확인하기 위한 API

- GET api/v1/stores/:storeId/menus
- 식당의 메뉴 리스트를 확인합니다.
- Parameters
  - storeId(path): [매장 아이디]
  - page(query, optional): [페이지 번호, 기본값 1]
  - limit(query, optional): [페이지당 항목 수, 기본값 10]
  - ex) /stores/56/menus?page=1&limit=10
- Response

```json
{
  "menus": [
    {
      "id": [메뉴 아이디],
      "name": [메뉴명],
      "price": [메뉴 가격]
    }
  ],
  "totalPages": [총 페이지],
  "currentPage": [현재 페이지]
}
```

- 응답 예시)

```json
{
  "menus": [
    {
      "id": 12,
      "name": "루꼴라 피자",
      "price": 12000
    },
    {
      "id": 3,
      "name": "리코타 치즈 샐러드",
      "price": 9900
    }
  ],
  "totalPages": 5,
  "currentPage": 1
}
```

### 손님 사용자가 식당의 메뉴의 상세 정보를 확인하기 위한 API

- GET api/v1/stores/:storeId/menus/:menuId
- 식당 메뉴의 상세 정보를 확인합니다.
- Parameters
  - storeId(path): [매장 아이디]
  - menuId(path): [메뉴 아이디]
  - ex) /stores/56/menus/23
- Response

```json
{
  "id": [메뉴 아이디],
  "name": [메뉴명],
  "price": [메뉴 가격],
  "description": [메뉴 설명]
}
```

- 응답 예시)

```json
{
  "id": 3,
  "name": "리코타 치즈 샐러드",
  "price": 9900,
  "description": "최고의 에피타이져"
}
```

### 손님 사용자가 메뉴를 장바구니에 담기 위한 API

- POST api/v1/tables/:tableId/menus
- 메뉴를 장바구니에 담습니다.
- Parameters
  - tableId(path): [테이블 아이디]
  - ex) /tables/12/menus
- Body
  - menuId: [메뉴 아이디]
  - quantity: [수량]
  - ex) { "menuId": "452", "quantity": 1 }
- Response

```json
{
  "tableItems": [
    {
      "menuId": [메뉴 아이디],
      "name": [메뉴명],
      "price": [메뉴 가격],
      "quantity": [메뉴 수량]
    }
  ],
  "totalPrice": [총 가격]
}
```

- 응답 예시)

```json
{
  "tableItems": [
    {
      "menuId": 12,
      "name": "루꼴라 피자",
      "price": 12000,
      "quantity": 2
    },
    {
      "menuId": 23,
      "name": "리코타 치즈 샐러드",
      "price": 9900,
      "quantity": 3
    }
  ],
  "totalPrice": 41700
}
```

### 손님 사용자가 장바구니에 담긴 메뉴를 수정하기 위한 API

- PATCH api/v1/tables/:tableId/menus/:menuId
- 장바구니에 담긴 메뉴를 수정합니다.
- Parameters
  - tableId(path): [테이블 아이디]
  - menuId(path): [메뉴 아이디]
  - ex) /tables/12/menus/452
- Body
  - quantity: [수정할 수량]
  - ex) { "quantity": 2 }
- Response

```json
{
  "tableItems": [
    {
      "menuId": [메뉴 아이디],
      "name": [메뉴명],
      "price": [메뉴 가격],
      "quantity": [메뉴 수량]
    }
  ],
  "totalPrice": [총 가격]
}
```

- 응답 예시)

```json
{
  "tableItems": [
    {
      "menuId": 12,
      "name": "루꼴라 피자",
      "price": 12000,
      "quantity": 2
    },
    {
      "menuId": 23,
      "name": "리코타 치즈 샐러드",
      "price": 9900,
      "quantity": 2
    }
  ],
  "totalPrice": 31800
}
```

### 손님 사용자가 장바구니에 있는 메뉴들을 주문하기 위한 API

- POST api/v1/tables/:tableId/orders
- 장바구니에 있는 메뉴들을 주문합니다.
- Parameters
  - tableId(path): [테이블 아이디]
  - ex) /tables/12/orders
- Response

```json
{
  "orderId": [주문 아아디],
  "menus": [
    {
      "menuId": [메뉴 아이디],
      "name": [메뉴명],
      "price": [메뉴 가격],
      "quantity": [메뉴 수량]
    }
  ],
  "totalPrice": [총 가격],
  "status": [주문 상태]
}
```

- 응답 예시)

```json
{
  "orderId": 1,
  "menus": [
    {
      "menuId": 1,
      "name": "치즈 버거",
      "price": 12000,
      "quantity": 3
    },
    {
      "menuId": 2,
      "name": "루꼴라 피자",
      "price": 21000,
      "quantity": 3
    }
  ],
  "totalPrice": 99000,
  "status": "CONFIRMED"
}
```

### 손님 사용자가 주문한 내역을 확인하기 위한 API

- GET api/v1/tables/:tableId/orders
- 손님 사용자가 주문 내역을 확인합니다.
- Parameters
  - tableId(path): [테이블 아이디]
  - ex) /tables/12/orders
- Response

```json
{
  "orders": [
    {
      "orderId": [주문 아이디],
      "menus": [
        {
          "menuId": [메뉴 아이디],
          "name": [메뉴명],
          "price": [메뉴 가격],
          "quantity": [메뉴 수량]
        }
      ],
      "totalPrice": [총 가격],
      "status": [주문 상태],
      "createdAt": [주문 일시]
    }
  ]
}
```

- 응답 예시)

```json
{
  "orders": [
    {
      "orderId": 1,
      "menus": [
          {
              "menuId": 1,
              "name": "치즈 버거",
              "price": 12000,
              "quantity": 3
          },
          {
              "menuId": 2,
              "name": "루꼴라 피자",
              "price": 21000,
              "quantity": 3
          }
      ],
      "totalPrice": 99000,
      "status": "CONFIRMED"
      "createdAt": "2024-08-21 19:24:33"
    }
  ]
}
```

## 기능 구현을 위해 따로 제작한 api

### 회원가입

- POST api/v1/users
- 회원 가입을 합니다.
- Body
  - id: [회원 아이디],
  - password: [비밀번호]
  - ex) {id: "testUser", password:"1234"}
- Reponse

```json
{
  "message": [회원가입 성공 여부]
}
```

- 응답 예시)

```json
{
  "message": "SUCCESS"
}
```

### 액세스 토큰 재발급

- POST /reissue
- 만료된 액세스 토큰을 재발급 받습니다.
- Headers
  - Authorization: Bearer [만료된 액세스 토큰]
  - ex) Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiUkVGUkVTSCIsInVzZXJuYW1lIjoibGFzdGNvdyIsInJvbGUiOiJDVVNUT01FUiIsImlhdCI6MTcyNDIzNDU1NSwiZXhwIjoxNzI0MzIwOTU1fQ.5cYlJ1wrvqszLmrhF6f8DuSEJVmwKGsIo7ljkOHwWgQ
- Cookie
  - REFRESH=[리프레시 토큰]
  - ex) REFRESH=eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiUkVGUkVTSCIsInVzZXJuYW1lIjoibGFzdGNvdyIsInJvbGUiOiJDVVNUT01FUiIsImlhdCI6MTcyNDIzNDU1NSwiZXhwIjoxNzI0MzIwOTU1fQ.5cYlJ1wrvqszLmrhF6f8DuSEJVmwKGsIo7ljkOHwWgQ
- Reponse

```json
{
  "message": [재발급 성공 여부]
}
```

- 응답 예시)

```json
{
  "message": "SUCCESS"
}
```

  </details>

## 구동 방법

1. 아래의 명령어를 쉘에 입력합니다.

```bash
$ git clone https://github.com/LastCow9000/rgt-assignment.git

$ git checkout develop
```

2. ide로 프로젝트를 연 후 의존성을 받고 실행합니다.
3. 회원가입을 위해 postman류의 도구로 다음의 주소와 request body를 담아 요청을 보냅니다.

- 주소 : `POST http://localhost:8090/api/v1/users`

```json
// 데이터 예시
{
  "id": "rgt",
  "password": "1234"
}
```

4. 로그인을 위해 postman류의 도구로 다음의 주소와 위에서 입력했던 데이터를 그대로 request body에 담아 요청을 보냅니다.

- 주소 : `POST http://localhost:8090/login`

```json
{
  "id": "rgt",
  "password": "1234"
}
```

5. 응답의 `Authorization` 헤더에 담겨져 있는 access token을 복사합니다.
6. 장바구니에 담긴 주문을 하기 위해 요청 `Authorization` 헤더에 Bearer {access token} 을 넣고 다음의 주소로 요청을 보냅니다.

- 주소: `POST http://localhost:8090/api/v1/tables/:tablesId/orders`
- 테이블id 1과 테이블id 2에 더미 데이터가 들어가 있으므로 `api/v1/tables/1/orders`과 `api/v1/tables/2/orders`에 요청을 날려볼 수 있습니다.

```text
헤더 예시)
Authorization 헤더: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiQUNDRVNTIiwidXNlcm5hbWUiOiJsYXN0Y293Iiwicm9sZSI6IkNVU1RPTUVSIiwiaWF0IjoxNzI0MTc1MjQ0LCJleHAiOjE3MjQxNzU4NDR9.IA8a7eYqTOSoBdEsu4--_k1dtP49by6J1rhNKlgSDYc
```

7. 만약 액세스 토큰이 만료되면(10분) 요청 `Authorization` 헤더에 Bearer {access token} 을 넣고 다음의 주소로 요청을 보내서 access token을 재발급 받을 수 있습니다. (
   refresh token은 쿠키에 자동으로 담겨져 있습니다.)

- 주소: `POST http://localhost:8090/reissue`

8. 다시 주문 api 테스트 하기를 원하신다면 서버를 재시작하고 2번부터 진행하시면 됩니다.

## 진행 내용

- 로그인 기능을 위해 회원 가입 기능을 구현했습니다.
- 로그인 기능은 spring security를 이용하여 jwt 토큰 방식으로 구현했습니다.
- 로그인 성공 시 access token과 refresh token을 발급해주는 기능을 구현했습니다.
  - access token은 10분간 유효하고, refresh token은 24시간 유효합니다.
  - access token은 Authrozation 헤더에 Bearer {access token} 으로 발급됩니다.
  - refresh token은 Cookie에 자동으로 담겨져서 발급되며, Cookie의 이름은 REFRESH입니다.
- access token 만료시에는 refresh token을 통해 새로운 access token을 발급해주는 기능을 구현했습니다.
- 장바구니에 담긴 주문 api 의 테스트를 위해 spring boot 시작 시 더미 데이터를 넣도록 했습니다. (resource/data.sql)
  - 1번 장바구니와 2번 장바구니에 데이터가 들어가 있어 두 번의 테스트가 가능합니다.
- DB 테이블 설계 시 굳이 cart 테이블이 필요 없다고 판단하여 tables 테이블을 가게의 테이블이자 장바구니로 사용하도록 설계했습니다.
