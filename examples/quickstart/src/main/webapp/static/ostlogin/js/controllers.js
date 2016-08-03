angular.module('starter.controllers',  ['ngCookies'] )

.controller('AppCtrl', function($state, $http, $scope, $ionicModal, $ionicPopover, $timeout,  $location, $ionicPopup , $cookieStore) {

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

		$http.post('http://localhost:8001/quickstart/admin/user/getUserByLoginName',{loginName:user.username}).success(function(dbuser){
			
			if(user.username=='admin' && user.password==user.password){
				console.log('login success...');
				console.log('login user clanId = '+dbuser.clanId);
				$cookieStore.put("loginid", dbuser.id);
				$cookieStore.put("loginPid", "xcccc");
				var lid = $cookieStore.get("loginid");
				
				//judgment infos size
				$http.post('http://localhost:8001/quickstart/task/getTasksByParent',{pid:0,lid: lid,clanId:dbuser.clanId}).success(function(data){
					console.log('>>>length '+data.results.length );
					console.log('>>>clanId '+dbuser.clanId);
					if(data.results.length==0){
						 $location.path('/app/noneinfo');
					 }else{
						 $location.path('/app/profiles/'+dbuser.clanId);
					 }
				 
				})
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
		  if(data.success==false){
			  // An alert dialog
			  $scope.showAlert(data.message);	
			  $location.path('/app/profiles');   
		  }else{
			  console.log('createContact function success...'+data);
		  }
	  });
	  
	  console.log('createContact end...');
  };
  
})

.controller('InfoNoneCtrl', function($http , $scope , Profiles , $cookieStore) {
})


.controller('ProfilesCtrl', function($http , $scope , Profiles , $cookieStore, $stateParams) {
	 console.log('profilesCtrl begain...');
	 var lid = $cookieStore.get("loginid");
	 var clanId = $stateParams.clanId;
	 console.info('cookieStore >> '+lid);
	 console.info('clanId >> '+clanId);
	 
	 $http.post('http://localhost:8001/quickstart/task/getTasksByParent',{pid:0,lid: lid, clanId:clanId}).success(function(data){
		 console.log('ProfilesCtrl funtion success...');
		 $scope.profiles  = data;
		 
		 
    	 console.log('getTasksByParent >> '+ data.length);
     });
	 
	 console.log('profilesCtrl end...');
})

.controller('ProfileCtrl', function($http, $scope, $stateParams , Profiles) {
	 $http.post('http://localhost:8001/quickstart/task/getTasksById',{id:$stateParams.profileId}).success(function(data){
		 console.log('ProfilesCtrl funtion success...');
		 $scope.profile  = data;
		 
		 
    	 console.log('getTasksByParent >> '+ data.length);
     });
})

.controller('AddCtrl', function($scope , $stateParams , $cookieStore) {
	var Nid = $stateParams.Nid;
	console.log('add ctrl .. Nid = '+Nid);
	
	//set uid
	var lid = $cookieStore.get("loginid");
	
	
	if(Nid==0){
	   	  $scope.newTask = {
	   			  parents: '0',
	   			  relation: '宗族',
	   			  userId: lid
	   	  };
	}else{
		 $scope.parentList = [
	          { text: "Backbone", value: "1" },
	          { text: "Angular", value: "2" },
	          { text: "Ember", value: "3" },	
	          { text: "Knockout", value: "4" }
	        ];
	   	  $scope.relationList = [
	          { text: "夫妻", value: "夫妻" },
	          { text: "子女", value: "子女" },
	          { text: "父母", value: "父母" }
	       ]; 
	   	  $scope.newTask = {
	   			  parents: '1',
	   			  relation: '宗族'
	   	  };
	}
})

.controller('DashCtrl', function($scope, $stateParams , Profiles) {
	$scope.profiles = Profiles.all();
});

