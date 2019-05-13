<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Stock </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.stock.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="stockname">Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.stock.name" id="stockname" class="stockname form-control input-sm" placeholder="Enter stock name" required ng-minlength="2" ng-pattern="ctrl.onlyText"/>
	                        </div>
	                    </div>
	                </div>


	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="currentPrice">CurrentPrice</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.stock.currentPrice" id="currentPrice" class="form-control input-sm" placeholder="Enter current price." required ng-pattern="ctrl.digitsOrDecimals" />
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.stock.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Stocks </span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>NAME</th>
		                <th>CURRENT PRICE</th>
		                <th>LAST UPDATE</th>
		                <th width="100"></th>
		                <th width="100"></th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="u in ctrl.getAllStocks()">
		                <td>{{u.id}}</td>
		                <td>{{u.name}}</td>
		                <td>{{u.currentPrice}}</td>
		                <td>{{u.timestamp}}</td>
		                <td><button type="button" ng-click="ctrl.editStock(u.id)" class="btn btn-success custom-width">Edit</button></td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>
