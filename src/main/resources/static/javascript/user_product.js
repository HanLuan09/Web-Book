/* chuyển content  */


const account = document.getElementById('container__menu-account');
const product = document.getElementById('container__menu-product');
const check = document.getElementById('check-input');
if(account!=undefined && product!=undefined){
	var x = document.querySelector('.content-account');
	var y = document.querySelector('.content-phetshop');
	if(check.value == 1) {
		account.classList.remove("active--right");
		product.classList.add("active--right")   
		x.style.display="none";
		y.style.display="block";
	}else {
		account.classList.add("active--right");
		product.classList.remove("active--right")   
		x.style.display="block";
		y.style.display="none";
	}

	account.addEventListener('click', function(){
		account.classList.add("active--right");
		product.classList.remove("active--right")
		check.value = "2";   
		x.style.display="block";
		y.style.display="none";
	});
	product.addEventListener('click', function(){
		account.classList.remove("active--right");
		product.classList.add("active--right")
		check.value = "1"; 
		x.style.display="none";
		y.style.display="block";
	});
}
//đổi mật khẩu
const btnPass = document.getElementById('btn-pass');
const tablePass = document.getElementById('table-pass');
console.log(btnPass)
btnPass.addEventListener('click', function(){
	tablePass.style.display="inline-table";
});
// thông báo đạt hàng thành công
const addToCartPopup = document.getElementById('add-to-cart-popup');
// Kiểm tra xem div thông báo có tồn tại không
if (addToCartPopup !== null) {      
	addToCartPopup.classList.add('show');
	// Tự động ẩn div thông báo sau
	setTimeout(function() {
		addToCartPopup.classList.remove('show');	                
	}, 1000);
}