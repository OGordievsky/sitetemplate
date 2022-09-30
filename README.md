<h2>Before the start</h2>
Make sure you have 15 minutes or more of free time
</br>Ensure that your PC have installed Java 18
</br>And have installed Git

<h2>How to start?</h2>

* Download from Git this folder: "/Releases"
* Find last release build. Current version: <b>web-DB_216_1909.jar</b>
* Double-click on StartPort8081.bat
    * Or into <b>Releases</b> directory type next console command:<code> java -jar web_DB_216_1909.jar</code>

<h2>Postman</h2>
You find last file here:
</br>[Local Postman](Releases/Postman)
</br><a href="/Releases/Postman">GIT Postman</a>

<h2>CURL</h2>
You find last curl here:
</br>[Local CURL](Releases/curl/curl.md)
</br><a href="/Releases/curl/curl.md">GIT CURL</a>

<h2>API:</h2>

<h3>Main page</h3>

<h4>/main</h4>

1. GET: localhost:8081/main
   </br>Return: Json
   </br>{
   </br>"title": "Text",
   </br>"imgUrl": [
   </br>"/img/0main/400w.jpeg",
   </br>"/img/0main/800w.jpeg",
   </br>"/img/0main/1500w.jpeg"
   </br>],
   </br>"menu": {
   </br>"o_nas": "О нас",
   </br>"produkty": "Продукты",
   </br>"proyekty": "Проекты"
   </br>},
   </br>"colorScheme": {
   </br>}"menuColorDesktop": "#232730",
   </br>}"menuColorMobile": "#ffffff",
   </br>}"sectionOddBg": "#ffefba",
   </br>}"sectionTitle": "#a55c30",
   </br>}"slideTitle": "#232730",
   </br>}"footerBg": "#232f3e",
   </br>}"menuBg": "#0c0c0c",
   </br>}"introTitle": "#ffffff",
   </br>}"sectionEvenBg": "#ece9e6"
   </br>},
   </br>"address": "String",
   </br>"coordinates": [-90.00000, -180.00000],
   </br>"telList": [
   "String array",
   ...
   ],
   </br>"email": "String",
   </br>"socials": {
   </br>"telegram": "String",
   </br>"vk" : "String"
   </br>}
   </br>}

<h4>/main/content</h4>

2. GET: localhost:8081/main/content
   </br>Return: Json Map
   </br>{ "menuId" : 
</br> &emsp; [
</br> &emsp; &emsp; { "id": 100007,
   </br> &emsp; &emsp; "menuId": "uslugi",
   </br> &emsp; &emsp; "title": "String",
   </br> &emsp; &emsp; "content": "Long Text",
   </br> &emsp; &emsp; "imgUrl": [
   </br> &emsp; &emsp; "/img/0content/400w.jpeg",
   </br> &emsp; &emsp; "/img/0content/800w.jpeg",
   </br> &emsp; &emsp; "/img/0content/1500w.jpeg"
   </br> &emsp; &emsp; ],
   </br> &emsp; &emsp; "priority": 7,
   </br> &emsp; &emsp; "show": true,
   </br> &emsp; &emsp; "authorId": 100000
   </br> &emsp; &emsp; }
   </br> &emsp; &emsp; ...
   </br> &emsp; ]
   </br> &emsp; &emsp; ...
</br>}


<h3>Content</h3>
<h4>/content</h4>

3. GET: localhost:8081/content
   </br>Query Params (optional): ?menu=produkty
   </br>Return: Json sorted by <code>priority</code>
   </br>[
   </br>{
   </br>"id": Long,
   </br> "menuId": "transcrypt string",
   </br>"title": "String",
   </br>"content": "Long text ...",
   </br>"imgUrl": [
   </br>"/img/0content/400w.jpeg",
   </br>"/img/0content/800w.jpeg",
   </br>"/img/0content/1500w.jpeg"
   </br>],
   </br>"priority": int,
   </br>"show": boolean,
   </br>"authorId": Long,   
   }, ...
   </br>]


<h4>/content/{id}</h4>

4. GET: localhost:8081/content/{String}
   </br>Query Params (NONE): null
   </br>Return: Json
   </br>{
   </br>"id": Long,
   </br>"menuId": "Enum String",
   </br>"title": "String",
   </br>"content": "Long text ...",
   </br>"imgUrl": [
   </br>"/img/0content/400w.jpeg",
   </br>"/img/0content/800w.jpeg",
   </br>"/img/0content/1500w.jpeg"
   </br>],
   </br>"priority": int,
   </br>"show": boolean,
   </br>"authorId": Long
   </br>}

<h3>Admin panel</h3>

<h4>/adminpanel/login</h4>

5. GET: localhost:8081/adminpanel/login
   </br>IF authorised
   </br>Return: User long Id
   </br>Else
   </br>Return: 403


<h4>/adminpanel/main</h4>

6. POST: localhost:8081/adminpanel/main
</br>Body (required): Json
</br>{
</br>"title": "Text",
   </br>
   </br>_(optional, IF it contains -> Upload or set new image)_
   </br>"newImg": "String Base64 with headers" || Or empty string "",
   </br>
</br>_(optional, IF it contains -> SORTED UPDATE menu)_ 
</br>"newMenu": [
</br>"О нас",
</br>"Продукты",
</br>"Проекты"
</br>],
   </br>
</br> (required -> Set mappoint coordinate)_
</br>"coordinates": [
 </br>       -89.162319, 179.00001
 </br>   ],
   </br>
   </br>   "colorScheme": {
   </br>"sectionTitle": "#a55c30",
   </br>  "menuColor": "#ffffff",
   </br>"slideTitle": "#232730",
   </br>"footerBg": "#0c0c0c",
   </br>"menuBg": "#0c0c0c",
   </br>"introTitle": "#ffffff"
   </br>},
</br>"address": "String",
</br>"telList": [
"String array"
],
</br>"email": "String",
</br>"socials": {
</br>"telegram": "String",
</br>"vk" : "String"
</br>}
</br>}
</br>Return: this Json; status 200


<h4>/adminpanel/getAll</h4>

7. GET: localhost:8081/adminpanel/getAll
   </br>Query Params (Optional, return elements by page): 
   </br> <code>page=1</code>
   </br> <code>size=5</code>
   </br>Return: Json
   </br>[
   </br>{
   </br>"id": Long,
   </br>"menuId" : "transcrypt string",
   </br>"title": "String",
   </br>"imgUrl": [
   </br>"/img/0content/400w.jpeg",
   </br>"/img/0content/800w.jpeg",
   </br>"/img/0content/1500w.jpeg"
   </br>],
   </br>"priority": int,
   </br>"show": boolean,
   </br>"authorId": Long,
   </br>"dateTime": "2022-07-30T18:22:19.7379272+03:00"
   }, ...
   </br>]

<h4>/adminpanel/getLength</h4>

8. GET: localhost:8081/getLength
</br>Return: Int content list size


<h4>/adminpanel/content</h4>

9. GET: localhost:8081/adminpanel/content?id=100007
   </br>Query Params (required): ?id=1
   </br>Return: Json
   </br>{
   </br>"id": Long,
   </br>"menuId": "transcrypt string",
   </br>"title": "String",
   </br>"content": "Long text ...",
   </br>"imgUrl": [
   </br>"/img/0content/400w.jpeg",
   </br>"/img/0content/800w.jpeg",
   </br>"/img/0content/1500w.jpeg"
   </br>],
   </br>"priority": int,
   </br>"show": boolean,
   </br>"authorId": Long
   </br>}


10. DELETE: localhost:8081/adminpanel/content?id=100015
   </br>IF Query Params (optional): ?id=100015
   </br>Return: ~~200~~ -> 204 NO CONTENT
   </br>IF <b>JSON</b> Body (optional) have 
</br>{ 
</br>~~"remove_id"~~ -> "removeIds" : [Long, ...]
</br>}
   </br>Return: 200 OK ; Deleted: + int numbers of delete
   </br>Else: 404 NOT FOUND


11. POST: localhost:8081/adminpanel/content
   </br>Body (required): Json
   </br>{
   </br>"id": Long, (IF = null; CREATE)
   </br>"menu": "transcrypt string",
   </br>"title": "String",
   </br>"content": "Long text ...",
   ~~</br>"imgUrl": [
   </br>"/img/0content/400w.jpeg",
   </br>"/img/0content/800w.jpeg",
   </br>"/img/0content/1500w.jpeg"
   </br>]~~
    </br>_(optional, IF it contains -> Upload or set new image)_
    </br>"newImg": "String Base64 with headers" || Or empty string "",
   </br>"priority": int,
   </br>"show": boolean,
   </br>"authorId": Long
   </br>}
   </br>If POST correct return: this Json; status 200
   </br>Return errors:
   </br>422 UNPROCESSABLE_ENTITY (incorrect <b>Body</b> construction)
   

<h3>/img/...</h3>

12. GET: localhost:8081http:/localhost:8081/img/0main/800w.jpeg
   </br>Query Params (NONE): null
   </br>Return: image/jpeg
   </br> real image from server directory: /media/img

