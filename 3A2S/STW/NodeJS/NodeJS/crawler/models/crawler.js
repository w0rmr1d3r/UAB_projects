var cheerio = require('cheerio');
var request = require('request');
var fs = require('fs');
var url = require('url');
var path = require('path');

function doCrawlAndDownload(_uri, callback){
  
  // TODO: definiu les següents variables:
  // var array dels detalls de les imatges = [];
  var imageDetail = [];
  // var comptador del nombre de imatges descarregades
  var imageDownloadedCount = 0;
  // var comptador del nombre d'imatges processades.
  var imageProcessedCount = 0;
  // var codi d'error a passar-li a la callback. 
  // Si hi ha error -1. Inicialment = 0;
  var errorCode = 0;

  //TODO-6 
  request(_uri, function(error, response, body){
    
    if(!error && response.statusCode == 200) { 
      //TODO-7
      //en la variable $ carrego aquest codi HTML
      $ = cheerio.load(body);
      
      //per cada tag <img>, afegirem al image detail
      $('img').each(function(i, elem) {
        var pathOfImage = url.resolve(_uri, $(this).attr('src'));
        var nameOfImage = path.basename(pathOfImage);
        var sizeOfImage = 0;

        console.log("EN EACH, VARIABLES GOOD");

        (function(imageName, imagePath, imageSize) {
          console.log("EN ANON FUNC");
          request
          .get(imagePath)
          .on('response', function(response) {
              if (response.statusCode == 200) {
                console.log("EN ANON FUNC >> STATUS CODE OK");
                request(imagePath).pipe(fs.createWriteStream(imageName));

                console.log("sizeOfImage RECEIVED >> " + sizeOfImage);
                sizeOfImage = response.headers['content-length'];

                console.log("SIZE OF IMG MODIFIED >> " + sizeOfImage);
                this.pipe(fs.createWriteStream('../public/images/' + imageName));

                imageDetail.push( { 'name' : nameOfImage, 'size' : sizeOfImage, 'path' : pathOfImage } );
                imageDownloadedCount++;

                if(imageDownloadedCount == imageProcessedCount) {
                      console.log("MODELS>CRAWLER imageDetail");
                      console.log(imageDetail);
                      callback(errorCode, imageDetail);
                }
              } else {
                imageProcessedCount--;
              }
          });
      })(nameOfImage, pathOfImage, sizeOfImage);

        imageProcessedCount++; 
      });

      //TODO-8
      //TODO-9
      //TODO-10
      //TODO-11
      // estan todos en la anon
    }else{
      //TODO-12
      errorCode = -1;
      callback(errorCode, imageDetail);
    }
      
  }); //end request 

} //end doCrawl

exports.doCrawlAndDownload = doCrawlAndDownload;
