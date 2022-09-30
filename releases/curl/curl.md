<h3>Main page</h3>

1.  GET: /main

curl --location --request GET 'localhost:8081/main'

2. GET: /main/content

curl --location --request GET 'localhost:8081/main/content'

3. GET: /content

curl --location --request GET 'localhost:8081/content'

4. GET: /content/{id}

curl --location --request GET 'localhost:8081/content/100004'


<h3>Admin Panel</h3>


5. GET: /adminpanel/login

curl --location --request GET 'localhost:8081/adminpanel/login' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu'



6. POST: adminpanel/main

curl --location --request POST 'localhost:8081/adminpanel/main' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Современные инженерные решения",
"colorScheme": {
"sectionTitle": "#a55c30",
"menuColor": "#ffffff",
"slideTitle": "#232730",
"footerBg": "#0c0c0c",
"menuBg": "#0c0c0c",
"introTitle": "#ffffff"
},
"address": "142701, обл. Московская, г. Видное, ул. Радужная, 10",
"coordinates": [
-83.162319, 179.00001
],
"telList": [
"+7-910-449-05-67",
"+7-123-456-78-90"
],
"email": "adamov.m@acme-eg.ru",
"socials": {
"telegram": "mvadamov"
},
"newMenu": [
"Услуги",
"О нас",
"Продукты",
"Решения"],
"newImg": ""
}'


7. GET: /adminpanel/getAll

curl --location --request GET 'localhost:8081/adminpanel/getAll?page=1&size=5' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \

8. GET: /adminpanel/getLength

curl --location --request GET 'localhost:8081/adminpanel/getLength' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \

9. GET: /adminpanel/content?id=100007

curl --location --request GET 'localhost:8081/adminpanel/content?id=100007' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu'

10.1. DELETE: /adminpanel/content?id=100015

curl --location --request DELETE 'localhost:8081/adminpanel/content?id=100015' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \
--data-raw ''

10.2. DELETE: /adminpanel/content

curl --location --request DELETE 'localhost:8081/adminpanel/content' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"removeIds": [ 100027,100028, 100029, 100030, 100031, 100032]
}'

11. POST: /adminpanel/content

curl --location --request POST 'localhost:8081/adminpanel/content' \
--header 'Authorization: Basic YWRtaW5AYWRtaW4uYWRtOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": null,
"menuId": "resheniya",
"title": "New title",
"content": "NewProduct",
"priority": 4,
"show": true,
"authorId": 100000,
"newImg": ""
}'

<h3>Images</h3>

12. GET: /img/0main/400w.jpeg
    GET: /img/0main/800w.jpeg
    GET: /img/0main/1500w.jpeg

curl --location --request GET 'http://localhost:8081/img/0main/800w.jpeg'
