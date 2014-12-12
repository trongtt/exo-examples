<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<div>
	
  <p>
    Click <a href="#" onclick="init_<portlet:namespace/>();">here</a> to init cometd processing
  </p>
  
	<p>
		Send message:
		<input type="text" id="msg_<portlet:namespace/>" value="Hello World"/> <a href="#" onclick="send_<portlet:namespace/>();">send</a>
		
	</p>

  <div id="display_msg_<portlet:namespace/>"></div>
</div>

<script type="text/javascript">
	
	function send_<portlet:namespace/>() {
		var msg = document.getElementById("msg_<portlet:namespace/>").value;
		
		var query = "message=" + msg;
		var url = "<portlet:actionURL />".replace(/&amp;/g, "&");
		var request = new eXo.portal.AjaxRequest('POST', url, query);
		request.process();
		
	}
	
	function init_<portlet:namespace/>()
	{
	  require(["SHARED/commons-cometd"],function(cometd) {
    	if (!cometd.isConnected()) {
      	cometd.url = '/cometd/cometd' ;	
      	cometd.exoId = '<%=request.getAttribute("userName")%>';
      	cometd.exoToken = '<%=request.getAttribute("userToken")%>';
      	cometd.addOnConnectionReadyCallback(subcribeNotification_<portlet:namespace/>);
      	cometd.init();
    	} else {
    	  subcribeNotification_<portlet:namespace/>();
    	}
	  });
	}
	
	function subcribeNotification_<portlet:namespace/>()
	{
	  require(["SHARED/commons-cometd"],function(cometd) {
  		cometd.subscribe('/eXo/portal/notification', function(eventObj) {
  		  document.getElementById('display_msg_<portlet:namespace/>').innerHTML = document.getElementById('display_msg_<portlet:namespace/>').innerHTML + "<br/>" + eventObj.data;
  		});
	  });
	}

</script>