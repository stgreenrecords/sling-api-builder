
var PORTAL = (function (PORTAL, $) {

    PORTAL.modules.Base = {};

    PORTAL.modules.Base.selfSelector = "#main-content-block";

    PORTAL.modules.Base.init = function ($self) {

        console.log('Component: "#main-content-block"');

                    $.ajax({
                        type: 'GET',
                        url: "/bin/models",
                        success: function (data) {
                            var select = $self.find("#model-select");
                            if (data){
                                data.forEach(function(model) {
                                  select.append("<option>"+model.className+"</option>");
                                });
                            }
                            console.log(data);
                        }
                    });
    };

    return PORTAL;

})(PORTAL || {}, jQuery);

