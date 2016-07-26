
  $(function () {
      $('#birth').datetimepicker();
  });

$(function () {
    $('#country').change(function () {
        if ($(this).val() == 'USA') {
            $("#state").show();
            $("#city").show();
            $("#zip").show();
        }
        else {
            $("#state").hide();
            $("#city").hide();
            $("#zip").hide();
        }
    });
});

$(function () {
    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': { allow_single_deselect: true },
        '.chosen-select-no-single': { disable_search_threshold: 10 },
        '.chosen-select-no-results': { no_results_text: 'Oops, nothing found!' },
        '.chosen-select-width': { width: "95%" }
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
});