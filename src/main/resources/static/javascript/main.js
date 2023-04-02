
// tăng số lượng sản phẩm
const minusBtn = document.querySelector('#container__left');
const plusBtn = document.querySelector('#container__right');
const quantityInput = document.querySelector('.container__right-purchase-input-text');
function decreaseQuantity() {
 	let quantity = parseInt(quantityInput.value);
 	if (quantity > 1) {
    	quantity--;
  	}
  	quantityInput.value = quantity;
}

function increaseQuantity() {
  	let quantity = parseInt(quantityInput.value);
  	quantity++;
  	quantityInput.value = quantity;
}
minusBtn.addEventListener('click', () => {
  	decreaseQuantity();
});
plusBtn.addEventListener('click', () => {
  	increaseQuantity();
});

        
