$(function (){
	var photoDiv = $("#photoDiv");
	var fileInput = $("#file");
	var fileReader = new FileReader();
	var filter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	var $photo = $("<img>");
	
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
		}
	});
});