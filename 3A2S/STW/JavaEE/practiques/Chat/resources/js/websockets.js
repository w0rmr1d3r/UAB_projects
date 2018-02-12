/**
 * Client endPoint
 */
var wsUri = "ws://localhost:8080/Chat/chat/";
var connection;
var browserSupport = ("WebSocket" in window) ? true : false;

function initiateChatSession() {
	console.log("INFO: In initiateChatSession");
	
	if (browserSupport) {
		wsUri += $('#personId').val();
		
		connection = new WebSocket(wsUri);
		console.log("Websocket created");
		
		connection.onopen = function(evt) {
			console.log("INFO: onOpen");
			onOpen(evt);
		};
		
		connection.onclose = function(evt) {
			console.log("INFO: onClose");
			onClose(evt);
		};
		
		connection.onmessage = function(evt) {
			console.log("INFO: onMessage");
			onMessage(evt);
		};
		
		connection.onerror = function(evt) {
			console.log("ERROR: onError");
			onError(evt);
		};
	} else {
		console.log("ERROR: BROWSER DOES NOT SUPPORT WEBSOCKET");
		alert("WebSocket NOT supported!");
	}
}

/**
 * This function is called when the connection to the serverEndPoint is
 * established.
 * 
 * @param evt
 *            the event that contains the connection data.
 */
function onOpen(evt) {
}

/**
 * This function is called when the connection to the serverEndPoint is closed.
 * 
 * @param evt
 *            the event that contains the disconnection data.
 */
function onClose(evt) {
}

/**
 * This function is called when a message is received by the client end point
 * 
 * @param evt
 *            Event that contains the message data. Use evt.data to get the
 *            message.
 */
function onMessage(evt) {
	console.log("INFO: Message received");
	var received_msg = JSON.parse(evt.data);
	switch(received_msg.code) {
		case "MESSAGE":
			console.log("MESSAGE WITH CODE 0 RECEIVED");
			addMsgToHistory(received_msg.user + "> " + received_msg.message);
			break;
		case "REFRESH":
			console.log("MESSAGE WITH CODE 1 RECEIVED");
			$("#hidden-refresh").click();
			break;
		case "CLOSE":
			console.log("MESSAGE WITH CODE 2 RECEIVED");
			$("#hidden-refresh").click();
			break;
		default:
			console.log("RECEIVED MESSAGE WITH UNKOWN CODE");
			break;
	}
}

/**
 * This function is called on a connection error.
 * 
 * @param evt
 *            the event that contains the error.
 */
function onError(evt) {
	console.log("WS connection error");
	connection.close();
}

function sendChatMessage() {
	console.log("INFO: In sendChatMessage");
	// JSON structure - {"code" : code, "user": user, "message": message}
	var chatobj = {};
	chatobj.code = 1;
	chatobj.user = $("#personName").val();
	chatobj.message = $("#message-to-send").val();

	if (connection == null) {
		console.log("ERROR: PROBLEM WITH SOCKET, INITIATE SESSION AGAIN");
		alert('problem with WebSocket, ' + 'please initiate session again');
	} else {
		console.log("Sending message");
		connection.send(JSON.stringify(chatobj));
		// Ahora reseteamos la box
		$("#message-to-send").val("");
		$("#message-to-send").focus();
		addMsgToHistory(chatobj.user + "> " + chatobj.message);
	}
}

function closeChatSession() {
	console.log("In closeChatSession");
	addMsgToHistory("The chat session has been closed");
	connection.close();
}

function addMsgToHistory(msg){
	console.log("INFO: Adding message to history");
	var chatHistory = $("#chat-history").html();
	$("#chat-history").html(chatHistory + msg + "<br />");
}

$(document).ready(function(){
	
	initiateChatSession();
	
	});
