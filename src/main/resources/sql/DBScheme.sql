CREATE TABLE [User](
    id INT PRIMARY KEY IDENTITY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL,
    phone_number NVARCHAR(20) NULL,
    password_hash VARCHAR(60) NOT NULL,
    [Role] VARCHAR(20) NOT NULL,

    CONSTRAINT CHK_User_Role CHECK([Role] IN ('USER','ADMIN'))
);

CREATE TABLE Brand(
    id INT PRIMARY KEY IDENTITY ,
    name NVARCHAR(255) NOT NULL,
    country NVARCHAR(255) NOT NULL,
    description NVARCHAR(512) NOT NULL
);

CREATE TABLE Category(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL
);

CREATE TABLE HealthFunction(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(255) NOT NULL
);

CREATE TABLE OperatingSystem(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    version NVARCHAR(255) NOT NULL,
    developer NVARCHAR(255) NOT NULL
);


CREATE TABLE SmartWatch(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    year_of_making INT,
    screen_size DOUBLE PRECISION,
    battery_life int,
    ip_rating NVARCHAR(50),
    price Decimal(10, 2),
    image_path NVARCHAR(512),


    brand_id INT,
    category_id INT,
    operating_system_id INT,


    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brand(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Category(id),
    CONSTRAINT fk_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);





CREATE TABLE SmartWatchHealthFunction(
    id INT PRIMARY KEY IDENTITY,
    smart_watch_id INT,
    health_function_id INT,

    CONSTRAINT fk_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_health_function FOREIGN KEY (health_function_id) REFERENCES HealthFunction(id)
);



CREATE TABLE CompatibleOsTypes(
    id INT PRIMARY KEY IDENTITY,
    smart_watch_id INT,
    operating_system_id INT,

    CONSTRAINT fk_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);