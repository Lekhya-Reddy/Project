<!DOCTYPE html>
<%@page import="com.ctl.ch.driver.diagnostics.Utils"%>
<%@page import="com.ctl.ch.driver.diagnostics.db.ConfigableDataBase"%>
<html>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=9" />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/dials.tools.css">
    
    <title>IVR Diagnostic Tool (IDT)</title>
  </head>
  <body>
    <div class="container-fluid" ng-controller="mainCtrl" du-spinner="{{coffeySpinner}}">
    
      <h2>IVR Diagnostic Tool (IDT)</h2>
      
      <form class="form-inline" role="form" method="post">
        <div class="row">
          <div class="col-md-2">
            <label class="control-label">DB Connection</label>
          </div>       
          <div class="col-sm-4">
            
              <select class="form-control" name="<%=ConfigableDataBase.ATTRIBUTE_NAME%>" ng-model="db.<%=ConfigableDataBase.ATTRIBUTE_NAME%>">
                <option value="">--Select a Connection--</option>
                <% for(String dbname : ConfigableDataBase.dbConfig.getProperty("dbnames").split(",")) { %>
                <option value="<%= dbname %>"><%= dbname %></option>
                <% } %>
              </select>
           
          </div>
          <div class="col-md-1">
            <label for="ucid" class="control-label">UCID</label>
          </div>
          <div class="col-sm-2"><input type="text" class="form-control" id="ucid" ng-model="searchQuery.ucid" name="ucid" placeholder="UCID" ng-init="searchQuery.ucid = '<%=Utils.r(request, "ucid")%>'"></div>
           
          <div class="col-md-1">
            <label for="dnis" class="col-sm-2 control-label">DNIS</label>
          </div>
          <div class="col-sm-2"><input class="form-control" type="text" id="dnis" ng-model="searchQuery.dnis" placeholder="DNIS" name="dnis" ng-init="searchQuery.dnis = '<%=Utils.r(request, "dnis")%>'"></div>
        </div>
        <br/>
        <div class="row">
          <div class="col-md-12">
            <div class="col-md-1"><label for="ani" class="control-label">ANI</label></div>
            <div class="col-sm-2"><input class="form-control" type="text" id="ani" ng-model="searchQuery.ani" placeholder="ANI" name="ani" ng-init="searchQuery.ani = '<%=Utils.r(request, "ani")%>'"></div>
            
            <div class="col-md-1"><label for="accountid" class="control-label">Account Id</label></div>
            <div class="col-sm-2"><input class="form-control" type="text" id="accountid" ng-model="searchQuery.accountid" placeholder="Account Id" name="accountid" ng-init="searchQuery.accountid = '<%=Utils.r(request, "accountid")%>'"></div>
            
            <div class="col-md-1"><label for="date" class="control-label">Date (US)</label></div>
            <div class="col-sm-2"><input class="form-control" type="text" id="date" ng-model="searchQuery.date" placeholder="mm/dd/yyyy" ng-blur="formatDate()" name="date" ng-init="searchQuery.date = '<%=Utils.r(request, "date")%>'"></div>
            
            <div class="col-sm-3">
              <button type="button" class="btn btn-primary" ng-click="search()">Search</button>
              <button type="button" class="btn btn-warning" ng-click="clear()">Clear</button>
            </div>
          </div>
        </div>
    </form>
      
      <br/><br/>
      
      <table du-table="callLogs" name="callLogTable" class="table table-bordered table-hover" filters="false">
        <thead>
          <tr>
            <th width="10%">UCID</th>
            <th width="10%" title="Session Connection Id">SessConn Id</th>
            <th width="10%">Vru Id</th>
            <th width="10%" title="External System Unique Id">ExtSys Uniq Id</th>
            <th width="10%">DNIS</th>
            <th width="10%">Account Id</th>
            <th width="10%">ANI</th>
            <th width="10%">RxSessionId</th>
            <th width="10%">Language</th>
            <th width="10%">Time Stamp</th>
          </tr>
        </thead>
        <tbody du-controller="angular.noop">
          <tr ng-click="$parent.showLog($$row.UCID)"  style="cursor: pointer;">
            <td>{{$$row.UCID}}</td>
            <td>{{$$row.SessionConnectionId}}</td>
            <td>{{$$row.VruId}}</td>
            <td>{{$$row.ExternalSystemUniqueId}}</td>
            <td>{{$$row.Dnis}}</td>
            <td>{{$$row.AccountId}}</td>
            <td>{{$$row.Ani}}</td>
            <td>{{$$row.RxSessionId}}</td>
            <td>{{$$row.Language}}</td>
            <td>{{$$row.TimeStamp}}</td>
          </tr>
        </tbody>
      </table>

      <div id="tabs-box">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li><a href="#KPILog" role="tab" data-toggle="tab">KPI Log</a></li>
          <li><a href="#OSLegLog" role="tab" data-toggle="tab">OSDM & Legacy Log Data</a></li>
          <% if(Utils.isDev(request)) { %><li><a href="#traceLog" role="tab" data-toggle="tab">Trace Log</a></li><% } %>
        </ul>
        
        <!-- Tab panes -->
        <div class="tab-content">
          <div class="tab-pane fade" id="KPILog">
            <table class="table table-bordered table-hover">
              <tr>
                <th>Date/Time</th>
                <th>RXSessionId</th>
                <th>ETN</th>
                <th>Host</th>
                <th>Menu</th>
                <th>Vru Id</th>
                <th>Call Reason</th>
                <th>Application Name</th>
              </tr>
              <tr ng-repeat="$log in kpiLogs">
                <td>{{$log.TimeStamp}}</td>
                <td>{{$log.RxSessionId}}</td>
                <td>{{$log.EtnBan}}</td>
                <td>{{$log.HostName}}</td>
                <td>{{$log.Menu}}</td>
                <td>{{$log.VruId}}</td>
                <td>{{$log.CallReason}}</td>
                <td>{{$log.ApplicationName}}</td>
              </tr>
            </table>
          </div>
          <div class="tab-pane fade" id="OSLegLog">
            <table class="table table-bordered">
              <tr>
                <th width="50%">OSDM Log Data</th>
                <th width="50%">Legacy Log Data</th>
              </tr>
              <tr>
                <td>
                  <div class="panel-group" id="OSDMaccordion">
                    <div class="panel panel-default" ng-repeat="$log in osdmLogs">
                      <div class="panel-heading">
                        <h4 class="panel-title">
                          <a data-toggle="collapse" data-parent="#OSDMaccordion" href="#osdm{{$id}}">{{$log.State}}</a>
                          &nbsp;<i ng-if="$log.ReturnCode == 'FAILURE'" class="glyphicon glyphicon-remove"></i>
                          <i ng-if="$log.ReturnCode != 'SUCCESS' && $log.ReturnCode != 'FAILURE'" class="glyphicon glyphicon-exclamation-sign"></i>
                        </h4>
                      </div>
                      <div id="osdm{{$id}}" class="panel-collapse collapse">
                        <div class="panel-body">
                          <table class="table">
                            <tr>
                              <th>State</th>
                              <td>{{$log.State}}</td>
                            </tr>
                            <tr>
                              <th>Return Value</th>
                              <td>{{$log.ReturnValue}}</td>
                            </tr>
                            <tr>
                              <th>Return Code</th>
                              <td>{{$log.ReturnCode}}</td>
                            </tr>
                            <tr>
                              <th>Number of No Inputs</th>
                              <td>{{$log.NumberNoInputs}}</td>
                            </tr>
                            <tr>
                              <th>Number of Invalids</th>
                              <td>{{$log.NumberInvalids}}</td>
                            </tr>
                            <tr>
                              <th>Input Mode</th>
                              <td>{{$log.InputMode}}</td>
                            </tr>
                            <tr>
                              <th>Failure Reason</th>
                              <td>{{$log.FailureReason}}</td>
                            </tr>
                            <tr>
                              <th>Credit Card Type</th>
                              <td>{{$log.CreditCardType}}</td>
                            </tr>
                            <tr>
                              <th>Confidence Score</th>
                              <td>{{$log.ConfidenceScore}}</td>
                            </tr>
                            <tr>
                              <th>No to Confirm</th>
                              <td>{{$log.NoToConfirm}}</td>
                            </tr>
                            <tr>
                              <th>Repeat Count</th>
                              <td>{{$log.RepeatCount}}</td>
                            </tr>
                            <tr>
                              <th>Help Count</th>
                              <td>{{$log.HelpCount}}</td>
                            </tr>
                            <tr>
                              <th>Time Stamp</th>
                              <td>{{$log.TimeStamp}}</td>
                            </tr>
                             <tr>
                              <th>Semantic Id</th>
                              <td>{{$log.SemanticId}}</td>
                            </tr>
                             <tr>
                              <th>SSM Score</th>
                              <td>{{$log.SsmScore}}</td>
                            </tr>
                             <tr>
                              <th>Utterance</th>
                              <td>{{$log.Utterance}}</td>
                            </tr>
                          </table>
                         </div>
                      </div>
                    </div>
                  </div>
                </td>
                <td>
                  <div class="panel-group" id="LegacyAccordion">
                    <div class="panel panel-default" ng-repeat="$log in legacyLogs">
                      <div class="panel-heading">
                        <h4 class="panel-title">
                          <a data-toggle="collapse" data-parent="#LegacyAccordion" href="#legacy{{$id}}">{{$log.DataAction}}</a>
                          <i ng-if="$log.Status == 'FAILURE'" class="glyphicon glyphicon-remove"></i> 
                          <i ng-if="$log.Status != 'SUCCESS' && $log.Status != 'FAILURE'" class="glyphicon glyphicon-exclamation-sign"></i>
                          <span class="pull-right"><strong title="PLAID" ng-if="$log.DataSource == 'PlaidAccess'">P</strong>&nbsp;<i class="glyphicon glyphicon-time"></i>{{$log.ElapsedTime}}</span>
                        </h4>
                      </div>
                      <div id="legacy{{$id}}" class="panel-collapse collapse">
                        <div class="panel-body">
                          <table class="table">
                            <tr>
                              <th>State</th>
                              <td>{{$log.State}}</td>
                            </tr>
                            <tr>
                              <th>Data Source</th>
                              <td>{{$log.DataSource}}</td>
                            </tr>
                            <tr>
                              <th>Data Action</th>
                              <td>{{$log.DataAction}}</td>
                            </tr>
                            <tr>
                              <th>Elapsed Time</th>
                              <td>{{$log.ElapsedTime}}</td>
                            </tr>
                            <tr>
                              <th>Status</th>
                              <td>{{$log.Status}}</td>
                            </tr>
                            <tr>
                              <th>Error Message</th>
                              <td>{{$log.ErrorMessage}}</td>
                            </tr>
                            <tr>
                              <th>Time Stamp</th>
                              <td>{{$log.TimeStamp}}</td>
                            </tr>
                            <tr ng-if="$log.plaidEntrys">
                               <th colspan="2">Plaid Entries</th>
                            </tr>
                            <tr ng-if="$log.plaidEntrys">
                              <td colspan="2" style="background: #DDDDDD;">
                                <table class="table">
                                  <tr ng-repeat-start="plaidEntry in $log.plaidEntrys">
                                    <th>{{plaidEntry.EntryType}}</th>
                                  </tr>
                                  <tr>
                                    <td><a href="{{plaidEntry.Info}}" target="_blank">{{plaidEntry.Info}}</a></td>
                                  </tr>
                                  <tr ng-if="plaidEntry.DataType">
                                    <th>{{plaidEntry.DataType}}</th>
                                  </tr>
                                  <tr ng-repeat-end="" ng-if="plaidEntry.DataType">
                                    <td>
                                      <div class="output" dd-bind-html="plaidEntry.Data"></div>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                         </div>
                      </div>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <% if(Utils.isDev(request)) { %>
          <div class="tab-pane fade" id="traceLog" style="overflow: auto;">
            <iframe class="callTraceFrame" ng-src="{{traceURL}}"></iframe>
            <!-- table class="table table-bordered table-hover">
              <tr>
                <th>SessionId</th>
                <th>DateStamp</th>
                <th>IVRHost</th>
                <th>WebServiceHost</th>
                <th>ETN</th>
                <th>ANI</th>
                <th>DNIS</th>
                <th>Call Reason</th>
                <th>Need Directive</th>
                <th>InSessionData</th>
                <th>OutWorkflow</th>
                <th>OutSessionData</th>
                <th>OutErrorMessage</th>
                <th>DebugString</th>
                <th>ErrorString</th>
              </tr>
            </table -->
          </div>
          <% } %>
        </div>
      </div>
  
      <script src="js/jquery.js"></script>
      <script src="js/lodash.js"></script>
      <script src="js/bootstrap.js"></script>
      <script src="js/angular.js"></script>
      <script src="js/restangular.js"></script>
      <script src="js/ddi/ui.spinner.js"></script>
      <script src="js/ddi/ddi.rest.js"></script>
      <script src="js/ddi/ui.table.v3.js"></script>
      <script src="js/ddi.js"></script>

    </div>
  </body>
</html>