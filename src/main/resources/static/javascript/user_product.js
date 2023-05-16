/* chuyển content  */
const account = document.getElementById('container__menu-account');
const product = document.getElementById('container__menu-product');
if(account!=undefined && product!=undefined){
	var x = document.querySelector('.content-account');
	var y = document.querySelector('.content-phetshop');
	account.addEventListener('click', function(){
		account.classList.add("active--right");
		product.classList.remove("active--right")   
		x.style.display="block";
		y.style.display="none";
	});
	product.addEventListener('click', function(){
		account.classList.remove("active--right");
		product.classList.add("active--right")
		x.style.display="none";
		y.style.display="block";
	});
}


// chọn đánh giá
function SelectOne(options) {
    const buttons = document.querySelectorAll(options.id);
    const inputRating = document.querySelector(options.rating);
    buttons.forEach((button, index) => {
        button.addEventListener("click", function() {
            if (!button.classList.contains(options.action)) {
                buttons.forEach(btn => btn.classList.remove(options.action));
                button.classList.add(options.action);
                inputRating.value = index;
            }
        });
    });
}
