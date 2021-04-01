Parse.Cloud.define('products', (request) => {
  
  return Parse.Cloud.httpRequest({
    method: 'GET',
    url: 'https://api.jsonbin.io/b/6062646df6757843ce715904/8'
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
        stock: json.products[i].stock[0]["Navan Road"],
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

Parse.Cloud.define('test', (request) => {
  
    return Parse.Cloud.httpRequest({
      method: 'GET',
      url: 'https://api.jsonbin.io/b/604675027ea6546cf3d6d351'
    }).then(function (httpResponse) {
      // success
      console.log('Request succeeded: ' + httpResponse.text);

      //var obj = JSON.parse(httpResponse.text);
      //var product = obj.products[0].name;

      return httpResponse.text;
    }, function (httpResponse) {
      // error
      console.error('Request failed with response code ' + httpResponse.status);
    });
});

Parse.Cloud.define('breaks', (request) => {
  
  return Parse.Cloud.httpRequest({
    method: 'GET',
    url: 'https://api.jsonbin.io/b/604675027ea6546cf3d6d351'
  }).then(function (httpResponse) {
    // success
    console.log('Request succeeded: ' + httpResponse.text);

    var obj = JSON.parse(httpResponse.text);
    var product = {
      "name": obj.products[0].name
    };

    return product;
  }, function (httpResponse) {
    // error
    console.error('Request failed with response code ' + httpResponse.status);
  });
});