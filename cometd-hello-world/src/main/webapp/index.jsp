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
  	if (!eXo.core.Cometd.isConnected()) {
    	eXo.core.Cometd.url = '/cometd-ksdemo/cometd' ;	
    	eXo.core.Cometd.exoId = '<%=request.getAttribute("userName")%>';
    	eXo.core.Cometd.exoToken = '<%=request.getAttribute("userToken")%>';
    	eXo.core.Cometd.addOnConnectionReadyCallback(subcribeNotification_<portlet:namespace/>);
    	eXo.core.Cometd.init();
  	} else {
  	  subcribeNotification_<portlet:namespace/>();
  	}
	}
	
	function subcribeNotification_<portlet:namespace/>()
	{
		eXo.core.Cometd.subscribe('/eXo/portal/notification', function(eventObj) {
		  var data = eXo.core.JSON.parse(eventObj.data);
		  eXo.core.Notification.addMessage(data.message);
		});
	}

</script>