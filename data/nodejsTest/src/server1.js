var http=require('http');
var url=require('url');
var querystring=require('querystring');
function service(req,response){
	var arg = url.parse(req.url).query;
	var params = querystring.parse(arg);
	console.log("method - "+req.method);
	console.log("url - "+req.url);
	console.log("id- "+params.id);
	response.writeHead(200,{'Content-Type':'text/plain'});
	response.end('Hello Node.js aa');
}
var server = http.createServer(service);
server.listen(8088);
