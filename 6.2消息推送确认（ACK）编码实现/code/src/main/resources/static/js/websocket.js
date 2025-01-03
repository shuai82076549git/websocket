var stompClient = null;
var wsCreateHandler = null;
var userId = null;

function connect() {
	var host = window.location.host; // 带有端口号
	userId =  GetQueryString("userId");
	var socket = new SockJS("http://" + host + "/websocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({"receipt": "message-124"}, function (frame) {
		stompClient.onreceipt = function(receipt) {
			console.log("Received receipt for message with id:", receipt.headers["receipt-id"]);
		};
		writeToScreen("connected: " + frame);

		//1./exchange/<exchangeName>
		// stompClient.subscribe("/exchange/sendToUser/user" + userId, function (response) {
		// 		writeToScreen(response.body);
		// 	});

		//2./queue/<queueName>
		stompClient.subscribe("/topic/user" + userId, function (response) {
			var messageId = response.headers['message-id'];
			var subscription = response.headers['subscription'];
			console.log("Received message-id:", messageId);
			console.log("Received subscription:", subscription);

			writeToScreen(response.body);
			//response.ack();
			//response.ack(messageId);
			stompClient.ack(messageId,subscription); // 发送 ACK 确认消息
		}, {receipt: 'sub-11111',ack:'client-individual'});


		stompClient.subscribe("/topic/chat", function (response) {
			writeToScreen(response.body);
		});

		}, function (error) {
			wsCreateHandler && clearTimeout(wsCreateHandler);
			wsCreateHandler = setTimeout(function () {
				console.log("重连...");
				connect();
				console.log("重连完成");
			}, 1000);
		}
	)
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	writeToScreen("disconnected");
}

function writeToScreen(message) {
	if(DEBUG_FLAG)
	{
		$("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
	}
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
	var context = "";
	if (r != null)
		context = r[2];
	reg = null;
	r = null;
	return context == null || context == "" || context == "undefined" ? "" : context;
}
