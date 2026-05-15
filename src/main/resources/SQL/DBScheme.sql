
--Brand
CREATE TABLE Brand(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description VARCHAR(512) NOT NULL
);
--Category
CREATE TABLE Category(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);
--HealthFunctions
CREATE TABLE HealthFunction(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);
--OperatingSystem
CREATE TABLE OperatingSystem(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(255) NOT NULL,
    developer VARCHAR(255) NOT NULL
);

--SmartWatch
CREATE TABLE SmartWatch(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    year_of_making INT,
    screen_size DOUBLE PRECISION,
    battery_life int,
    ip_rating VARCHAR(50),
    price Decimal(10, 2), --1st num Total amount of numbers, 2nd num Number of decimals
    image_path VARCHAR(512),

    -- Foreign keys
    brand_id INT,
    category_id INT,
    operating_system_id INT,

    -- Table relations
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brand(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Category(id),
    CONSTRAINT fk_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);




-- M : N table between smartwatch and health function
CREATE TABLE SmartWatchHealthFunction(
    id INT PRIMARY KEY AUTO_INCREMENT,
    smart_watch_id INT,
    health_function_id INT,

    CONSTRAINT fk_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_health_function FOREIGN KEY (health_function_id) REFERENCES HealthFunction(id)
);


-- M : N table between smartwatch and operating system
CREATE TABLE CompatibleOsTypes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    smart_watch_id INT,
    operating_system_id INT,

    CONSTRAINT fk_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);