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
				$cookieStore.put("clanId", dbuser.clanId);
				var lid = $cookieStore.get("loginid");
				
				//judgment infos size
				$http.post('http://localhost:8001/quickstart/task/getIndexTasks',{id:0,clanId:dbuser.clanId}).success(function(data){
					console.log('>>>length '+data.results.length );
					console.log('>>>clanId '+dbuser.clanId);
					if(data.results.length==0){
						 $location.path('/app/noneinfo');
					 }else{
						 $location.path('/app/profiles/'+"0_"+dbuser.clanId);
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
		  console.log(data);
		  if(data.success==false){
			  // An alert dialog
			  $scope.showAlert(data.message);	
			  $location.path('/app/profiles/'+ "0_0");   
		  }else{
			  $location.path('/app/profiles/' +data.results.parents+ "_0");
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
	 var IdAndClanId = $stateParams.IdAndClanId;
	 var Id = IdAndClanId.split("_")[0];
	 var ClanId = IdAndClanId.split("_")[1];
	 
	 console.info('IdAndClanId >> '+IdAndClanId);
	 
	 $http.post('http://localhost:8001/quickstart/task/getIndexTasks',{id:Id,clanId:ClanId}).success(function(data){
		 console.log('ProfilesCtrl funtion success...');
		 $scope.infos  = data;
     });
	 
	 console.log('profilesCtrl end...');
})

.controller('ProfileCtrl', function($state, $location, $http, $scope, $stateParams , Profiles) {
	 var IdAndPid=$stateParams.IdAndPid
	 var id= IdAndPid.split("_")[0];
	 var pid= IdAndPid.split("_")[1];
	 console.log(IdAndPid);
	 console.log(id);
	 console.log(pid);
	 if (id == pid) {
		 console.log("id==pid");
		 $http.post('http://localhost:8001/quickstart/task/getTaskById',{id:id}).success(function(data){
			 console.log('ProfilesCtrl funtion success...');
			 $scope.info  = data;
		 });
		 //$state.go('app.profile', {IdAndClanId:id+"_0"}, { reload: true });
	 }else{
		 console.log("id<>pid");
		 //$location.path('/app/profiles/' +id+ "_0");
		 $state.go('app.profiles', {IdAndClanId:id+"_0"}, { reload: true });
		 console.log("id<>pid "+id);
	 }
	
})

.controller('AddCtrl', function($http ,$scope , $stateParams , $cookieStore) {
	var id = $stateParams.id;
	console.log('add ctrl .. id = '+id);
	
	//set uid
	var lid = $cookieStore.get("loginid");
	var clanId = $cookieStore.get("clanId");
	console.log("add controller clanId ="+clanId)
	if(id==0){
	   	  $scope.newTask = {
	   			  parents:0,
	   			  gender:'男',
	   			  clanId:clanId,
	   			  pName:'宗族',
	   			  relation: '宗族',
	   			  userId: lid
	   	  };
	}else{
		 $http.post('http://localhost:8001/quickstart/task/getTaskById',{id:id}).success(function(data){
			 $scope.relationList = [
			                        { text: "夫妻", value: "夫妻" },
			                        { text: "子女", value: "子女" },
			                        { text: "父母", value: "父母" }
			                        ]; 
			 $scope.newTask = {
					 gender:'男',
					 parents:data.results.id,
					 clanId:clanId,
					 pName:data.results.fullName,
					 relation: '子女'
			 };
		 });
		 
	}
})

.controller('DashCtrl', function($scope, $stateParams , Profiles) {
	$scope.profiles = Profiles.all();
});

