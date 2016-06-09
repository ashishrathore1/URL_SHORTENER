<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.6/semantic.min.css">
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.6/semantic.min.js" /></script>
<style type="text/css">
body {
	background-color: #F4F4F4;
}

body>.grid {
	height: 100%;
}

.image {
	margin-top: -100px;
}

.column {
	max-width: 450px;
}

.ui.form .error.message, .ui.form .success.message, .ui.form .warning.message
{display:block;}
</style>
</head>
<body>

	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<h2 class="ui teal image header">
				<div class="content">Log-in to your account</div>
			</h2>
			<form class="ui large form" action="/urlshortener/login"
				method="POST">
				<div class="ui stacked segment">
					<div class="field">
						<div class="ui left icon input">
							<i class="user icon"></i> <input type="text" name="username"
								placeholder="E-mail address">
						</div>
					</div>
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="password"
								placeholder="Password">
						</div>
					</div>
					<input type="submit" name="button"
						class="ui fluid large teal submit button" value="login" />
				</div>
				<c:if test="${not empty invalidCredentials}">
					<div class="ui error message">
							<div class="content">${invalidCredentials }</div>
					</div>
				</c:if>

			</form>


		</div>
	</div>

	<!--<form action="/urlshortener/login" method="POST">
          Username<input type="text" name="username"/> 
          <br>Password<input type="password" name="password"/>
          <br> <input type="submit" name="button" value="login"/>
      </form>
-->

</body>
</html>