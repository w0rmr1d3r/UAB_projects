var http = require('http'), //http module
    fs2 = require('fs');

// TODO: implement object fsPlus here.
function fsPlus() {
	var total = 0;
	this.getTotal = function() { return total; };
	this.readdir = function(path, callback) { 
		this.onRead();

		var mycall = function (err, f) {
			total += f.length;
			callback(err, f);
		};

		fs2.readdir(path, mycall);
	};
	this.onRead = function() { console.log("fsPlus started"); };
}
fsPlus.prototype = fs2;

var fs = new fsPlus();

/**
 * Prints the output of a fs.readdir() call.
 * 
 * @param dir        A string containing the directory used as the first
 *                   parameter to the fs.readdir() call.
 *
 * @param files      The list of files returned by the fs.readdir() call.
 *
 * @param target     The expected value returned by the fs.total() call.
 *
 * @param response   The HTTPResponse object.
 *
 * This function prints to the server output:
 *
 *    (1) The directory dir.
 *    (2) The elements of the list files.
 *    (3) The value of target and the value of fs.total()
 *
 */
function listDirectory(dir, files, target, response) {
    response.write("<h2>Testing &quot;" + dir + "&quot;</h2>");

    if (files.length == 0) {
      response.write("Empty directory<br />");
    } else {
      files.forEach(function(file) {
        response.write(file);
        response.write("<br />");      
      }); 
    }

    response.write('<br /> Calling fs.getTotal() should return at least ' + 
      target + ' and returns ' + fs.getTotal() + ' => ');

    if (target == fs.getTotal()) {
        response.write('<span style="color: green">most likely OK!</span>');
    } else if (target < fs.getTotal()) {
        // The value of fs.getTotal() may ocasionally be larger 
        // than the value of target if multiple parallel HTTP requests
        // are made.
        //
        // However, this is unlikely in your case... so...
        response.write('<span style="color: orange">may be OK or may be not</span>');
    } else {
        response.write('<span style="color: red">NOT OK :-(</span>');
    }
}

function serverFunctionality(request, response) {
  //Sending HTTP Response headers
  response.writeHead(200, {'Content-Type': 'text/html'}); 
  response.write('<h1>Test Suit for fsPlus</h1>')

  var inicial = fs.getTotal();
  
  var dir = "."
  fs.readdir(dir, function(err, files){
    listDirectory(dir, files, inicial + 5, response);

    dir = "./folder1";
    fs.readdir(dir, function(err, files){
      listDirectory(dir, files, inicial + 5, response);      

    dir = "./folder2";
    fs.readdir(dir, function(err, files){
      listDirectory(dir, files, inicial + 5 + 2, response);            

    fs.readdir(dir, function(err, files){
      listDirectory(dir, files, inicial + 5 + 2 + 2, response);            

    dir = "."
    fs.readdir(dir, function(err, files){
      listDirectory(dir, files, inicial + 5 + 2 + 2 + 5, response);

      response.end();
    }); //end .
    }); //end folder2
    }); //end folder2
    }); //end folder1
  });  //end .
}  

http.createServer(serverFunctionality).listen(8081);

console.log('Server running at http://localhost:8081/');




