Parse.Cloud.define("fetchProducts", async(request) => {
    var productID = request.params.productID;
    var locationID = request.params.locationID;
    
    Parse.Cloud.httpRequest({
        url: 'https://{username}:{password}@{shop}.myshopify.com/admin/api/2021-01/inventory_levels.json',
        params: {
            'inventory_item_ids' : productID,
            'location_ids' : locationID
        }
    }).then(function(httpResponse) {
        console.log(httpResponse.text);
        response.success("success from response.success>>>>");
    
    }, function(httpResponse){
        console.error('Request failed with response code ' + httpResponse.status);
        response.error(httpResponse.status);
    });
});