This is a learning/example application

The application provides REST Api for animal products eshop. All endpoints run on HTTPS.

=====================================================

Data Model:

AnimalCategory
 + name PK

Order
 + id PK
 + user
 + totalPrice
 + products
 + time

Product
 + id PK
 + name
 + price
 + description
 + gallery
 - lowerName - it serves for the purposes of filter

User
 + id PK
 + email unique
 + username unique
 + passwordHash
 
=====================================================
 
There is a configured h2 database (schema.sql) and some sample data (data.sql)
There are 2 users with passwords defined:
 - Juraj, Weak_Password1 
 - Fero, EasyAccess2Account!
 
The password must follow safety guidlines, see sk.baric.animalshop.validation.SafePasswordValidator
 
=====================================================

Endpoints:
There are public and authorized endpoints:


 + GET /products

 optional GET params: size, page, min-price, max-price, name (name prefix)
 retrieves products overview


 + GET /products/{id}

 retrieves product details


 + POST /user

 mandatory POST params: email, username, password
 retrieves new User entity OR bad request 400


 + POST /login

 mandatory:

 HEADER Parameter Authorization: Basic {username}:{password}

 OR
 
 POST params password AND (username OR email) which allows to login via email instead of username
 retrieves authorization token in HEADER in format "Authorization: Basic {authorization_token}" OR 401
 
 
 + POST /orders?products-ids=pid_1,...,pid_n
 
 mandatory: 
 
 GET params: at least 1 Product ID (no upper bound for count)
 
 HEADER Authorization: Basic {authorization_token}
 
 submits order, returns order ID, OR returns error


 + GET /orders
 
 mandatory: HEADER Authorization: Basic {authorization_token}
 retrieves all orders of logged user

=====================================================

to Run: navigate to project root and run mvn spring-boot:run

to Debug: import existing maven project and run as Java Application the main method sk.baric.animalshop.AnimalShopApplication.main in debug mode

=====================================================

there is a SoapUI project to test the endpoints, but consider that the token is not valid any more, you have to use a fresh one after login

=====================================================

Juraj Baric, 2020