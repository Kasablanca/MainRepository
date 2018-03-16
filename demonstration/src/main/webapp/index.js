$(function() {
	$("#test").on("click", function() {
		$.ajax({
			"url": "http://localhost:8080/fastjson/test",
			"type": "get",
			"dataType": "json",
			"success": function(data) {
				console.log(data);
			}
		})
	});
});