var http = require('http');
var how2j = require('./how2j');
how2j.hi();
var server = http.createServer(how2j.service);
server.listen(8088);
