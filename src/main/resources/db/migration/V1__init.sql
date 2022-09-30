CREATE SEQUENCE glb_id_gen START 100000 INCREMENT 1;
CREATE TABLE content
(
    id           INT8 PRIMARY KEY NOT NULL DEFAULT nextval('glb_id_gen'),
    menu_id      VARCHAR(255) DEFAULT null,
    creation     TIMESTAMP with time zone  DEFAULT now(),
    title        VARCHAR(255)     not null,
    content_data TEXT             NOT NULL,
    priority     INT4             NOT NULL CHECK (priority >= 0 AND priority <= 10),
    active       BOOLEAN          NOT NULL,
    author_id    INT8
);

CREATE TABLE content_img_url
(
    content_id INT8         NOT NULL,
    img_url    VARCHAR(255) NOT NULL
);

CREATE TABLE main_page
(
    id      INT8         NOT NULL PRIMARY KEY DEFAULT 1,
    address VARCHAR(255) NOT NULL,
    email   VARCHAR(255),
    title   VARCHAR(255) NOT NULL
);

CREATE TABLE main_page_img_url
(
    main_page_id INT8         NOT NULL,
    img_url      VARCHAR(255) NOT NULL
);

CREATE TABLE main_page_color_scheme
(
    main_page_id     INT8         NOT NULL,
    color_scheme     VARCHAR(255),
    color_scheme_key VARCHAR(255) NOT NULL,
    PRIMARY KEY (main_page_id, color_scheme_key)
);


CREATE TABLE main_page_socials
(
    main_page_id INT8         NOT NULL,
    socials      VARCHAR(255),
    socials_key  VARCHAR(255) NOT NULL,
    PRIMARY KEY (main_page_id, socials_key)
);

CREATE TABLE main_page_tel_list
(
    main_page_id INT8 NOT NULL,
    tel_list     VARCHAR(255)
);

CREATE TABLE menu
(
    menu         varchar(255) UNIQUE NOT NULL PRIMARY KEY,
    name         varchar(255),
    queue        INT4,
    main_page_id INT8 NOT NULL DEFAULT 1
);


CREATE TABLE user_role
(
    user_id INT8 not null,
    roles   VARCHAR(255)
);

create table usr
(
    id              INT8 NOT NULL PRIMARY KEY DEFAULT nextval('glb_id_gen'),
    activation_code VARCHAR(255),
    active          BOOLEAN NOT NULL ,
    email           VARCHAR(255),
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    psw             VARCHAR(255)
);



ALTER TABLE IF EXISTS content ADD CONSTRAINT FK_content_author FOREIGN KEY (author_id) REFERENCES usr (id);
ALTER TABLE IF EXISTS content ADD CONSTRAINT FK_content_menu FOREIGN KEY (menu_id) REFERENCES menu (menu) ON DELETE SET NULL ;
ALTER TABLE IF EXISTS content_img_url ADD CONSTRAINT FK_images_content FOREIGN KEY (content_id) REFERENCES content (id) ON DELETE CASCADE;

ALTER TABLE IF EXISTS main_page_img_url ADD CONSTRAINT FK_images_main FOREIGN KEY (main_page_id) REFERENCES main_page (id);
ALTER TABLE IF EXISTS main_page_color_scheme ADD CONSTRAINT FK_colors_main FOREIGN KEY (main_page_id) REFERENCES main_page (id);
ALTER TABLE IF EXISTS main_page_tel_list ADD CONSTRAINT FK_tel_main FOREIGN KEY (main_page_id) REFERENCES main_page (id);
ALTER TABLE IF EXISTS main_page_socials ADD CONSTRAINT FK_socials_main FOREIGN KEY (main_page_id) REFERENCES main_page (id);

ALTER TABLE IF EXISTS menu ADD CONSTRAINT FK_menu_main FOREIGN KEY (main_page_id) REFERENCES main_page(id) ON UPDATE CASCADE;
ALTER TABLE IF EXISTS user_role ADD CONSTRAINT FK_user_role FOREIGN KEY (user_id) references usr(id) ON DELETE CASCADE;