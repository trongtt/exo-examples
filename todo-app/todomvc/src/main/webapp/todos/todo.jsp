<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<script type="text/javascript">
<!--
	var todoServerURL= '<portlet:resourceURL id="getDataResourceURL"></portlet:resourceURL>';
//-->
</script>
<div class="TodoPortlet">
	<h1>todos</h1>
	<div class="TodoGrid">
		<div class="Header"></div>
		<div class="Main">
			<input class="ToggleAll" type="checkbox">
			<input class="NewTodo" placeholder="What needs to be done?">
			<ul class="TodoList"></ul>
	   </div>
		<div class="Footer">
			<script type="text/template" class="StatsTmpl">
				<span class="TodoCount"><strong>{{- active}}</strong> items left</span>
					<ul class="Filters">
						<li>
						<a class="{{=filter == '' ? 'selected' : ''}}" href="#/">All</a>
					</li>
					<li>
						<a class="{{=filter == 'active' ? 'selected' : ''}}" href="#/active">Active</a>
					</li>
					<li>
						<a class="{{=filter == 'completed' ? 'selected' : ''}}" href="#/completed">Completed</a>
					</li>
				</ul>
				<button class="ClearCompleted">Clear completed ({{- completed}})</button>
			</script>			
		</div>
	</div>
	<script type="text/template" class="ItemTmpl">
		<div class="view {{= completed ? 'completed' : ''}} {{= editing ? 'editing' : ''}} {{= display ? '' : 'hidden'}}">
			<div class="PriorityColor prio-{{-priority}}"></div>
     		<input class="toggle" type="checkbox" {{= completed ? 'checked' : ''}}/>
	  		<label>{{- job}}</label>
			<select class="PrioritySelector" style="{{= completed ? 'display: none;' : ''}}" title="priority">						
					<option value="0" {{= (priority == 0) ? 'selected="selected"' : ''}}>0</option>		
					<option value="1" {{= (priority == 1) ? 'selected="selected"' : ''}}>1</option>
					<option value="2" {{= (priority == 2) ? 'selected="selected"' : ''}}>2</option>
					<option value="3" {{= (priority == 3) ? 'selected="selected"' : ''}}>3</option>
			</select>
	  		<button class="destroy" title="delete"></button>
			<input class="Edit" value="{{- job}}"/>
		</div>
	</script>
</div>