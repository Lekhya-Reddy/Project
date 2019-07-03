(function(angular, _) {
  'use strict';
  var restService = angular.module('ddi.rest', ['restangular']),

  /* INFO: End-Point Map and loop through to build the services.
    End Point: [Service Name, Unique ID For Service] Path pair's
  */
  endPoints = {
    'log/kpi': ['KPILogREST', 'Ucid'],
    'log/legacy': ['LegacyLogREST', 'Ucid'],
    'log/osdm': ['OSDMLogREST', 'Ucid'],
    '/log/plaid': ['PlaidLogREST', 'Ucid'],
    'tracelog/': ['CallLogREST', 'UCID']
  };

  restService.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push(function() {
      return {
        'response': function(response) {
          var headers = response.headers();
          if(headers.location || headers['x-auth-ctl']) {
            window.location = headers.location || headers['x-auth-ctl'];
            return; 
          }

          return response;
        }
      };
    });  
  }]);
  
  restService.config(['RestangularProvider', function(RestangularProvider) {
    RestangularProvider.setDefaultHttpFields({cache: false});
    RestangularProvider.setBaseUrl('/DrivrDiagnostics/api');
  }]);

  restService.run(['$http', 'Restangular',
    function($http, Restangular) {
      Restangular.configuration.getIdFromElem = function(elem) {
        if(endPoints[elem.route]) {
          var id = endPoints[elem.route][1];
          if(angular.isString(id) && id.length) {
            return elem[id];
          } else if(angular.isArray(id) && id.length) {
            var path = _.map(id, function(prop) { return elem[prop]; });
            if(_.every(path, angular.isDefined)) {
             return path.join('/');
            }
          }
        }
      };
   
      $http.defaults.cache = false;
  }]);

  angular.forEach(endPoints, function serviceBuilder(service, endPoint) {
    restService.factory(service[0], ['Restangular', function(Restangular) {
      return Restangular.all(endPoint);
    }]);
  });
})(angular, _);