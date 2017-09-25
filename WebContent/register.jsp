<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

</head>
<body>

<script>
window.onload = function(){
	var msg = '${rmsg}';
	if (!!msg){
		alert(msg);
	}
}


function checkPwd(form){
	if(form.password.value != form.confirm_password.value){
		alert("passwords mismatch!");
		return false;
	}
}
</script>

	<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarTogglerDemo01"
			aria-controls="navbarTogglerDemo01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
			<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
			<h3><a class="navbar-brand" href="register.jsp">IS102</a></h3>
			<h3><a class="navbar-brand" href="login.jsp">Login</a></h3>					
		</div>
		
	</nav>
	
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Register with SMU Email Account</h2>
			<hr>
		</header>
		<form class="form-horizontal" role="form" method="POST" action="Authenticate_register" onsubmit="return checkPwd(this);">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
					<label class="sr-only" for="username"> Username </label>
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-user"></i>
						</div>
						<input type="text" name="username" class="form-control" id="username"
							placeholder="Insert your username" required autofocus size="34">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
					<label class="sr-only" for="email"> School Email </label>
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-envelope-o"></i>
						</div>
						<input type="email" name="email" class="form-control" id="email"
							placeholder="Insert your email" required autofocus size="34">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
					<label class="sr-only" for="password">Password</label>
					<!-- setting margin bottom and margin right for user id -->
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<!-- importing fonts awesome into login page -->
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-key"></i>
						</div>
						<input type="password" name="password" class="form-control"
							id="password" placeholder="Insert your password" required
							autofocus size="34">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
					<label class="sr-only" for="password">Password</label>
					<!-- setting margin bottom and margin right for user id -->
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<!-- importing fonts awesome into login page -->
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-key"></i>
						</div>
						<input type="password" name="confirm_password" class="form-control"
							id="confirm_password" placeholder="Confirm your password" required
							autofocus size="34">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-7"></div>
				<button type="submit" class="btn btn-primary">
					Register
				</button>
			</div>
		</form>
	</div>

<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="style/js/jquery-3.2.1.min.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>
</html>