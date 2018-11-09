
var PORTAL = (function (PORTAL, $) {

    PORTAL.modules.Base = {};

    PORTAL.modules.Base.selfSelector = "#main-content-block";

    PORTAL.modules.Base.init = function ($self) {

    var modelsStore;
    var select = $self.find("#model-select");
    var inputsBlock = $self.find(".inputs-block");
    var currentQuery;

    select.change(function(){
        var selectValue = $("#model-select :selected").text();
        if (selectValue != 'Choose model for testing.'){
            modelsStore.forEach(function(model) {
                if (model.className == selectValue){
                var fields = model.fields;
                inputsBlock.empty();
                    var method = $("input[name='contact']:checked").val();
                    if (method == "POST"){
                        for (var fieldName in fields){
                            inputsBlock.append('<input type="text"> ' + fieldName + '<Br>');
                        }
                    }
                    if (method == "GET"){
                    currentQuery = location.origin + '/bin/rest.' + model.extension +'/new.json';
                        inputsBlock.append('<input class="get-query-input" type="text" value="'+ currentQuery +'"><Br>');
                    }
                    inputsBlock.append("<button>Send</button>");
                }

            });
        } else{
            inputsBlock.empty();
        }

    });
        console.log('Component: "#main-content-block"');

                    $.ajax({
                        type: 'GET',
                        url: "/bin/models",
                        success: function (data) {
                        modelsStore = data;

                            if (data){
                                modelsStore.forEach(function(model) {
                                  select.append("<option>"+model.className+"</option>");
                                });
                            }
                            console.log(data);
                        }
                    });


    };

    return PORTAL;

})(PORTAL || {}, jQuery);

