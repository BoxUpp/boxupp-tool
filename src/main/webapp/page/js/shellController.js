angular.module("boxuppApp").
	controller('shellController',function($scope){
		
	$scope.activeScript = {};
	$scope.nodeSelectionDisabled = true;
	$scope.invokeShellConsole = function(){
		$scope.activeScript = {};
		$scope.activeScript.scriptName = "";
		$scope.nodeSelectionDisabled = true;
		$scope.scriptSelected = -1;
	}
	
	$scope.setShellChangeFlag = function(){
		$scope.boxuppConfig.shellChangeFlag = 1;	
	}
	
	
						
	$scope.$watch('shellScripts',function(newValue,oldValue){
		if(newValue.length !== oldValue.length){
			//shell scripts deleted or added//
			$scope.setShellChangeFlag();
		}
		//Only handle script name change events//
		if((newValue !== oldValue) && ($scope.scriptSelected !== -1)){
		$scope.setShellChangeFlag();
		/*To handle empty scripts case*/
		if(((typeof newValue[$scope.scriptSelected]) !== 'undefined') && 
			((typeof oldValue[$scope.scriptSelected]) !== 'undefined')){
			if(newValue[$scope.scriptSelected].scriptName !== oldValue[$scope.scriptSelected].scriptName){
				var newScriptName = newValue[$scope.scriptSelected].scriptName;
				var oldScriptName = oldValue[$scope.scriptSelected].scriptName;
				for(index in $scope.boxesData){
					var oldScriptNameIndex = $scope.boxesData[index].linkedScripts.indexOf(oldScriptName);
					if( oldScriptNameIndex > -1){
						$scope.boxesData[index].linkedScripts.splice(oldScriptNameIndex,1);
						$scope.boxesData[index].linkedScripts.push(newScriptName);
					}
				}
			}
		}
	}},true);
	
	$scope.pushNewScript = function(name,source){
		if((typeof name !== 'undefined') && (name !== "")){
			var newScript = {};
			newScript.scriptName = name;
			newScript.scriptSource = source;
			var scriptStatus = $scope.checkIfScriptExists(name);
			
			if(scriptStatus.exists && scriptStatus.index > -1){
				var userInput = confirm("You already have a script by that name. Do you want to overwrite?");
				if(userInput){
					$scope.shellScripts.splice(scriptStatus.index,1);
					$scope.shellScripts.splice(scriptStatus.index,0,newScript);
				}	
			}else{
				$scope.shellScripts.push(newScript);
			}
		}
	}
	
	$scope.checkIfScriptExists = function(name){
		var scriptStatus = {"exists":false,index:-1};
		for(script in $scope.shellScripts){
			if($scope.shellScripts[script].scriptName === name){
				scriptStatus.exists = true;
				scriptStatus.index = script;
			}
		}
		return scriptStatus;
	}
	
	$scope.selectScript = function(num){
		if($scope.nodeSelectionDisabled === true) animateArrow();
		$scope.activeScript = $scope.shellScripts[num];
		$scope.nodeSelectionDisabled = false;
	}
	
	$scope.deleteScript = function(num){
		$scope.removeMappings($scope.shellScripts[num].scriptName);
		$scope.shellScripts.splice(num,1);
	}
	
	$scope.editScript = function(num){
		$scope.activeScript = $scope.shellScripts[num];
		$scope.scriptSelected = num;
	}
	
	$scope.removeMappings = function(nameOfScript){
		for(index in $scope.boxesData){
			var boxScripts = $scope.boxesData[index].linkedScripts;
			var scriptIndex = boxScripts.indexOf(nameOfScript);
			if( scriptIndex > -1){
				$scope.boxesData[index].linkedScripts.splice(scriptIndex,1);
			}
		}
	}
		
	$scope.toggleScriptSelection = function(num){
		var index = $scope.boxesData[num].linkedScripts.indexOf($scope.activeScript.scriptName);
		if( index > -1){
			$scope.boxesData[num].linkedScripts.splice(index,1);
		}else{
				$scope.boxesData[num].linkedScripts.push($scope.activeScript.scriptName);
		}
		if($scope.boxesData[num].linkedScripts.length === 0){
			$scope.boxesData[num].isShellMapped = false;
		}else{
			$scope.boxesData[num].isShellMapped = true;
		}
	}
		
});