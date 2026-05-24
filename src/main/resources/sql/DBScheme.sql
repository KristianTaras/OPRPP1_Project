IF OBJECT_ID(N'[User]', N'U') IS NULL
CREATE TABLE [User](
    id INT PRIMARY KEY IDENTITY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    phone_number NVARCHAR(20) NULL,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(72) NOT NULL,
    [Role] VARCHAR(20) NOT NULL,

    CONSTRAINT CHK_User_Role CHECK([Role] IN ('USER','ADMIN'))
);

IF OBJECT_ID(N'Brand', N'U') IS NULL
CREATE TABLE Brand(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    country NVARCHAR(255) NOT NULL,
    description NVARCHAR(512) NOT NULL
);

IF OBJECT_ID(N'Category', N'U') IS NULL
CREATE TABLE Category(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL
);

IF OBJECT_ID(N'HealthFunction', N'U') IS NULL
CREATE TABLE HealthFunction(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(255) NOT NULL
);

IF OBJECT_ID(N'OperatingSystem', N'U') IS NULL
CREATE TABLE OperatingSystem(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    version NVARCHAR(255) NOT NULL,
    developer NVARCHAR(255) NOT NULL
);

IF OBJECT_ID(N'SmartWatch', N'U') IS NULL
CREATE TABLE SmartWatch(
    id INT PRIMARY KEY IDENTITY,
    name NVARCHAR(255) NOT NULL,
    year_of_making INT,
    screen_size FLOAT,
    battery_life INT,
    ip_rating NVARCHAR(10),
    price DECIMAL(10, 2),
    image_path NVARCHAR(512),
    brand_id INT,
    category_id INT,
    operating_system_id INT,

    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brand(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Category(id),
    CONSTRAINT fk_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);

IF OBJECT_ID(N'SmartWatchHealthFunction', N'U') IS NULL
CREATE TABLE SmartWatchHealthFunction(
    id INT PRIMARY KEY IDENTITY,
    smart_watch_id INT,
    health_function_id INT,

    CONSTRAINT fk_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_health_function FOREIGN KEY (health_function_id) REFERENCES HealthFunction(id)
);

IF OBJECT_ID(N'CompatibleOsTypes', N'U') IS NULL
CREATE TABLE CompatibleOsTypes(
    id INT PRIMARY KEY IDENTITY,
    smart_watch_id INT,
    operating_system_id INT,

    CONSTRAINT fk_compatible_smart_watch FOREIGN KEY (smart_watch_id) REFERENCES SmartWatch(id),
    CONSTRAINT fk_compatible_operating_system FOREIGN KEY (operating_system_id) REFERENCES OperatingSystem(id)
);