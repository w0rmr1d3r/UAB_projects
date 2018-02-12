var http = require('http'), //http module
    archiver = require('archiver'), //archiver module
    fs = require('fs'); //file system module 

/**
 * Function that pipes (sends) all the InputStreams in the streams parameter
 * to a ZipOutputStream connected to the HTTP Response Stream.
 * @streams Array with the InputStreams to be zipped. 
 * @response OutputStream connected to the HTTP Response.
 */
function sendFilesInAZip(streams, response){
  var zipOS = archiver('zip');
  //when compression finishes we execute the anonymous func. passed as a 
  //parameter. 
  zipOS.on('finish', function(){
    console.log('Zip file has been finalized. Zip  size in bytes: ' + zipOS.pointer());
    //closing the OutputStream connected to the HTTP Response.
    response.end();
  }); 

  //piping (connecting) the zip to the HTTP response output stream.
  zipOS.pipe(response);

  //for each InputStream to be zipped we add an entry into the 
  //zip archive associating an entry name to the inputStream.
  streams.forEach(function(inputStream){
    //appending an entry in the archive from a FileInputStream
    zipOS.append(inputStream.stream, {name: inputStream.name});
  });

  // finalize the archive (we are done appending files but streams have to finish yet 
  // to be compressed)
  zipOS.finalize();

  console.log('Zip creation has been started.');
} //end funcition sendFilesInAZip


/**
 * Function that when receives an HTTP request replies with an HTTP 
 * response sending compressed in zip format the files 'file1.txt' and
 * 'file2.txt'
 */
function serverFunctionality(request, response){
  //sending HTTP Response headers
  response.writeHead(200, {'Content-Type': 'application/zip',
    'Content-Disposition' : 'attachment; filename=files.zip' });

  //specifying the file names to be zipped and sent
  var names = ['file1.txt','file2.txt'];

  //creating an input stream from each file. var streams will be an array
  //of inputStreams.
  var streams = names.map(function(fileName){
    var inputStream = {
      name: fileName,
      stream: fs.createReadStream(fileName)
    };

    return inputStream;

  });

  //sending all the inputStreams in the streams array to the ZipOutputStream
  //connected to the HTTP Response.
  sendFilesInAZip(streams, response);
} //end ServerFuncionality

http.createServer(serverFunctionality).listen(8080);
console.log('Server running at http://localhost:8080/');
