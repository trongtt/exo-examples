/**
 * Backbone localStorage Adapter
 * https://github.com/jeromegn/Backbone.localStorage
 */

(function() {
	// localSync delegate to the model or collection's
	// *localStorage* property, which should be an instance of `Store`.
	// window.Store.sync and Backbone.localSync is deprectated, use
	// Backbone.LocalStorage.sync instead
	Backbone.sync = function(
			method, model, options, error) {

		// Backwards compatibility with Backbone <= 0.3.3
		if (typeof options == 'function') {
			options = {
				success : options,
				error : error
			};
		}

		var resp;

		switch (method) {
		case "read":
			$.ajax({
				url: todoServerURL,
				type: 'POST',
				data:{'method':'read'},
				dataType:'json',
				async:'false',
				success:function(data, textStatus, jqXHR) {
					if(data) {
						resp = data;
						options.add=true;
						options.success(resp);
					}
				},
				error:function(jqXHR, textStatus, errorThrow) {
					error("Record not found");
				}
			});
			break;
		case "create":
			$.ajax({
				url: todoServerURL,
				type: 'POST',
				data:{'method':'create','todocontent':JSON.stringify(model)},
				dataType:'json',
				async:'false',
				success:function(data, textStatus, jqXHR) {
					model.set({'id' : data.id});
					resp = model.toJSON();
					options.success(resp);
				},
				error:function(jqXHR, textStatus, errorThrow) {
					error("Record not found");
				}
			});
			
			break;
		case "update":
			if(model.editing != true) {
				$.ajax({
					url: todoServerURL,
					type: 'POST',
					data:{'method':'update','todocontent':JSON.stringify(model)},
					dataType:'json',
					async:'false',
					success:function(data, textStatus, jqXHR) {
						resp = model.toJSON();
						options.success(resp);
					},
					error:function(jqXHR, textStatus, errorThrow) {
						error("Record not found");
					}
				});
			}
			break;
		case "delete":
			$.ajax({
				url: todoServerURL,
				type: 'POST',
				data:{'method':'delete','todocontent':JSON.stringify(model)},
				dataType:'json',
				async:'false',
				success:function(data, textStatus, jqXHR) {
				},
				error:function(jqXHR, textStatus, errorThrow) {
					error("Record not found");
				}
			});
			break;
		}
	};

})();
