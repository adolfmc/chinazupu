angular.module('starter.controllers',  ['ngCookies'] )

.controller('AppCtrl', function($http, $scope, $ionicModal, $ionicPopover, $timeout,  $location, $ionicPopup , $cookieStore) {

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

		$http.post('http://localhost:8001/quickstart//admin/user/getUserByLoginName',{loginName:user.username}).success(function(data){
			console.log('login success...');
			
			if(user.username=='admin' && user.password==data.password){
				$cookieStore.put("loginid", data.id);
				$cookieStore.put("loginPid", "xcccc");
				
				$location.path('/app/profiles');
			}else{
				$scope.showAlert('Invalid username or password.');	
			}
		});
		
		
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
	  console.log('createContact begain...');
	  console.log(newTask);

	  $http.post('http://localhost:8001/quickstart/task/create', newTask).success(function(data){
		  console.log('createContact function success...'+data);
		  $location.path('/app/profiles');   
      });
	  
	  console.log('createContact end...');
  };
  
})

.controller('ProfilesCtrl', function($http , $scope , Profiles , $cookieStore) {
	 console.log('profilesCtrl begain...');
	 $http.post('http://localhost:8001/quickstart/task/getTasksByParent',{pid:2}).success(function(data){
		 console.log('ProfilesCtrl funtion success...');
		 $scope.profiles  = data;
    	 console.log('getTasksByParent >> '+ data);
     });
	 
	 var xx = $cookieStore.get("loginid");
	 console.info('cookieStore >> '+xx);
	 console.log('profilesCtrl end...');
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
	   { text: "宗族", value: "宗族" },                      
       { text: "夫妻", value: "夫妻" },
       { text: "孝男", value: "孝男" },
       { text: "孝女", value: "孝女" },	
       { text: "父母", value: "父母" }
     ]; 

	 $scope.newTask = {
	   parents: '1',
       relation: '宗族'
     };
     
     $scope.serverSideChange = function(item) {
       console.log("Selected Serverside, text:", item.text, "value:", item.value);
     };
})

.controller('DashCtrl', function($scope, $stateParams , Profiles) {
	$scope.profiles = Profiles.all();
});

