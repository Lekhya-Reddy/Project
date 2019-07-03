(function (angular, _) {
'use strict';
var duTable = angular.module('dials.ui.table', []);

var duTableController = ['$scope', '$element', '$attrs', '$compile', '$controller', '$filter', '$timeout',
  function($scope, $element, $attrs, $compile, $controller, $filter, $timeout) {
    var ctrl = this,
        $viewPoint = $element.find('.sticky-body'),
        $viewPointTable = $viewPoint.find('table'),
        $tbody = $viewPointTable.find('tbody'),
        $halfLimit = 10,
        $multiSort = true;

    $scope.$at = 0;
    $scope.$limit = parseInt($attrs.bufferSize) || 50;
    $scope.$orderBy = [];
    $scope.$orderDirection = {};
    $scope.setOrder = function(column) {
      var index = _.findIndex($scope.$orderBy, function(value) { var posx = value.indexOf(column); return posx > -1 && posx < 2; }) ;

      if(index === -1 && $multiSort) {
        $scope.$orderDirection[column] = 'up';
        $scope.$orderBy.push(column);
      }
      else if( $scope.$orderBy[index] && $scope.$orderBy[index][0] === '-' ) {
        $scope.$orderBy.splice(index, 1);
        delete $scope.$orderDirection[column];
      }
      else {
        if( !$multiSort ) {
          $scope.$orderBy = [];
          $scope.$orderDirection = {};
        }

        $scope.$orderDirection[column] = 'down';
        if( index > -1 ) {
          $scope.$orderBy[index] = '-' + column;
        } else {
          $scope.$orderBy.push('-' + column);
        }
      }

      //console.info($scope.$orderBy);
    };

    var lastViewPointAt = 0;
    function updateView() {
      var rows = $scope[$attrs.duTable];
      lastViewPointAt = $viewPoint[0].scrollTop;
      
      $scope.$$viewRecords = $filter('filter')(
        rows, $scope.dataFilter, false);
      
      ctrl.external.showing = $scope.$$viewRecords?
        $scope.$$viewRecords.length : 0;
      
      ctrl.external.count = $scope[$attrs.duTable]? 
        rows.length : 0;

      //ctrl.external.selected = null; // commented as part of QC#1062
      
      $element.find('input:checked').attr('checked', false);

      $scope.$virtualFrameHeight = (ctrl.external.showing * ctrl.heights.tr);
      $viewPoint[0].scrollTop = Math.min( lastViewPointAt, $scope.$virtualFrameHeight );
    }

    //TODO: map this to input change in the header
    $scope.$watchCollection('dataFilter', updateView);
    $scope.$watch($attrs.multiSort,
      function(value) {
        $multiSort = angular.isUndefined(value)? true: value;
        $scope.$orderBy = [];
        $scope.$orderDirection = {};
        updateView();
      }
    );
    $scope.$watchCollection($attrs.duTable, function() { updateView(); ctrl.external.selected = null; });
    
    $tbody.find('tr').attr('ng-repeat',  //  | filter | orderBy | filter:dataFilter:false
      '$$row in $$viewRecords | orderBy:$orderBy | range:$at:$limit');
    
    //$tbody.html($tbody.html().replace(/{{/g, '{{record.'));

    this.external = {};

    this.heights = {
      'header': $viewPointTable.find('thead')[0].offsetHeight,
      'tr': $tbody.find('td').height(), // [0].offsetHeight, //
      'viewpoint': $viewPoint[0].offsetHeight
    };

    var viewPointSize = Math.round(this.heights.viewpoint / this.heights.tr);
    $halfLimit = Math.floor(($scope.$limit - viewPointSize) / 2.2);

    //INFO: Create the child controller, so it can update the scope
    var dataCtrl, ctrlLocals;
    if($tbody.attr('du-controller')) {
      ctrlLocals = {
        '$scope': $scope,
        '$parent': $scope.$parent,
        'external': ctrl.external
      };
      
      dataCtrl = $controller($tbody.attr('du-controller'), ctrlLocals);
    }

    var timeoutId, drawFrom;

    function $render() {
      $scope.$apply(function() {
        $scope.$at = drawFrom;
      });

      $viewPointTable.css({
        'margin-top': ((ctrl.heights.tr * drawFrom) - ctrl.heights.header) + 'px'
      });
      return;
/*      var scrollTop = $viewPoint[0].scrollTop,
          scrollAt = Math.floor(scrollTop / ctrl.heights.tr),
          scrolledFromTop = scrollAt - ($scope.$at +  $halfLimit);

      if( Math.abs(scrolledFromTop) > $halfLimit ) {
        scrollAt = scrollAt < $halfLimit? scrollAt : (scrollAt - $halfLimit);
        $viewPointTable.css({
          'margin-top': ((ctrl.heights.tr * scrollAt) - ctrl.heights.header) + 'px'
        }).find('td').css('opacity', 1);

        $scope.$apply(function(scope) {
          scope.$at = scrollAt;
        });
      }*/
    }

/*    $viewPoint.parent().on('mouseleave mousedown mouseup', function(event) {
      console.info(event.type);
      //.find('td').css('opacity', 0);
    });
*/
    //var lastScroll = _.now();
    $viewPoint.on('scroll', function() { // Set an time out
      //console.info(_.now() - lastScroll)
      //lastScroll = _.now()
      if(timeoutId) { $timeout.cancel(timeoutId); }
      
      var scrollTop = $viewPoint[0].scrollTop,
          scrollAt = Math.floor(scrollTop / ctrl.heights.tr),
          scrolledFromTop = scrollAt - ($scope.$at +  $halfLimit);
      //TODO: Render the ScrollAt onto the screen so the user would know on which screen they are at

      /*console.info(Math.abs(scrolledFromTop), $halfLimit);*/
      if( !$scope.$at || Math.abs(scrolledFromTop) > $halfLimit ) {
        drawFrom = scrollAt < $halfLimit? scrollAt : (scrollAt - $halfLimit);
        if($scope.$$viewRecords) {
          drawFrom = Math.max(0, Math.min(drawFrom, $scope.$$viewRecords.length - $halfLimit));
        } else { drawFrom = 0; }
        
        timeoutId = $timeout($render, 70);
      }
    });

    $element.addClass('table-fixed-columns');
    $compile($element)($scope);

    $scope.$parent[$attrs.name] = ctrl.external;
}];

var duTableCompile = function(element, attrs) {
  //Wrap it with the Outer & Inner Divs.
  element.wrap('<div class="stick-table"></div>')
    .wrap('<div class="sticky-body"></div>').removeAttr('du-table');

  var wraper = element.parent().parent(), // .stick-table
      tbodyWrap = element.parent(), // .stick-body
      theadWrap = angular.element('<div class="sticky-head"></div>'),
      thead = element.find('thead'),
      virtualFrame = angular.element('<div class="virtual-frame" ng-style="{\'height\': $virtualFrameHeight + \'px\'}"></div>'),
      hasFilters = (attrs.filters !== 'false'),

      // INFO: Clone this to make an sticky header & Place Holders Info for the Filters   
      stickyHeader = thead.clone();

  theadWrap.append(
    angular.element('<table></table>').append(stickyHeader).
    addClass(element.attr('class')) );

  wraper.prepend(theadWrap);
  element.css('margin-top', '-' + thead[0].offsetHeight + 'px');

  var filterHeader = angular.element('<tr></tr>'),
      stickyHeaders = stickyHeader.find('th');

  angular.forEach(element.find('tbody').find('td'), function(column, index) {
    var content = angular.element(column).text(),
        tbinding = content.trim().replace(/{{\$\$row\.|}}/g, ''),
        $header = angular.element(stickyHeaders[index]),
        placeHolder = $header.text().trim();
    
    if( hasFilters ) {
      //NOTE: add the filter column and the input used for it

      var filterColm = angular.element('<td></td>');
    
      if( placeHolder ) {

        filterColm.append(angular.element('<input type="text">')
          .attr('ng-model', 'dataFilter.' + tbinding)
          .addClass('form-control input-sm')
          .attr('placeholder', placeHolder)
        );
        
      }

      filterHeader.append(filterColm);
    }

    if( placeHolder ) {
      //NOTE: this is for the sorting of columns
      $header.addClass('header-cell')
        .attr('ng-click', 'setOrder(\'' + tbinding + '\')')
        .append('&nbsp;<i class="glyphicon" ng-class="\'glyphicon-chevron-\' + $orderDirection[\'' + tbinding + '\']"></i>');
    }
  });

  thead.css('opacity', 0);

  if( hasFilters ) {
    stickyHeader.append(filterHeader);
  }

  tbodyWrap.append(
    virtualFrame.css({
      'position': 'absolute',
      'width': '1px',
      'left': 0,
      'top': 0
    })
  );

  function duTableLinker(scope, element, attrs, ctrl) {
    var $viewPoint = element.find('.sticky-body'),
        $viewPointTable = $viewPoint.find('table');

    ctrl.heights.header = $viewPointTable.find('thead')[0].offsetHeight;
    ctrl.heights.viewpoint = $viewPoint[0].offsetHeight;

    $viewPointTable.css('margin-top', '-' + ctrl.heights.header + 'px');
  }

  return duTableLinker;
};

duTable.directive('duTable', [function() {
  return {
    restrict: 'A',
    scope: true,
    terminal: true,
    compile: duTableCompile,
    controller: duTableController
  };
}]);

duTable.directive('duChange', ['$parse', function($parse) {
  return {
    restrict: 'A',
    scope: true,
    link: function(scope, element, attrs) {
      element.on('change', function(event) {
        scope.$apply(function() {
          $parse(attrs.duChange)(scope, {'$event': event});
        });
      });
    }
  };
}]);

duTable.directive('duChecked', ['$parse', function($parse) {
  return {
    restrict: 'A',
    scope: true,
    link: function(scope, element, attrs) {
      var $getter = $parse(attrs.duChecked);
      scope.$watch(function() { return $getter(scope) && element.prop('checked'); },
        function() {
          //console.info(arguments);
          attrs.$set('checked', $getter(scope)); 
        }
      );
    }
  };
}]);

duTable.controller('duTableSingleSelect', ['$scope', 'external',
  function($scope, external) {
    $scope.select = function(record) {
      external.selected = record;
    };
}]);

duTable.controller('duTableMultiSelect', ['$scope', 'external',
  function($scope, external) {

    $scope.select = function(record, $event) {
      if( $event.target.checked ) { // !$scope.isChecked(record)
        if(!external.selected) { external.selected = []; }
        external.selected.push(record);
      } else {
        _.remove(external.selected, record);
      }
    };

    $scope.isChecked = function(record) {
      //console.info(record, external.selected);
      return !_.isUndefined(
          _.find(external.selected, {'$$hashKey': record['$$hashKey']}) );
    };
  }
]);

duTable.filter('range', [function() {
  return function(src, begin, limit) {
    return src? src.slice(begin, (begin + limit)) : src;
  };
}]);

})(angular, _);