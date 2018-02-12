exports.doZip =function(req, res) {
  console.log('routes/doZip');
  var zipArchiver = require("../models/zipArchiver");

  zipArchiver.serverFunctionality(res);  
};