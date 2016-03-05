var form_wizard = function () {

    var handleWizard = function () {
        var form = $("#example-form");
        form.validate({
            rules: {
                userName: {
                    required: true,
                    minlength: 5
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm: {
                    required: true,
                    minlength: 5,
                    equalTo: '#password'
                },
                email: {
                    required: true,
                    email: true
                }
            },
            messages: {
                userName: {
                    required: 'Please enter your Username'
                },
                password: {
                    minlength: "Your password must be at least 5 characters long",
                    required: 'Please enter your password'
                },
                confirm: {
                    minlength: "Your password must be at least 5 characters long",
                    required: 'Please enter your password',
                    equalTo: "Please enter the same password as above"
                },
                email: {
                    required: 'Please enter you Email',
                    email: 'Please enter available email'
                }
            },
            errorPlacement: function (error, element) {
                error.insertAfter(element);
            }
        });
        form.children("div").steps({
            headerTag: "h3",
            bodyTag: "section",
            transitionEffect: "slideLeft",
            onStepChanging: function (event, currentIndex, newIndex) {
                form.validate().settings.ignore = ":disabled,:hidden";
                return form.valid();
            },
            onFinishing: function (event, currentIndex) {
                form.validate().settings.ignore = ":disabled";
                return form.valid();
            },
            onFinished: function (event, currentIndex) {
                alert("Submitted!");
            }
        });

        var wizard_3 = $("#wizard-3").steps({
            transitionEffect: "slideLeft",
            stepsOrientation: "vertical",
            onStepChanging: function (event, currentIndex, newIndex) {
                $("#form-3").validate().settings.ignore = ":disabled,:hidden";
                return $("#form-3").valid();
            },
            onFinishing: function (event, currentIndex) {
                $("#form-3").validate().settings.ignore = ":disabled";
                return $("#form-3").valid();
            },
            onFinished: function (event, currentIndex) {
                alert("Submitted!");
            }
        });
        wizard_3.steps("add", {
            title: "Account",
            content: '<fieldset><legend>Account Information</legend><div class="form-group"><label for="userName1">Username <span class="require">*</span></label><div><input id="userName1" name="userName1" type="text" class="form-control required"></div></div><div class="form-group"><label for="password1">Password <span class="require">*</span></label><div><input id="password1" name="password1" type="text" class="form-control required"></div></div><div class="form-group"><label for="confirm1">Confirm Password <span class="require">*</span></label><div><input id="confirm1" name="confirm1" type="text" class="form-control required"></div></div></fieldset>'
        });
        wizard_3.steps("add", {
            title: "Profile",
            content: '<fieldset><legend>Profile Information</legend><div class="form-group"><label for="name1">First name <span class="require">*</span></label><div><input id="name1" name="name1" type="text" class="form-control required"></div></div><div class="form-group"><label for="surname1">Last name <span class="require">*</span></label><div><input id="surname1" name="surname1" type="text" class="form-control required"></div></div><div class="form-group"><label for="email1">Email <span class="require">*</span></label><div><input id="email1" name="email1" type="text" class="form-control required email"></div></div><div class="form-group"><label for="address1">Address</label><div><input id="address1" name="address1" type="text" class="form-control"></div></div><div class="form-group"><label for="age1">Age (The warning step will show up if age is less than 18) *</label><div><input id="age1" name="age1" type="text" class="form-control required number"></div></div></fieldset>'
        });
        wizard_3.steps("add", {
            title: "Warning",
            content: '<fieldset><legend>You are to young</legend><p>Please go away ;-)</p></fieldset>'
        });
        wizard_3.steps("add", {
            title: "Finish",
            content: '<fieldset><legend>Terms and Conditions</legend><input id="acceptTerms1" name="acceptTerms1" type="checkbox" class="required"> <label for="acceptTerms1">I agree with the Terms and Conditions.</label></fieldset>'
        });


        $('#rootwizard').bootstrapWizard({
            tabClass: 'nav', onTabShow: function (tab, navigation, index) {
                tabClass:'nav'
                var $total = navigation.find('li').length;
                var $current = index + 1;
                var $percent = ($current / $total) * 100;
                $('#rootwizard').find('.bar').css({width: $percent + '%'});
                $('.next>a,.previous>a').click(function (e) {
                    e.preventDefault();
                });
            }
        });
    }
    return {
        init: function () {
            handleWizard();

        }
    }
}(jQuery);
