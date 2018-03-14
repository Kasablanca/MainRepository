$(function (){
	var photoDiv = $("#photoDiv");
	var fileInput = $("#file");
	var fileReader = new FileReader();
	var filter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	var $photo = $("<img>");
	var photoBlob = null;
	var fileChange = false;
	
	fileReader.onload = function (event){
		$photo.attr("src",event.target.result);
		
		photoDiv.empty();
		photoDiv.append($photo);
	};
	
	photoDiv.click(function (){
		fileInput.trigger("click");
	});
	
	fileInput.change(function (){
		if(this.files.length === 0) {
			photoDiv.empty();
			photoDiv.text("未选择头像");
		} else if(!filter.test(this.files[0].type)){
			alert("Invalid image type");
		} else {
			fileReader.readAsDataURL(this.files[0]);
			fileChange = true;
		}
	});
	
	if(photoName){
		$photo[0].crossOrigin = "Anonymous";
		$photo[0].onload = function (){
			var canvas = document.createElement("CANVAS");
			var ctx = canvas.getContext("2d");
			var dataURL;
			var extentionName = getExtentionName(fileInput.val());
			
			canvas.height = this.height;
			canvas.width = this.width;
			ctx.drawImage(this,0,0);
			
			canvas.toBlob(function (blob){
				photoBlob = blob;
				canvas = null;
			},"image/"+extentionName);
			
			$photo[0].onload = null;
		}
		$photo[0].src = "Resource/"+photoName;
		photoDiv.append($photo[0]);
	}
	
	$("#mainForm").submit(function (event){
		event.preventDefault();
		var fd;
		if(fileChange){
			fileInput.appendTo($(this));
			fd = new FormData(this);
		} else {
			fd = new FormData(this);
			fd.append("userPortraitFile",photoBlob,photoName);
		}
		$.ajax({
			method: "post",
			url: "user/updUser",
			processData: false,
			contentType: false,
			data: fd,
			error: function (){
				alert("网络连接失败");
			},
			success: function (data){
				if(data.code === 0) {
					window.location.href = "user/list";
				} else {
					alert(data.message);
				}
			}
		});
	});
});