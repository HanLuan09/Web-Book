// giảm giá 
// Lấy danh sách tất cả các phần tử có class 'home-product-item__sale'
var sales = document.querySelectorAll('.home-product-item__sale');
// Duyệt qua từng phần tử và kiểm tra giảm giá
for (var i = 0; i < sales.length; i++) {
  var percent = parseFloat(sales[i].querySelector('.home-product-item__sale-percent').textContent);
  if (percent <= 0) {
    // Nếu giảm giá bằng 0 thì xóa phần tử
    sales[i].parentNode.removeChild(sales[i]);
  }
}
// login/reginter
var loginLink = document.querySelector(".header__user-item-login");
var registerLink = document.querySelector(".header__user-item-register");
// click đăng ký
loginLink.addEventListener("click", function() {
  document.querySelector(".modal").style.display= "flex";
  document.querySelector("#auth-form-register").style.display= "none";
  document.querySelector("#auth-form-login").style.display= "none";
  document.querySelector("#auth-form-pay").style.display= "block";
});
// click đăng nhập
registerLink.addEventListener("click", function() {
  document.querySelector(".modal").style.display= "flex";
  document.querySelector("#auth-form-register").style.display= "block";
  document.querySelector("#auth-form-login").style.display= "none";
  document.querySelector("#auth-form-pay").style.display= "none";
});
// giảm giá 
 // Lấy danh sách tất cả các phần tử có class 'home-product-item__sale'
var sales = document.querySelectorAll('.home-product-item__sale');

// Duyệt qua từng phần tử và kiểm tra giảm giá
for (var i = 0; i < sales.length; i++) {
  var percent = parseFloat(sales[i].querySelector('.home-product-item__sale-percent').textContent);
  if (percent <= 0) {
    // Nếu giảm giá bằng 0 thì xóa phần tử
    sales[i].parentNode.removeChild(sales[i]);
  }
}


// 
function Validator(options) {

  function getParent(element, selector) {
    while (element.parentElement.matches(selector)) {
      return element.parentElement;
    }
    element = element.parentElement
  }

  var selectorRule = {};
  // hàm thực hiện validate
  function validate(inputElement, errorElement,rule) {
    // value: inputE.value
    // test: rule.test
    var errorMessage;
    // mảng rules của selector
    var rules = selectorRule[rule.selector];
    for(var i =0; i< rules.length; i++) {
      // nếu có lỗi dừng kiểm tra
      errorMessage = rules[i](inputElement.value);
      if(errorMessage) break;
    }

    if(errorMessage){
      errorElement.innerText = errorMessage;
    }
    else{
      errorElement.innerText = '';
      inputElement.parentElement.classList.remove('invalid');
    }
    return !errorMessage;
  }
  // console.log(options)
  // lấy element của form
  var formElement = document.querySelector(options.form);
  if(formElement) {

    // khi submit form
    formElement.onsubmit = function(e) {
      e.preventDefault();
      var isFormVlaid = true;
      options.rules.forEach(function(rule) {
        var inputElement = formElement.querySelector(rule.selector);
        var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
        var isVlaid = validate(inputElement, errorElement,rule);
        if(!isVlaid) {
          isFormVlaid = false;
        }
      });

      if(isFormVlaid) {
        // TH summit với js
        if(typeof options.onSubmit === 'function') {
          var enableInputs = formElement.querySelectorAll('[name]:not([disabled])')
          var formEnableInputs = Array.from(enableInputs).reduce(function (values, input) {
            values[input.name] = input.value;
            return values; 
          }, {});
          options.onSubmit(formEnableInputs);
        }
        // TH submmit với hành vi mặc định
        else {
          formElement.onsubmit();
        }
      }
    }

    // Lặp qua mỗi rule và xử lý (lắng nghe sự kiên blur, input,..)
    options.rules.forEach(function(rule) {
      // Lưu lại các rules trong mỗi input
      if(Array.isArray(selectorRule[rule.selector])) {
        selectorRule[rule.selector].push(rule.test)
      }
      else {
        selectorRule[rule.selector] = [rule.test];
      }
      // 
      var inputElement = formElement.querySelector(rule.selector);
      var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
      if(inputElement) {
        // xử lý TH blur khỏi input
        inputElement.onblur = function() {
          validate(inputElement, errorElement,rule)
        }
        // xử lý mỗi khi nhập vào input
        inputElement.oninput = function() {
          errorElement.innerText = ''; 
          inputElement.parentElement.classList.remove('invalid');
        }
      }
    });
  }
  // console.log(selectorRule)
}
// Định nghĩa Rules
// Nguyên tắc của các rules
// 1 khi có lỗi messaes lỗi
Validator.isRequired = function(selector) {
  return {
    selector: selector,
    test: function(value) {
      return value.trim() ? undefined : 'Vui lòng nhập trường này'

    }
  };
};
Validator.isEmail = function(selector, messaes) {
  return {
    selector: selector,
    test: function(value) {
      var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
      return regex.test(value) ? undefined : messaes || 'Vui lòng nhập email hợp lệ'
    }
  };
};
Validator.minLength = function(selector, min, messaes) {
  return {
    selector: selector,
    test: function(value) {
      return value.length >= min ? undefined : messaes || `Vui lòng nhập tối thiểu ${min} ký tự`;
    }
  };
};
Validator.isConfirmed = function(selector, getConfirmValue, messaes) {
  return {
    selector: selector,
    test: function(value) {
      return value === getConfirmValue() ? undefined : messaes || 'Giá trị nhập vào không đúng'
    }
  };
};