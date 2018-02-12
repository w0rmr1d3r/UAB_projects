/**
 * Client endPoint
 */
// Defining the serverEnd point of the websocket connection.
//TODO var wsUri = 
var wsUri = "ws://localhost:8080/WeatherForecast/WeatherForecast/";
var connection;

/**
 * Function that creates a new websocket connection to the URI serverEndPoint
 * specified in the global variable wsUri if the browser supports websockets
 * technology. After creating the connection to the serverEndPoint each
 * connection event: onopen, onclose, onmessage, onerror; has to be handled.
 * Each event is associated to an anonymous function that handles the event.
 */
function initWS() {
	console.log("In initWS");
	if ("WebSocket" in window) {
		//TODO
		connection = new WebSocket(wsUri);
		console.log("Websocket created");
		
		connection.onopen = function(evt) {
			console.log("Info: onOpen");
			onOpen(evt);
		};
		connection.onclose = function(evt) {
			console.log("Info: onClose");
			onClose(evt);
		};
		connection.onmessage = function(evt) {
			console.log("Info: onMessage");
			onMessage(evt);
		};
		connection.onerror = function(evt) {
			console.log("Error: onError");
			onError(evt);
		};
	} else {
		// The browser doesn't support WebSocket
		alert("WebSocket NOT supported!");
	}
}

/**
 * This function is called when the connection to the serverEndPoint is
 * established.
 * 
 * @param evt the event that contains the connection data.
 */
function onOpen(evt) {
}

/**
 * This function is called when the connection to the serverEndPoint is closed.
 * 
 * @param evt the event that contains the disconnection data.
 */
function onClose(evt) {
}

/**
 * This function is called when a message is received by the client end point
 * 
 * @param evt Event that contains the message data. Use evt.data to get the
 * message.
 */
function onMessage(evt) {
	//TODO	
	console.log("En onMessage");
	var currentAlerts = $("#textareaID").html();
	$("#textareaID").html(currentAlerts + "\n" + evt.data);
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
//TODO 

$(document).ready(function(){
	
	initWS();
	
	});
