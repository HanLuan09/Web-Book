$(document).ready(function() {
    var images = [
      "./assets/img/slider/slider_shoppet_1.jpg",
      "./assets/img/slider/slider_shoppet_2.jpg",
      "./assets/img/slider/slider_shoppet_1.jpg",
      "./assets/img/slider/slider_shoppet_2.jpg",
      "./assets/img/slider/slider_shoppet_1.jpg"
    ];
    var index = 0;
    var intervalId = setInterval(function() {
      index++;
      if (index >= images.length) {
        index = 0;
      }
      $(".home-slider-left-img__left").fadeOut("slow", function() {
        $(this).attr("src", images[index]);
        $(this).fadeIn("slow");
      });
    }, 9000);
  
    $("#home-slider-left-btn-icon-left").click(function() {
      clearInterval(intervalId);
      index--;
      if (index < 0) {
        index = images.length - 1;
      }
      $(".home-slider-left-img__left").fadeOut("slow", function() {
        $(this).attr("src", images[index]);
        $(this).fadeIn("slow");
      });
      intervalId = setInterval(function() {
        index++;
        if (index >= images.length) {
          index = 0;
        }
        $(".home-slider-left-img__left").fadeOut("slow", function() {
          $(this).attr("src", images[index]);
          $(this).fadeIn("slow");
        });
      }, 9000);
    });
  
    $("#home-slider-left-btn-icon-right").click(function() {
      clearInterval(intervalId);
      index++;
      if (index >= images.length) {
        index = 0;
      }
      $(".home-slider-left-img__left").fadeOut("slow", function() {
        $(this).attr("src", images[index]);
        $(this).fadeIn("slow");
      });
      intervalId = setInterval(function() {
        index++;
        if (index >= images.length) {
          index = 0;
        }
        $(".home-slider-left-img__left").fadeOut("slow", function() {
          $(this).attr("src", images[index]);
          $(this).fadeIn("slow");
        });
      }, 9000);
    });
});
  