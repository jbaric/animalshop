create table ANIMAL_CATEGORY
(
    NAME VARCHAR not null,
    constraint ANIMAL_CATEGORY_PK
        primary key (NAME)
);

create table PRODUCT
(
    ID INT auto_increment,
    NAME VARCHAR not null,
    PRICE DECIMAL not null,
    DESCRIPTION VARCHAR not null,
    LOWER_NAME VARCHAR not null,
    constraint PRODUCT_PK
        primary key (ID)
);

create table PRODUCT_CATEGORY
(
	
    PRODUCT_ID INT,
    CATEGORY_NAME VARCHAR not null,
    constraint PRODUCT_CATEGORY_PRODUCT_ID_FK
        foreign key (PRODUCT_ID) references PRODUCT (ID),
    constraint PRODUCT_CATEGORY_ANIMAL_CATEGORY_NAME_FK
        foreign key (CATEGORY_NAME) references ANIMAL_CATEGORY (NAME)
);

create table PRODUCT_GALLERY
(
	PRODUCT_ID INT,
	URL VARCHAR,
    constraint PRODUCT_GALLERY_PRODUCT_ID_FK
        foreign key (PRODUCT_ID) references PRODUCT (ID)
);

create table USER
(
    ID INT auto_increment,
    USERNAME VARCHAR not null,
    EMAIL VARCHAR not null,
    PASSWORD_HASH VARCHAR not null,
    constraint USER_PK
        primary key (ID),
    constraint USERNAME_UNIQUE
        unique (USERNAME),
    constraint USEREMAIL_UNIQUE
        unique (EMAIL)
);
    
create table ORDERING
(
    ID INT auto_increment,
	USER_ID INT, 
	TOTAL_PRICE DECIMAL not null,
	CREATED_TIME TIMESTAMP,
    constraint ORDERING_PK
        primary key (ID),
    constraint ORDERING_USER_ID_FK
        foreign key (USER_ID) references USER (ID)

);

create table ORDERING_PRODUCTS
(
	ORDERING_ID INT, 
	PRODUCTS_ID INT,
    constraint ORDERING_PRODUCTS_ORDERING_FK
        foreign key (ORDERING_ID) references ORDERING (ID),
    constraint ORDERING_PRODUCTS_PRODUCTS_FK
        foreign key (PRODUCTS_ID) references PRODUCT (ID)
);
	