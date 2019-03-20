var http=require('http');
function service(request,response){
	response.writeHead(200,{'Content-Type':'text/plain'});
	response.end('Hello Node.js hahah');
}
var server = http.createServer(service);
server.listen(8088);
