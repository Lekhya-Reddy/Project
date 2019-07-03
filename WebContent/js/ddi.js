/**
 * 
 */

(function(angular) {
  'use strict';
  
  function contains(src, subStr) {
    return src && src.indexOf(subStr) > -1;
  }
  
  var ddi = angular.module('ddi', 
        ['restangular', 'ddi.rest', 'dials.ui.table', 'dials.du.spinner']);
  
  ddi.controller('mainCtrl', 
    ['$scope', '$http', 'KPILogREST', 'LegacyLogREST', 'OSDMLogREST', 'CallLogREST', 'PlaidLogREST', '$q',
     function($scope, $http, KPILogREST, LegacyLogREST, OSDMLogREST, CallLogREST, PlaidLogREST, $q) {
      
      $scope.callLogs = [];
      
      $scope.search = function() {
        if(!$scope.db) { return alert('Please Select a DB'); }
        $scope.coffeySpinner = true;
        $scope.clear(true);

       var restRequest = CallLogREST.getList(angular.extend({}, $scope.searchQuery, $scope.db));
   
        $scope.callLogs = restRequest.$object;
        restRequest['catch'](function(){alert('Search request failed')})['finally'](function(){ $scope.coffeySpinner = false});
        
      };
      
      function map_legacy_plaid(response) {
        var plaidLogs = response.plaidLogs;
        //if($scope.legacyLogs.length && $scope.plaidLogs.length) {
          _(response.legacyLogs).filter({ 'DataSource': 'PlaidAccess' }).each(function(legacyLog) {
            var plaidEntryIndex = 
            _.findIndex(response.plaidLogs, { 'EntryType': legacyLog.DataAction }), //legacyLog
            plaidEntry = response.plaidLogs[plaidEntryIndex],
            plaidData = response.plaidLogs[plaidEntryIndex - 1],
            plaidEntrys = [];
            
            if(plaidEntry) {
              do {
                var plaidLink = {};
                
                plaidLink.EntryType = plaidEntry.EntryType;
                plaidLink.Info = plaidEntry.AdditionalInformation.replace('localhost', plaidEntry.HostName);
                
                plaidEntrys.push(plaidLink);
                
                if(plaidData && contains(plaidData.EntryType, 'DataSource')) {
                  plaidLink.DataType = plaidData.EntryType;
                  plaidLink.Data = plaidData.AdditionalInformation;
                  if(plaidLink.Data) { plaidLink.Data = plaidLink.Data.replace(/\|/g, '</br>'); }
                }
                plaidEntryIndex += 2;
  
                plaidEntry = response.plaidLogs[plaidEntryIndex];
                plaidData = response.plaidLogs[plaidEntryIndex - 1];
              } while(plaidData && contains(plaidData.EntryType, 'RCRDataSource'));
            }
            legacyLog.plaidEntrys = plaidEntrys;
            //console.info(plaidEntryIndex, plaidEntrys);
          });
        //}
        angular.extend($scope, response);
      };
      
      $scope.showLog = function(ucid) {
        if(!$scope.db) { return alert('Please Select a DB'); }
        $scope.selectedUcid = ucid;
        $scope.kpiLogs = KPILogREST.one(ucid).getList('',$scope.db).$object;
        $scope.osdmLogs = OSDMLogREST.one(ucid).getList('',$scope.db).$object;
        $q.all({
          legacyLogs: LegacyLogREST.one(ucid).getList('',$scope.db),
          plaidLogs: PlaidLogREST.one(ucid).getList('',$scope.db)
        }).then(map_legacy_plaid);
        $scope.traceURL = 'CallTrace.html?DB_CONNECTION=' + $scope.db.DB_CONNECTION + '#' + ucid;
        
        setTimeout(function() {$('.callTraceFrame')[0].contentDocument.location.reload(true);}, 150);
      };
      
      $scope.formatDate = function() {
        var today = new Date(), year = '' + today.getFullYear();
        if($scope.searchQuery && $scope.searchQuery.date) {
          var date = $scope.searchQuery.date.split('/');
          date[0] = prefixZero(date[0] || '' + (today.getMonth() + 1));
          date[1] = prefixZero(date[1] || '' + (today.getDate()));
          if(date[2]) { date[2] = year.slice(0, date[2].length - 4) + date[2]; }
          else { date[2] = year; }
          $scope.searchQuery.date = date.join('/');
        }
      };
      
      function prefixZero(val) {
        return val.length > 1? val : '0' + val;
      };
      
/*      $scope.watch('selectedUcid', function(){
        $('.callTraceFrame')[0].contentDocument.location.reload(true);
      });*/
      
      $scope.uid = function(id) { _.uniqueId(id); };
      
      $scope.clear = function(searchQuery) {
        if(!searchQuery) { $scope.searchQuery = {}; }
        $scope.callLogs = [];
        $scope.selectedUcid = undefined;
        $scope.kpiLogs = undefined;
        $scope.osdmLogs = undefined;
        $scope.legacyLogs = undefined;
        $scope.traceURL = 'CallTrace.html';
        
        setTimeout(function() {$('.callTraceFrame')[0].contentDocument.location.reload(true);}, 150);
      };
    }
  ]);
  
  ddi.directive('ddBindHtml', ['$parse', function($parse) {
    return {
      restrict: 'A',
      scope: true,
      link: function(scope, element, attrs) {
        var $getter = $parse(attrs.ddBindHtml);
        element.html($getter(scope));
      }
    };
  }]);

})(angular, _);

$(function() {
  'use strict';
  $('a[href="#KPILog"]').tab('show');
  
  angular.element(document).ready(function() {
    angular.bootstrap(document, ['ddi']);
  });
});