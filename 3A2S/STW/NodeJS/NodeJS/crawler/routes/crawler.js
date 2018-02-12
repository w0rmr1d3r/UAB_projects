var express = require('express');
var router = express.Router();

/* what to do when the module is used */
router.get('/', function(req, res) {
  console.log("INFO >>> EN CRAWLER CONTROLLER");
  //TODO-3 
  //load the model module
  var model = require('../models/crawler');
  
  //TODO-4
  //define the var to store de link
  console.log("URL REQ");
  console.log(req.query.url);
  var uri = 'http://' + req.query.url;
  console.log("FINAL URI");
  console.log(uri);

  //TODO-5
  var myCallback = function (error, images_details) {
  	
  	if (error === -1) {
  		console.log("CODIGO -1, IMAGENES NO ENCONTRADAS");
  		var options = {root: '../views/'};
  		res.sendFile('notFound.html', options);
  	} else if (error === 0){
  		console.log("CODIGO CORRECTO");
  		res.render('crawler.ejs', { details : images_details } );  		
  	} else {
		console.log("CODIGO NO DEFINIDO");
  	}
  }

  model.doCrawlAndDownload(uri, myCallback);

}); //end router.get

//exporting the controller
module.exports = router;
