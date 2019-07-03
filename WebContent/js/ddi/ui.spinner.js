(function (angular) {
  'use strict';
  var duSpinner = angular.module('dials.du.spinner', []);

  duSpinner.directive('duSpinner', [function() {
    var template = '<div class="du spinner"><i class="animation glyphicon glyphicon-refresh"></i></div>';
    return {
      restrict: 'A',
      scope: false,
      link: function(scope, element, attrs) {
        element.append(template);

        var degree = 0, timerId = 0,
            frameNode = element.find('.du.spinner'),
            spinner = frameNode.find('i');

        attrs.$observe('duSpinner', function(value) {
          try {
            if(value === 'true') {
              frameNode.css('display', 'block');
              timerId = setInterval(function() {
                var transform = 'rotate(' + degree + 'deg)';
                spinner.css({
                  'transform': transform,
                  '-ms-transform': transform
                });
                degree = degree === 360? 0 : (degree + 3.6);
              }, 10);
            } else {
              frameNode.css('display', 'none');
              clearTimeout(timerId);
            }
          } catch(ex) {}
        });

      }
    };
  }]);
})(angular);
