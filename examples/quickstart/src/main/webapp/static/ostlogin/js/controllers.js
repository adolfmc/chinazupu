angular.module('starter.controllers', [])

.controller('AppCtrl', function($http,$scope, $ionicModal, $ionicPopover, $timeout,  $location, $ionicPopup) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  //--------------------------------------------
   $scope.login = function(user) {
			
		if(typeof(user)=='undefined'){
			$scope.showAlert('Please fill username and password to proceed.');	
			return false;
		}

		if(user.username=='admin' && user.password=='admin'){
			$location.path('/app/dashboard');
		}else{
			$scope.showAlert('Invalid username or password.');	
		}
		
	};
  //--------------------------------------------
  $scope.logout = function() {   $location.path('/app/login');   };
  //--------------------------------------------
   // An alert dialog
	 $scope.showAlert = function(msg) {
	   var alertPopup = $ionicPopup.alert({
		 title: 'Warning Message',
		 template: msg
	   });
	 };
  //--------------------------------------------
	 
   	 
  //------------createContact--------------------------------
  $scope.createContact = function(newTask) {   
	  //TODO save users
	  console.log(newTask);
	  
	  /**
	   * POST 1
	   * $http.post('http://localhost:8001/quickstart/task/create', {
		  newTask: newTask
      })
	   */
	  
	  
	  /**
	   * POST 2
	   * http.post('http://localhost:8001/quickstart/task/create',newTask).success(function(){
          //window.location.href = "Gulugulus/subMenu";
      });
	   * 
	   */
	  
	  
	  /**
	   * POST 3
	   * $http({
    method: "POST",
    url: "/metronic/api/getAfterSales",
    data:$.param({orderNo: orderNo,sessionId:sessionId}),
    async: false,
    dataType:'json'
})
	   */
	  $http.post('http://localhost:8001/quickstart/task/create', newTask).success(function(){
          //window.location.href = "Gulugulus/subMenu";
      }).success(function(){
    	  //window.location.href = "Gulugulus/subMenu";
      });
	  
	  $location.path('/app/profiles');   
  };
  
})

.controller('ProfilesCtrl', function($scope , Profiles) {
    $scope.profiles = Profiles.all();
})

.controller('ProfileCtrl', function($scope, $stateParams , Profiles) {
	$scope.profile = Profiles.get($stateParams.profileId);
})

.controller('AddCtrl', function($scope) {
	  $scope.parentList = [
       { text: "Backbone", value: "1" },
       { text: "Angular", value: "2" },
       { text: "Ember", value: "3" },	
       { text: "Knockout", value: "4" }
     ];
	  
	  $scope.relationList = [
	   { text: "宗族", value: "0001" },                      
       { text: "夫妻", value: "0002" },
       { text: "孝男", value: "0003" },
       { text: "孝女", value: "0004" },	
       { text: "父母", value: "0005" }
     ]; 

	 $scope.newTask = {
	   parents: '1',
       relation: '0001'
     };
     
     $scope.serverSideChange = function(item) {
       console.log("Selected Serverside, text:", item.text, "value:", item.value);
     };
})

.controller('DashCtrl', function($scope, $stateParams , Profiles) {
	$scope.profiles = Profiles.all();
});

