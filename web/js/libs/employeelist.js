var serviceURL = "http://localhost:8080/GestionDesBiens/webresources/model.item/";

getEmployeeList();

$(document).ajaxError(function(event, request, settings) {
	alert("Error accessing the server");
});

function getEmployeeList() {
	$.getJSON(serviceURL , function(data) {
		$('#employeeList li').remove();
		var Items = data.Item;
		$.each(Items, function(index, Item) {
			$('#employeeList').append(
				'<li><a href="employeedetails.html#' + Item.itemCode + '">'
				+ Item.itemDateCreated + ' ' + Item.itemId + ' (' 
				+ Item.itemName + ')</a></li>');
		});
	});
}