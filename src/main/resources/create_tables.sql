CREATE TABLE Call
(
    call_id              SERIAL NOT NULL,
    client_id            BIGINT NOT NULL,
    subscriber_number    VARCHAR(20) NOT NULL,
    call_duration        FLOAT NOT NULL
);



ALTER TABLE Call
    ADD PRIMARY KEY (call_id);



CREATE TABLE "Check"
(
    product_id           BIGINT NOT NULL,
    product_count        INTEGER NOT NULL,
    check_id             BIGINT NOT NULL,
    client_id            BIGINT NOT NULL
);



ALTER TABLE "Check"
    ADD PRIMARY KEY (product_id,check_id);



CREATE TABLE Client
(
    client_id            SERIAL NOT NULL,
    passport_id          BIGINT NOT NULL,
    first_name           VARCHAR(32) NOT NULL,
    last_name            VARCHAR(32) NOT NULL,
    contact_id           BIGINT NOT NULL
);



ALTER TABLE Client
    ADD PRIMARY KEY (client_id);


CREATE TABLE Role
(
    role_id SERIAL NOT NULL,
    role_name VARCHAR(255),
    description VARCHAR(255)
);

ALTER TABLE Role
    ADD PRIMARY KEY (role_id);

CREATE TABLE Client_Contact
(
    contact_id           SERIAL NOT NULL,
    number               VARCHAR(20) NOT NULL,
    type                 VARCHAR(20) NULL
);



ALTER TABLE Client_Contact
    ADD PRIMARY KEY (contact_id);



CREATE TABLE Client_Device
(
    device_id            SERIAL NOT NULL,
    model                VARCHAR(255) NOT NULL,
    client_problem       VARCHAR(255) NOT NULL
);



ALTER TABLE Client_Device
    ADD PRIMARY KEY (device_id);



CREATE TABLE Client_Passport
(
    passport_id          SERIAL NOT NULL,
    series               VARCHAR(4) NOT NULL,
    number               VARCHAR(6) NOT NULL,
    birthday             DATE NOT NULL
);



ALTER TABLE Client_Passport
    ADD PRIMARY KEY (passport_id);



CREATE TABLE Communication_Salon
(
    salon_id             SERIAL NOT NULL,
    address              VARCHAR(128) NOT NULL,
    employee_number      INTEGER NULL
);



ALTER TABLE Communication_Salon
    ADD PRIMARY KEY (salon_id);



CREATE TABLE Contract
(
    contract_id          SERIAL NOT NULL,
    plan_id              BIGINT NOT NULL,
    client_id            BIGINT NOT NULL,
    date                 DATE NOT NULL
);



ALTER TABLE Contract
    ADD PRIMARY KEY (contract_id);



CREATE TABLE Department
(
    department_id        SERIAL NOT NULL,
    department_name      VARCHAR(128) NOT NULL,
    start_time           TIME NULL,
    end_time             TIME NULL
);



ALTER TABLE Department
    ADD PRIMARY KEY (department_id);



CREATE TABLE Employee
(
    employee_id          SERIAL NOT NULL,
    department_id        BIGINT NOT NULL,
    salon_id             BIGINT NOT NULL,
    first_name           VARCHAR(128) NOT NULL,
    last_name            VARCHAR(128) NOT NULL,
    post_id              BIGINT NOT NULL,
    passport_id          BIGINT NOT NULL,
    contact_id           INTEGER NOT NULL
);



ALTER TABLE Employee
    ADD PRIMARY KEY (employee_id);



CREATE TABLE Employee_Contact
(
    contact_id           SERIAL NOT NULL,
    work_number          VARCHAR(255) NOT NULL,
    personal_number      VARCHAR(255) NOT NULL
);



ALTER TABLE Employee_Contact
    ADD PRIMARY KEY (contact_id);



CREATE TABLE Employee_Passport
(
    passport_id          SERIAL NOT NULL,
    series               VARCHAR(4) NOT NULL,
    number               VARCHAR(6) NOT NULL,
    birthday             DATE NOT NULL,
    issue_date           DATE NOT NULL,
    place_code           VARCHAR(10) NOT NULL
);



ALTER TABLE Employee_Passport
    ADD PRIMARY KEY (passport_id);



CREATE TABLE Extra_Service
(
    service_id           SERIAL NOT NULL,
    service_description  TEXT NOT NULL,
    price                FLOAT NOT NULL,
    service_name         VARCHAR(32) NOT NULL,
    category_id          INTEGER NOT NULL
);



ALTER TABLE Extra_Service
    ADD PRIMARY KEY (service_id);



CREATE TABLE "Order"
(
    service_id           BIGINT NOT NULL,
    employee_id          BIGINT NOT NULL,
    client_id            BIGINT NOT NULL,
    order_id             SERIAL NOT NULL,
    status_id            CHAR(18) NOT NULL,
    device_id            INTEGER NOT NULL,
    comment              VARCHAR(255) NOT NULL
);



ALTER TABLE "Order"
    ADD PRIMARY KEY (service_id,order_id);



CREATE TABLE Post
(
    post_id              SERIAL NOT NULL,
    post_name            VARCHAR(128) NOT NULL,
    post_description     TEXT NULL
);



ALTER TABLE Post
    ADD PRIMARY KEY (post_id);



CREATE TABLE Product
(
    product_id           SERIAL NOT NULL,
    price                FLOAT NOT NULL,
    product_description  TEXT NULL,
    product_name         VARCHAR(32) NOT NULL,
    category_id          INTEGER NOT NULL,
    count                INTEGER NOT NULL
);



ALTER TABLE Product
    ADD PRIMARY KEY (product_id);



CREATE TABLE Product_Category
(
    category_id          SERIAL NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    description          VARCHAR(255) NOT NULL
);



ALTER TABLE Product_Category
    ADD PRIMARY KEY (category_id);



CREATE TABLE Service_Category
(
    category_id          SERIAL NOT NULL,
    name                 VARCHAR(255) NULL,
    difficulty           VARCHAR(255) NOT NULL,
    description          VARCHAR(255) NOT NULL
);



ALTER TABLE Service_Category
    ADD PRIMARY KEY (category_id);



CREATE TABLE Status
(
    status_id            SERIAL NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    description          VARCHAR(255) NOT NULL
);



ALTER TABLE Status
    ADD PRIMARY KEY (status_id);



CREATE TABLE Tariff_Plan
(
    plan_id              SERIAL NOT NULL,
    plan_name            VARCHAR(32) NOT NULL,
    call_minutes         INTEGER NOT NULL,
    internet_gb          INTEGER NOT NULL,
    sms_number           INTEGER NOT NULL,
    price                INTEGER NOT NULL
);



ALTER TABLE Tariff_Plan
    ADD PRIMARY KEY (plan_id);




ALTER TABLE Call
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE "Check"
    ADD FOREIGN KEY (product_id) REFERENCES Product (product_id);



ALTER TABLE "Check"
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE Client
    ADD FOREIGN KEY (passport_id) REFERENCES Client_Passport (passport_id);


ALTER TABLE Employee
    ADD FOREIGN KEY (role_id) REFERENCES Role (role_id);


ALTER TABLE Client
    ADD FOREIGN KEY (contact_id) REFERENCES Client_Contact (contact_id);



ALTER TABLE Contract
    ADD FOREIGN KEY (plan_id) REFERENCES Tariff_Plan (plan_id);



ALTER TABLE Contract
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (department_id) REFERENCES Department (department_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (salon_id) REFERENCES Communication_Salon (salon_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (post_id) REFERENCES Post (post_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (passport_id) REFERENCES Employee_Passport (passport_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (contact_id) REFERENCES Employee_Contact (contact_id);



ALTER TABLE Extra_Service
    ADD FOREIGN KEY (category_id) REFERENCES Service_Category (category_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (service_id) REFERENCES Extra_Service (service_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (employee_id) REFERENCES Employee (employee_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (status_id) REFERENCES Status (status_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (device_id) REFERENCES Client_Device (device_id);



ALTER TABLE Product
    ADD FOREIGN KEY (category_id) REFERENCES Product_Category (category_id);

ALTER TABLE Call
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE "Check"
    ADD FOREIGN KEY (product_id) REFERENCES Product (product_id);



ALTER TABLE "Check"
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE Client
    ADD FOREIGN KEY (passport_id) REFERENCES Client_Passport (passport_id);



ALTER TABLE Client
    ADD FOREIGN KEY (contact_id) REFERENCES Client_Contact (contact_id);



ALTER TABLE Contract
    ADD FOREIGN KEY (plan_id) REFERENCES Tariff_Plan (plan_id);



ALTER TABLE Contract
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (department_id) REFERENCES Department (department_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (salon_id) REFERENCES Communication_Salon (salon_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (post_id) REFERENCES Post (post_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (passport_id) REFERENCES Employee_Passport (passport_id);



ALTER TABLE Employee
    ADD FOREIGN KEY (contact_id) REFERENCES Employee_Contact (contact_id);



ALTER TABLE Extra_Service
    ADD FOREIGN KEY (category_id) REFERENCES Service_Category (category_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (service_id) REFERENCES Extra_Service (service_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (employee_id) REFERENCES Employee (employee_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (client_id) REFERENCES Client (client_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (status_id) REFERENCES Status (status_id);



ALTER TABLE "Order"
    ADD FOREIGN KEY (device_id) REFERENCES Client_Device (device_id);



ALTER TABLE Product
    ADD FOREIGN KEY (category_id) REFERENCES Product_Category (category_id);
