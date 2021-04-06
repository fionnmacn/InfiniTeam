/*
  FUNCTIONS THAT RESHAPE THE DATA FROM
  THE RETAILER's OWN DATABASE
 */

Parse.Cloud.define('restdb', (request) => {

  return Parse.Cloud.httpRequest({
    method: 'GET',
    url: 'https://retailer01-d282.restdb.io/rest/products',
    headers: {
      'cache-control': 'no-cache',
      'x-apikey': '3251ec36c8eb8e5e6a1dae5f9bd1729198c9a'
    }
  }).then(function (httpResponse) {
    // success
    console.log('Request succeeded: ' + httpResponse.text);

    var json = JSON.parse(httpResponse.text);
    var data = [];

    for (var i = 0; i < json.length; i++) {
      var product = {
        id: json[i].id,
        name: json[i].name,
        description: json[i].description,
        extra1: json[i].country,
        extra2: json[i].match,
        price: json[i].price,
        image: json[i].image
      };
      data.push(product);
    }

    return data;
  }, function (httpResponse) {
    // error
    console.error('Request failed with response code ' + httpResponse.status);
  });
});

Parse.Cloud.define('updateStock', (request) => {

  var product_id = "606b87f2f969667c00007aca";
  var store_name = request.params.name;
  var new_stock = parseInt(request.params.reserve);
  var update_url = "https://retailer01-d282.restdb.io/rest/stock/" + product_id;

  var request = require("request");

  var options = { method: 'PUT',
    url: update_url,
    headers: 
    { 'cache-control': 'no-cache',
      'x-apikey': '3251ec36c8eb8e5e6a1dae5f9bd1729198c9a',
      'content-type': 'application/json' },
    body: { id: "new", [store_name]: [new_stock] },
    json: true };

  request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(response);
  });

});

Parse.Cloud.define('products', (request) => {
  
  var current_store = request.params.current_store;
  var store_pos = parseInt(request.params.store_pos);
  
  return Parse.Cloud.httpRequest({
    method: 'GET',
    url: 'https://api.jsonbin.io/b/6062646df6757843ce715904/9'
  }).then(function (httpResponse) {
    // success
    console.log('Request succeeded: ' + httpResponse.text);

    var json = JSON.parse(httpResponse.text);
    var data = [];

    for (var i = 0; i < json.products.length; i++) {
      var product = {
        id: json.products[i].id,
        name: json.products[i].name,
        description: json.products[i].description,
        extra1: json.products[i].country,
        extra2: json.products[i].match,
        price: json.products[i].price,
        stock: json.products[i].stock[store_pos][current_store],
        image: json.products[i].image
      };
      data.push(product);
    }

    return data;
  }, function (httpResponse) {
    // error
    console.error('Request failed with response code ' + httpResponse.status);
  });
});

Parse.Cloud.define('retrieveStores', (request) => {

  var data = ["Navan Road", "Glasnevin", "Citywest", "Beacon", "Tallaght", "Limerick City", "Bray", "Howth", "Kildare Village", "Tralee", "Cork North"];
  return data;
});

Parse.Cloud.define('stock', (request) => {
  
  var search = request.params.id;

  return Parse.Cloud.httpRequest({
    method: 'GET',
    url: 'https://api.jsonbin.io/b/6062646df6757843ce715904/8'
  }).then(function (httpResponse) {
    // success
    console.log('Request succeeded: ' + httpResponse.text);

    var json = JSON.parse(httpResponse.text);
    var data = [];

    for (var i = 0; i < json.products.length; i++) {
      for (var j = 0; j < json.products[i].stock.length; j++) {
        if (json.products[i].id == search) {
          var product = {
            "Navan Road": json.products[i].stock[j]["Navan Road"],
            "Glasnevin": json.products[i].stock[j]["Glasnevin"],
            "Citywest": json.products[i].stock[j]["Citywest"],
            "Beacon": json.products[i].stock[j]["Beacon"],
            "Tallaght": json.products[i].stock[j]["Tallaght"],
            "Limerick City": json.products[i].stock[j]["Limerick City"],
            "Bray": json.products[i].stock[j]["Bray"],
            "Howth": json.products[i].stock[j]["Howth"],
            "Kildare Village": json.products[i].stock[j]["Kildare Village"],
            "Tralee": json.products[i].stock[j]["Tralee"],
            "Cork North": json.products[i].stock[j]["Cork North"]
          };
          data.push(product);
        }
    
      }
    }

    return data;
  }, function (httpResponse) {
    // error
    console.error('Request failed with response code ' + httpResponse.status);
  });
});

/*
  CLOUD CODE FUNCTIONS THAT ARE CALLED FROM THE APP
 */

Parse.Cloud.define("resetPassword", function (request, response) {
  Parse.Cloud.useMasterKey();
  var username = request.params.username;
  var password = request.params.password;
  
  var objUser = Parse.Object.extend("User");
  var query = new Parse.Query(objUser);
  query.equalTo("username", username);
  query.find().then(function (resultObj) {
      if (resultObj.length > 0) {
          var userObj = resultObj[0];
        userObj.set("password", password);
        return userObj.save({}, {useMasterKey: true});
      } else {
        response.error("User does not exist");
      }

  }).then(function () {
      console.log("Password changed successfully.");

  }, function (error) {
      console.error("Error " + error);
  });
});

/*
  CLOUD JOBS THAT WILL RUN AT A SET INTERVAL
 */

Parse.Cloud.job('deleteOldTasks', function(request, status) {
  var today = new Date();
  var numOfDays = 30;
  var timeInMs = (numOfDays * 24 * 3600 * 1000);
  var dateLimit = new Date(today.getTime() - (timeInMs));

  var query = new Parse.Query('Tasks');
  query.lessThan('createdAt', dateLimit);
  query.each(function(record) {
      return record.destroy({useMasterKey:true});
  },
  {useMasterKey:true}).then(function(result) {
      console.log("Successfully deleted Tasks older than 30 days.");

  }, function(error) {
      alert("Error performing deleteOldTasks: " + error.message);
  });
});

Parse.Cloud.job('deleteOldNotices', function(request, status) {
  var today = new Date();
  var numOfDays = 90;
  var timeInMs = (numOfDays * 24 * 3600 * 1000);
  var dateLimit = new Date(today.getTime() - (timeInMs));

  var query = new Parse.Query('Notices');
  query.lessThan('createdAt', dateLimit);
  query.each(function(record) {
      return record.destroy({useMasterKey:true});
  },
  {useMasterKey:true}).then(function(result) {
      console.log("Successfully deleted Notices older than 90 days.");

  }, function(error) {
      alert("Error performing deleteOldNotices: " + error.message);
  });
});

Parse.Cloud.job('deleteOldShifts', function(request, status) {
  var today = new Date();
  var numOfDays = 1095;
  var timeInMs = (numOfDays * 24 * 3600 * 1000);
  var dateLimit = new Date(today.getTime() - (timeInMs));

  var query = new Parse.Query('Shifts');
  query.lessThan('createdAt', dateLimit);
  query.each(function(record) {
      return record.destroy({useMasterKey:true});
  },
  {useMasterKey:true}).then(function(result) {
      console.log("Successfully deleted Shifts older than 1095 days (~3 years).");

  }, function(error) {
      alert("Error performing deleteOldShifts: " + error.message);
  });
});


// Parse.Cloud.define('test', (request) => {
  
//     return Parse.Cloud.httpRequest({
//       method: 'GET',
//       url: 'https://api.jsonbin.io/b/604675027ea6546cf3d6d351'
//     }).then(function (httpResponse) {
//       // success
//       console.log('Request succeeded: ' + httpResponse.text);

//       //var obj = JSON.parse(httpResponse.text);
//       //var product = obj.products[0].name;

//       return httpResponse.text;
//     }, function (httpResponse) {
//       // error
//       console.error('Request failed with response code ' + httpResponse.status);
//     });
// });

// Parse.Cloud.define('breaks', (request) => {
  
//   return Parse.Cloud.httpRequest({
//     method: 'GET',
//     url: 'https://api.jsonbin.io/b/604675027ea6546cf3d6d351'
//   }).then(function (httpResponse) {
//     // success
//     console.log('Request succeeded: ' + httpResponse.text);

//     var obj = JSON.parse(httpResponse.text);
//     var product = {
//       "name": obj.products[0].name
//     };

//     return product;
//   }, function (httpResponse) {
//     // error
//     console.error('Request failed with response code ' + httpResponse.status);
//   });
// });