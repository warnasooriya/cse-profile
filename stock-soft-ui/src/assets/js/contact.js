
(function ($) {
    "use strict";
    jQuery(document).ready(function ($) {
        $(document).on('submit', '#contact_form_submit', function (e) {
            e.preventDefault();

            var name = $('#name').val();
            var email = $('#email').val();
            var message = $('#message').val();

            if (!name) {
                $('#name').removeClass('error');
                $('#name').addClass('error').attr('placeholder', 'Please Enter Name');
            } else {
                $('#name').removeClass('error');
            }
            if (!email) {
                $('#email').removeClass('error');
                $('#email').addClass('error').attr('placeholder', 'Please Enter Email')
            } else {
                $('#email').removeClass('error');
            }
            if (!message) {
                $('#message').removeClass('error');
                $('#message').addClass('error').attr('placeholder', 'Please Enter Your Message')
            } else {
                $('#message').removeClass('error');
            }


            if (name && email && message) {
                var reqData = {
                    'name': name,
                    'email': email,
                    'message': message,
                };
                $.ajax({
                    type: "POST",
                    url: 'https://cseprofile.com/api/users/message',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(reqData),
                    processData: false,
                    success: function (data) {

                        $('#contact_form_submit').children('.email-success').remove();
                        $('#contact_form_submit').prepend('<span class="alert alert-success email-success"> Message Sent Successfully !  ,  we will contact you soon  </span>');
                        $('#name').val('');
                        $('#email').val('');
                        $('#message').val('');
                        $('#map').height('576px');
                        $('.email-success').fadeOut(3000);
                        setTimeout(() => {
                            $('#map').height('513px');
                        }, 3000);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {

                        $('#contact_form_submit').children('.email-success').remove();
                        $('#contact_form_submit').prepend('<span class="alert alert-danger email-success">somenthing is wrong</span>');
                        $('#map').height('576px');
                        $('.email-success').fadeOut(3000);
                        setTimeout(() => {
                            $('#map').height('513px');
                        }, 3000);
                    }
                });
            } else {
                debugger;
                $('#contact_form_submit').children('.email-success').remove();
                $('#contact_form_submit').prepend('<span class="alert alert-danger email-success">somenthing is wrong</span>');
                $('#map').height('576px');
                $('.email-success').fadeOut(3000);
                setTimeout(() => {
                    $('#map').height('513px');
                }, 3000);
            }



        });
    })

}(jQuery));
