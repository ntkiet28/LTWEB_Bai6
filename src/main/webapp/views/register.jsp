<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: sans-serif;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background: #f0f2f5;
}

.container {
	width: 400px;
	padding: 40px;
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h2 {
	font-size: 2em;
	color: #333;
	margin-bottom: 20px;
}

.alert {
	padding: 10px;
	margin-bottom: 20px;
	background-color: #f8d7da;
	color: #721c24;
	border: 1px solid #f5c6cb;
	border-radius: 5px;
	text-align: left;
}

.form-group {
	margin-bottom: 15px;
	text-align: left;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	color: #555;
	font-weight: bold;
}

.form-group input {
	width: 100%;
	padding: 12px;
	border: 1px solid #ddd;
	border-radius: 5px;
	font-size: 1em;
	transition: border-color 0.3s;
}

.form-group input:focus {
	outline: none;
	border-color: #007bff;
}

.btn {
	width: 100%;
	padding: 12px;
	border: none;
	background-color: #007bff;
	color: #fff;
	font-size: 1.1em;
	font-weight: bold;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.btn:hover {
	background-color: #0056b3;
}

.link {
	margin-top: 20px;
	font-size: 0.9em;
	color: #666;
}

.link a {
	color: #007bff;
	text-decoration: none;
	font-weight: bold;
	transition: color 0.3s;
}

.link a:hover {
	color: #0056b3;
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Đăng ký</h2>

		<c:if test="${not empty alert}">
			<div class="alert">${alert}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/register"
			method="post">
			<div class="form-group">
				<label for="username">Tên đăng nhập:</label> <input type="text"
					id="username" name="username" required />
			</div>

			<div class="form-group">
				<label for="email">Email:</label> <input type="email" id="email"
					name="email" required />
			</div>

			<div class="form-group">
				<label for="password">Mật khẩu:</label> <input type="password"
					id="password" name="password" required />
			</div>

			<button type="submit" class="btn">Đăng ký</button>
		</form>

		<div class="link">
			Đã có tài khoản? <a
				href="${pageContext.request.contextPath}/views/login.jsp">Đăng
				nhập</a>
		</div>
	</div>
</body>
</html>