/*$(document).ready(function () {
    // Activate tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Select/Deselect checkboxes
    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
});*/
function checkAdmin(){
	const inputAdmin = document.querySelector("#input-admin");
    var admin = document.querySelectorAll(".admin-none");
        	//console.log(admin)
    if(inputAdmin.value ===""){
        		
        admin.forEach(function(index){
        	index.style.display = "none";
       });
    }else{
        		
       admin.forEach(function(index){
        	index.style.display = "block";
       });
    }
}
function searchName(param){
    $(".pading-item--number").removeClass("active");
    $("#page-item--1").addClass("active");
    var txtSearch =param.value;
    $.ajax({
        url: "/admin-ajax",
        type: "get",
        data: {
            search: txtSearch,
            spbatdau: 0,
        },
        dataType: "json",
        success: function(data) {
            var html = "";
            for (var i = 0; i < data.length; i++) {
               var p = data[i];
				html += `<tr>
				    <td>${p.nameB}</td>
				    <td>${p.author}</td>
				    <td>${p.category}</td>
				    <td>${p.releaseDate}</td>
				    <td>${p.pages}</td>
				    <td>${p.sellNumber}</td>
				    <td class ="admin-none">
				        <a href="/admin/${p.idB}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
				        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" data-book-id="${p.idB}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
				    </td>
				</tr>`;
            }
            var row = document.getElementById("content-tbody");
            row.innerHTML = html;	    
            checkAdmin();
        },
        error: function(xhr){
            
        }
    })
}
$(".header__search-button").on("click", function(e) {
    
    var searchValue = $(".header__search-input").val(); // Lấy giá trị từ ô input tìm kiếm

    // Gửi AJAX request để tìm kiếm dữ liệu
    $.ajax({
        url: "/admin-ajax",
        type: "GET",
        data: {
            search: searchValue,
            spbatdau: 0 // Đặt giá trị spbatdau về 0 khi tìm kiếm mới
        },
        dataType: "json",
        success: function(data) {
            var html = "";
            for (var i = 0; i < data.length; i++) {
               var p = data[i];
				html += `<tr>
				    <td>${p.nameB}</td>
				    <td>${p.author}</td>
				    <td>${p.category}</td>
				    <td>${p.releaseDate}</td>
				    <td>${p.pages}</td>
				    <td>${p.sellNumber}</td>
				    <td class ="admin-none">
				        <a href="/admin/${p.idB}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
				        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" data-book-id="${p.idB}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
				    </td>
				</tr>`;
			}
            var row = document.getElementById("content-tbody");
            row.innerHTML = html;
            checkAdmin();
        }
    });
});

$("body").on("click", ".pading-item--number", function(){
     $(".pading-item--number").removeClass("active");
    $(this).addClass("active");
    var sotrang = $(this).text();                         
    var spbatdau = (sotrang - 1) * 12;
    var searchValue = $(".header__search-input").val();
    $.ajax({
        url: "/admin-ajax",
        type: "GET",
        data: {
            search: searchValue,
            spbatdau: spbatdau
        },
        dataType: "json",
        success: function(data){
            var html = "";
            for (var i = 0; i < data.length; i++) {
                var p = data[i];
				html += `<tr>
				    <td>${p.nameB}</td>
				    <td>${p.author}</td>
				    <td>${p.category}</td>
				    <td>${p.releaseDate}</td>
				    <td>${p.pages}</td>
				    <td>${p.sellNumber}</td>
				    <td class ="admin-none">
				        <a href="/admin/${p.idB}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
				        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" data-book-id="${p.idB}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
				    </td>
				</tr>`;
            }
            var row = document.getElementById("content-tbody");
            row.innerHTML = html;
            checkAdmin();
        }
    });
});

$("body").on("click", ".page-item--left", function() {
    var currentPage = parseInt($(".pading-item--number.active").text());
    if (currentPage > 1) {
        var newPage = currentPage - 1;
        $(".pading-item--number").removeClass("active");
        $(".pading-item--number").eq(newPage -1).addClass("active");
        var spbatdau = (newPage - 1) * 12;
        var searchValue = $(".header__search-input").val();
        $.ajax({
            url: "/admin-ajax",
            type: "GET",
            data: {
                search: searchValue,
                spbatdau: spbatdau
            },
            dataType: "json",
            success: function(data) {
	            var html = "";
	            for (var i = 0; i < data.length; i++) {
	                var p = data[i];
					html += `<tr>
					    <td>${p.nameB}</td>
					    <td>${p.author}</td>
					    <td>${p.category}</td>
					    <td>${p.releaseDate}</td>
					    <td>${p.pages}</td>
					    <td>${p.sellNumber}</td>
					    <td class ="admin-none">
					        <a href="/admin/${p.idB}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
					        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" data-book-id="${p.idB}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
					    </td>
					</tr>`;
	            }
	            var row = document.getElementById("content-tbody");
	            row.innerHTML = html;
	            checkAdmin();
            }
        });
    }
});

$("body").on("click", ".page-item--right", function() {
    var currentPage = parseInt($(".pading-item--number.active").text());
    var totalPages = parseInt($(".pading-item--number").last().text());
    if (currentPage < totalPages) {
        var newPage = currentPage + 1;
        $(".pading-item--number").removeClass("active");
        $(".pading-item--number").eq(newPage - 1).addClass("active");
        var spbatdau = (newPage - 1) * 12;
        var searchValue = $(".header__search-input").val();
        $.ajax({
            url: "/admin-ajax",
            type: "GET",
            data: {
                search: searchValue,
                spbatdau: spbatdau
            },
            dataType: "json",
            success: function(data) {
                var html = "";
	            for (var i = 0; i < data.length; i++) {
	                var p = data[i];
					html += `<tr>
					    <td>${p.nameB}</td>
					    <td>${p.author}</td>
					    <td>${p.category}</td>
					    <td>${p.releaseDate}</td>
					    <td>${p.pages}</td>
					    <td>${p.sellNumber}</td>
					    <td class ="admin-none">
					        <a href="/admin/${p.idB}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
					        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" data-book-id="${p.idB}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
					    </td>
					</tr>`;
	            }
	            var row = document.getElementById("content-tbody");
	            row.innerHTML = html;
	            checkAdmin();
            }
        });
    }
});
/*
$("body").on("click", ".page-item", function() {
    var currentPage = parseInt($(".pading-item--number.active").text());
    var totalPages = parseInt($(".pading-item--number").last().text());
    var index = $(this).index();
    if (index === 0) {
        // Xử lý khi nhấn nút "left"
        if (currentPage > 1) {
            $(".pading-item--number.active").removeClass("active");
            $(".pading-item--number").eq(currentPage - 2).addClass("active");
            updatePagination();
        }
    } else if (index === $(".page-item").length - 1) {
        // Xử lý khi nhấn nút "right"
        if (currentPage < totalPages) {
            $(".pading-item--number.active").removeClass("active");
            $(".pading-item--number").eq(currentPage).addClass("active");
            updatePagination();
        }
    } else {
        // Xử lý khi nhấn nút "..."
        var targetPage = parseInt($(this).text());
        var firstPage = parseInt($(".pading-item--number").first().text());
        var lastPage = parseInt($(".pading-item--number").last().text());
        if (targetPage < firstPage || targetPage > lastPage) {
            $(".pading-item--number").removeClass("active");
            $(".pading-item--number").eq(targetPage - 1).addClass("active");
            updatePagination();
        }
    }
});

function updatePagination() {
    var currentPage = parseInt($(".pading-item--number.active").text());
    var totalPages = parseInt($(".pading-item--number").last().text());

    $(".page-item").removeClass("disabled");

    if (currentPage === 1) {
        $(".page-item.previous").addClass("disabled");
    } else if (currentPage === totalPages) {
        $(".page-item.next").addClass("disabled");
    }

    var firstPage = 1;
    var lastPage = totalPages;

    if (totalPages > 5) {
        if (currentPage <= 3) {
            lastPage = 5;
        } else if (currentPage >= totalPages - 2) {
            firstPage = totalPages - 4;
        } else {
            firstPage = currentPage - 2;
            lastPage = currentPage + 2;
        }
    }

    $(".pading-item--number").hide();
        $(".pading-item--number").slice(firstPage - 1, lastPage).show();

    if (firstPage > 1) {
        $(".pagination").prepend('<li class="page-item"><a class="page-link">...</a></li>');
    }

    if (lastPage < totalPages) {
        $(".pagination").append('<li class="page-item"><a class="page-link">...</a></li>');
    }
}*/