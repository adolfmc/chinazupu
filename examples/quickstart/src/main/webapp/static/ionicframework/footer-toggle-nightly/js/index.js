angular.module('ionicApp', ['ionic'])
.controller('AppCtrl', function($scope) {
  $scope.showFooter = true;
  $scope.toggleFooter = function() {
    console.log('Toggling footer');
    $scope.showFooter = !$scope.showFooter;
  }
})