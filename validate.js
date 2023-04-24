function validateForm() {
	var userName = document.forms["gameForm"]["userName"].value;
	
	if (userName == null || userName == "") { //checks user name is not null
		alert("User name is required");
		return false;
	}
}
