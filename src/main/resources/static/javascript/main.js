function productNumber(options){
	//const minusBtn = document.querySelector('#container__left');
	//const plusBtn = document.querySelector('#container__right');
	//const quantityInput = document.querySelector('.container__right-purchase-input-text');
	const minusBtn = document.querySelector(options.minus);
	const plusBtn = document.querySelector(options.plus);
	const quantityInput = document.querySelector(options.quantity);
	var max = parseInt(options.quamax);
	function decreaseQuantity() {
	 	let quantity = parseInt(quantityInput.value);
	 	if (quantity > 1) {
	    	quantity--;
	  	}
	  	quantityInput.value = quantity;
	}
	
	function increaseQuantity() {
	  	let quantity = parseInt(quantityInput.value);
	  	if (quantity < max) {
	    	quantity++;
	  	}
	  	quantityInput.value = quantity;
	}
	minusBtn.addEventListener('click', () => {
	  	decreaseQuantity();
	});
	plusBtn.addEventListener('click', () => {
	  	increaseQuantity();
	});
	        
}
// sử lý max min khi chọn sản phẩm
function productSalary(options){
    var quantityInput = document.querySelector(options.quainput);
    var defaultValue = parseInt(options.quantity)// giá trị mặc định
    var max = parseInt(options.quamax);
    //console.log(quantityInput)
    //console.log(defaultValue)
	quantityInput.addEventListener("click", function(event) {
        if (this.value === defaultValue.toString()) {
            this.value = ""; // Nếu giá trị là giá trị mặc định, xóa giá trị khi click vào
        }
    });
    quantityInput.addEventListener("input", function() {
        var value = this.value.trim();
        var validInputPattern = /^\d+$/; // Biểu thức chính quy kiểm tra giá trị nhập vào chỉ chứa các chữ số

        if (validInputPattern.test(value)) {
            var intValue = parseInt(value);
            if (intValue < defaultValue) {
                this.value = defaultValue; // Nếu giá trị nhỏ hơn 1, đặt lại thành giá trị mặc định
            } else if (intValue > max) {
                this.value = max; // Nếu giá trị vượt quá giới hạn tối đa, đặt lại thành giá trị tối đa
            }
        } else if (value !== "") {
            this.value = defaultValue; // Nếu giá trị không hợp lệ (không chỉ chứa chữ số), đặt lại thành giá trị mặc định
        }
    });

    quantityInput.addEventListener("blur", function(event) {
        if (this.value === "") {
            this.value = defaultValue; // Nếu giá trị rỗng, đặt lại thành giá trị mặc định
        }
    });

}
        
