-- Prvo FK tablice, pa glavne
IF OBJECT_ID(N'SmartWatchHealthFunction', N'U') IS NOT NULL DROP TABLE SmartWatchHealthFunction;
IF OBJECT_ID(N'SmartWatchOperatingSystem',N'U') IS NOT NULL DROP TABLE SmartWatchOperatingSystem;
IF OBJECT_ID(N'SmartWatch',               N'U') IS NOT NULL DROP TABLE SmartWatch;
IF OBJECT_ID(N'Brand',                    N'U') IS NOT NULL DROP TABLE Brand;
IF OBJECT_ID(N'Category',                 N'U') IS NOT NULL DROP TABLE Category;
IF OBJECT_ID(N'HealthFunction',           N'U') IS NOT NULL DROP TABLE HealthFunction;
IF OBJECT_ID(N'OperatingSystem',          N'U') IS NOT NULL DROP TABLE OperatingSystem;
IF OBJECT_ID(N'[User]',                   N'U') IS NOT NULL DROP TABLE [User];