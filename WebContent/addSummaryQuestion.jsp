<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Post, dao.AvatarDAO,dao.PostDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">
</head>
<body>
<%@ include file="navigationBar.jsp"%>



	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Post Class Summary Questions</h2>
			<hr>
		</header>
		<div class="row">

			<div class="col-2">
				<div class="btn-group" role="group" aria-label="Basic example">
					<a class="btn btn-outline-primary" style="width: 15rem"
						href="postClassSummary.jsp"><b>View Summary Questions </b></a>

				</div>

			</div>

		</div>
		<div class="viewPostBoarder">

			<div class="container">
				<form method="post" action="AddNewSummaryQuestion">
					<div id="dynamicField">


						<p id="h1">
							<input type="text" id="dyTextField1" name="dyTextField1" value=""
								class="form-control" placeholder="Enter question here..."
								required />


						</p>




					</div>
					<div class="form-group row">
						<div class="offset-sm-2 col-sm-7">
							<a id="addTextField" class="btn btn-outline-primary">Add
								Question</a> <a id="removeTextField" class="btn btn-outline-primary">Remove
								Question</a> <input type="submit" class="btn btn-primary"
								value="Submit">
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>


</body>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
<script>
	$(function() {
		var scntDiv = $('#dynamicField');
		//var i = $('#dynamicField p').size() + 1;

		var counter = 2;
		$('#addTextField')
				.live(
						'click',
						function() {

							if (counter > 5) {
								alert("Only 5 textboxes allow");
								return false;
							}

							var newTextBoxDiv = $(document.createElement('p'))
									.attr("id", 'h' + counter);

							newTextBoxDiv
									.after()
									.html(
											'<input type="text"  name="dyTextField' + counter +
			      '" id="dyTextField' + counter + '" value="" class="form-control" placeholder="Enter question here..." required>');

							newTextBoxDiv.appendTo(scntDiv);

							counter++;

						});

		$('#removeTextField').live('click', function() {
			if (counter == 2) {
				alert("Minimum one question");
				return false;
			}

			counter--;

			$("#h" + counter).remove();
		});
	});
</script>



</html>