-- Tạo cơ sở dữ liệu
CREATE DATABASE IphonedShop;
GO

-- Sử dụng cơ sở dữ liệu
USE IphonedShop;
GO

-- Tạo bảng Categories (Danh mục sản phẩm)
CREATE TABLE Categories (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name NVARCHAR(255) NOT NULL
);

-- Tạo bảng Sizes (Danh sách kích thước quần áo)
CREATE TABLE Sizes (
    size_id INT PRIMARY KEY IDENTITY(1,1),
    size_name NVARCHAR(50) NOT NULL  -- Ví dụ: S, M, L, XL, XXL
);

-- Tạo bảng Products (Sản phẩm)
CREATE TABLE Products (
    product_id INT PRIMARY KEY IDENTITY(1,1),
    product_name NVARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    Description NVARCHAR(255) NOT NULL,
    image NVARCHAR(225) NOT NULL, 
    Price DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);

-- Tạo bảng ProductSizes (Liên kết sản phẩm với size và số lượng tồn kho)
CREATE TABLE ProductSizes (
    product_size_id INT PRIMARY KEY IDENTITY(1,1),
    product_id INT NOT NULL,
    size_id INT NOT NULL,
    stock_quantity INT NOT NULL,  -- Số lượng tồn kho theo size
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (size_id) REFERENCES Sizes(size_id)
);

-- Tạo bảng Users (Người dùng)
CREATE TABLE Users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    user_name VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(255),
    Email VARCHAR(255),
    Phone VARCHAR(20),
    Address NVARCHAR(255),
    RoleID INT NOT NULL -- 1: Admin , 2: User
);

-- Tạo bảng Orders (Đơn hàng)
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    StatusID INT NOT NULL,  -- 1: Đã đặt hàng thành công, 2: Không thành công
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Tạo bảng OrderDetails (Chi tiết đơn hàng)
CREATE TABLE OrderDetails (
    order_detailid INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL,
    product_id INT NOT NULL,
    size_id INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (size_id) REFERENCES Sizes(size_id)
);

-- Tạo bảng Cart (Giỏ hàng)
CREATE TABLE Cart (
    CartID INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    ProductID INT NOT NULL,
    size_id INT NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (ProductID) REFERENCES Products(product_id),
    FOREIGN KEY (size_id) REFERENCES Sizes(size_id)
);
INSERT INTO Sizes (size_name) 
VALUES ('128G'), ('256G'), ('512G'),('1TB');

INSERT INTO Users (user_name, Password, full_name, Email, Phone, Address, RoleID)
VALUES ('admin', 'admin123', N'ADMIN', 'admin@gmail.com', '0392936078', N'Quận 12, TP.HCM', 1), -- 1 là Admin
	   ('user', 'user123', N'User', 'user@gmail.com', '0392956578', N'Quận 12, TP.HCM', 2); -- 2 là User
GO
INSERT INTO Categories(category_name)
VALUES (N'Apple'),
		(N'Samsung'),
		(N'OPPO'),
		(N'Xiaomi')
GO
INSERT INTO Products (product_name, category_id, Description, image, Price)
VALUES (N'Iphone 16 Pro Max', 1, N'Trả góp 0% lãi suất.', N'iphone2.webp', CAST(31999998.00 AS Decimal(18, 2))),
		(N'Iphone 16 Pro', 1, N'Trả góp 0% lãi suất.', N'iphone3.webp', CAST(27489999.00 AS Decimal(18, 2))),
		(N'Iphone 16e', 1, N'Trả góp 0% lãi suất. (Sắp về hàng)', N'iphone1.webp', CAST(16990000.00 AS Decimal(18, 2))),
		(N'Iphone 16 ', 1, N'Trả góp 0% lãi suất.', N'iphone6.webp', CAST(21190000.00 AS Decimal(18, 2))),
		(N'Iphone 15 Plus', 1, N'Trả góp 0% lãi suất.', N'iphone5.webp', CAST(28489998.00 AS Decimal(18, 2))),
		(N'Iphone 15', 1, N'Trả góp 0% lãi suất.', N'iphone7.webp', CAST(18890000.00 AS Decimal(18, 2))),
		(N'Iphone 14 Pro Max', 1, N'Trả góp 0% lãi suất.', N'iphone9.webp', CAST(25590000.00 AS Decimal(18, 2))),
		(N'Iphone 11', 1, N'Trả góp 0% lãi suất. (Hết hàng)', N'iphone8.webp', CAST(9489998.00 AS Decimal(18, 2))),
		(N'Iphone 13', 1, N'Trả góp 0% lãi suất.', N'iphone4.webp', CAST(12750000.00 AS Decimal(18, 2))),
		(N'Iphone 13 Pro Max', 1, N'Trả góp 0% lãi suất.', N'iphone10.webp', CAST(22990000.00 AS Decimal(18, 2))),
		(N'Samsung Galaxy S25 Ultra', 2, N'Trả góp 0% lãi suất.', N'samsung1.webp', CAST(13990000.00 AS Decimal(18, 2))),
		(N'Samsung Galaxy A15 LTE', 2, N'Trả góp 0% lãi suất.', N'samsung2.webp', CAST(4289998.00 AS Decimal(18, 2))),
		 (N'Samsung Galaxy S24 Ultra', 2, N'Trả góp 0% lãi suất.', N'samsung3.webp', CAST(25989999.00 AS Decimal(18, 2))),
		 (N'Samsung Galaxy M55', 2, N'Trả góp 0% lãi suất.', N'samsung4.webp', CAST(9390000.00 AS Decimal(18, 2))),
		 (N'Samsung Galaxy Z Fold6', 2, N'Trả góp 0% lãi suất.', N'samsung6.webp', CAST(36490000.00 AS Decimal(18, 2))),
		 (N'OPPO Reno13 F', 3, N'Trả góp 0% lãi suất.', N'oppo1.webp', CAST(8990000.00 AS Decimal(18, 2))),
		 (N'OPPO Reno12 5G', 3, N'Trả góp 0% lãi suất.', N'oppo2.webp', CAST(9790000.00 AS Decimal(18, 2))),
		 (N'OPPO Find X8', 3, N'Trả góp 0% lãi suất.', N'oppo3.webp', CAST(22990000.00 AS Decimal(18, 2))),
		 (N'OPPO A3 ', 3, N'Trả góp 0% lãi suất.', N'oppo4.webp', CAST(4590000.00 AS Decimal(18, 2))),
		 (N'Xiaomi Redmi Note 14', 4, N'Trả góp 0% lãi suất.', N'xiaomi1.webp', CAST(4790000.00 AS Decimal(18, 2))),
		 (N'Xiaomi 14T', 4, N'Trả góp 0% lãi suất.', N'xiaomi2.webp', CAST(12989999.00 AS Decimal(18, 2))),
		 (N'Xiaomi Redmi Note 14 Pro Plus 5G', 4, N'Trả góp 0% lãi suất.', N'xiaomi3.webp', CAST(10790000.00 AS Decimal(18, 2)))
	   SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ProductSizes';
	   SELECT * FROM ProductSizes;
	   Select * from Products;
	   select * from Sizes;


	  


SELECT COLUMN_NAME 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'OrderDetails';