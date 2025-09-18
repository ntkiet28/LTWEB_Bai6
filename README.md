# Category Management Web Application

## Prerequisites
- Java JDK 17
- Maven
- Docker Desktop
- Apache Tomcat 9
- Eclipse IDE (hoặc IDE tương tự)

## 1. Cài đặt và Chạy Database

### 1.1 Khởi động MySQL Container
```bash
# Chạy container MySQL
docker compose -f mySQL.yaml up -d --build

# Kiểm tra container đã chạy chưa
docker ps
```

### 1.2 Tạo Database và User
```bash
# Truy cập MySQL với tài khoản root
docker exec -it my-mysql-bt4 mysql -uroot -proot

# Trong MySQL console, chạy các lệnh sau:
CREATE DATABASE IF NOT EXISTS mydb;
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'pass123';
GRANT ALL PRIVILEGES ON mydb.* TO 'user'@'%';
FLUSH PRIVILEGES;
exit;
```

### 1.3 Kiểm tra Kết nối
```bash
# Kiểm tra kết nối với user mới tạo
docker exec -it my-mysql-bt4 mysql -uuser -ppass123 mydb
```

## 2. Build và Deploy Project

### 2.1 Build Project với Maven
```bash
mvn clean install
```

### 2.2 Deploy vào Tomcat (Eclipse)
1. Mở Eclipse IDE
2. Import project:
   - File -> Import -> Maven -> Existing Maven Projects
   - Browse đến thư mục project
   - Click Finish

3. Thêm Tomcat Server (nếu chưa có):
   - Window -> Show View -> Servers
   - Click 'No servers are available...'
   - Chọn Tomcat 9.0
   - Browse đến thư mục Tomcat đã cài đặt
   - Click Finish

4. Deploy project:
   - Click phải vào project
   - Run As -> Run on Server
   - Chọn Tomcat server
   - Click Finish

## 3. Truy cập Ứng dụng
```
http://localhost:8080/bt4-0.0.1-SNAPSHOT
```

## 4. Cấu trúc Project
```
src/
├── main/
│   ├── java/
│   │   ├── Configs/     # Cấu hình Database
│   │   ├── Controller/  # Xử lý requests
│   │   ├── Dao/        # Data Access Objects
│   │   ├── Entity/     # Entity classes
│   │   ├── Filter/     # Authentication filters
│   │   ├── Service/    # Business logic
│   │   └── Utils/      # Utility classes
│   ├── resources/
│   │   └── META-INF/   # JPA configuration
│   └── webapp/
│       ├── views/      # JSP files
│       └── WEB-INF/    # Web configuration
```

## 5. Các Lệnh Hữu ích

### Docker Commands
```bash
# Kiểm tra trạng thái container
docker ps

# Khởi động lại container
docker restart my-mysql-bt4

# Xem logs của container
docker logs my-mysql-bt4

# Dừng container
docker stop my-mysql-bt4

# Xóa container
docker rm my-mysql-bt4
```

### Maven Commands
```bash
# Clean và build
mvn clean install

# Chỉ chạy test
mvn test
```

## 6. Troubleshooting

### Lỗi Kết nối Database
1. Kiểm tra container MySQL đang chạy:
```bash
docker ps
```

2. Kiểm tra file persistence.xml:
- Đường dẫn: `src/main/resources/META-INF/persistence.xml`
- Kiểm tra thông tin kết nối:
  - URL: jdbc:mysql://localhost:3307/mydb
  - Username: user
  - Password: pass123

3. Restart container nếu cần:
```bash
docker restart my-mysql-bt4
```

### Lỗi Deploy
1. Kiểm tra Tomcat đang chạy
2. Xóa deployment cũ trong thư mục webapps của Tomcat
3. Clean và build lại project
4. Deploy lại

## 7. Tài khoản Mặc định
- Admin:
  - Username: admin
  - Password: admin123
  - Role: 1 (Admin)