(function(Backbone, _) {
	var Todo = Backbone.Model.extend({
		defaults : {
			'job' : '',
			'completed' : false,
			'editing' : false,
			'display' : true,
			'priority' : 0
		},

		/** @expose */
		toggle : function(options) {
			var opt = {
				'completed' : !this.get('completed')
			};
			_.extend(opt, options);
			this.save(opt, {wait:true});
		},

		/** @expose */
		finishEdit : function(job, priority) {
			this.save({
				'job' : job,
				'priority' : priority
			},{wait:true});
		},

		/** @expose */
		tryEdit : function(isEditing) {
			this.set({
				'editing' : isEditing
			});
		},

		/** @expose */
		setDisplay : function(display) {
			this.set({
				'display' : display
			});
		}
	});

	var TodoList = Backbone.Collection.extend({

		model : Todo,

		// localStorage : new Backbone.LocalStorage('todoPortlet'),

		initialize : function() {
			this.on('add change:completed', function(todo) {
				if (this.filterParam === 'active' && todo.get('completed')
						|| this.filterParam === 'completed'
						&& !todo.get('completed')) {
					todo.setDisplay(false);
				}
			}, this);
		},

		addTodo : function(job) {
			var priorityResult = job.match(/^![0-3]\s/);

			if (priorityResult) {
				job = job.replace(priorityResult, '');
				if(job == ''){
					job = new String(priorityResult);
    				job = job.replace(/^\s+|\s+$/g,'');
					priorityResult = new Number(0);
				} else {
					priorityResult = priorityResult.toString().match(/[0-3]/);
					priorityResult = new Number(priorityResult);
				}
			} else {
				priorityResult = new Number(0);
			}

			return this.create({
				'job' : job,
				'priority' : priorityResult
			});
		},

		completed : function() {
			return this.filter(function(todo) {
				return todo.get('completed');
			});
		},

		active : function() {
			return this.without.apply(this, this.completed());
		},

		/** @expose */
		filterTodo : function(param) {
			if (param === 'active') {
				_.invoke(this.completed(), 'setDisplay', false);
				_.invoke(this.active(), 'setDisplay', true);
			} else if (param === 'completed') {
				_.invoke(this.active(), 'setDisplay', false);
				_.invoke(this.completed(), 'setDisplay', true);
			} else {
				_.invoke(this.models, 'setDisplay', true);
				param = '';
			}
			this.filterParam = param;
			this.trigger("filter");
		},

		clearCompleted : function() {
			var completedModels = this.completed();
			this.sync('deleteList', this, {
				wait : true
			});
			
			this.remove(completedModels);
			_.each(completedModels, function(model) {
					model.trigger('destroy');
			});
		},

		toggleAll : function(completed) {
			_.each(this.models, function(model) {
				model.set({
					'completed' : completed
				})
			});
			this.sync('updateList', this, {
				wait : true
			});
			this.trigger('changeTodoList:completed');
		}
	});

	return {
		'Todo' : Todo,
		'TodoList' : TodoList
	};
})(Backbone, _);