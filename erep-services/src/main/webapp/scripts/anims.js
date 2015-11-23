'use strict';

//MENU DROP DOWN ANIMATION
$(function(){
  $('.dropdown').on('show.bs.dropdown', function(){
    $(this).find('.dropdown-menu').first().stop(true, true).slideDown(200);
  });
  $('.dropdown').on('hide.bs.dropdown', function(){
    $(this).find('.dropdown-menu').stop(false, true).slideUp(200, function(){
    });
  });
});
