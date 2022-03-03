
var xhr = new XMLHttpRequest();
var cords;
var map;

xhr.onload = function () {
  if (xhr.readyState === 4 && xhr.status !== 400) {
      cords = JSON.parse(xhr.responseText);
      console.log(cords[0][0], cords[0][1]);
      createMap(cords);
      addMapMarkers();
      addView();
      transformPosition();
  }
};

xhr.open(
  "GET",
  "/Personalized_Health/getLocDoctors?"
);
xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xhr.send(null);




function createMap() {
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    map.addLayer(new OpenLayers.Layer.Markers("Markers"));
}
function addMapMarkers() {
    var size = new OpenLayers.Size(21, 25);
    var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
    var icon = new OpenLayers.Icon(
      "../assets/marker.png",
      size,
      offset
    );
    for(let i = 0; i < cords.length; i ++) {
        var markers = new OpenLayers.Layer.Markers("Markers");
        map.addLayer(markers);

        var position = transformPosition(cords[i][1], cords[i][0]);
        var mar = new OpenLayers.Marker(position);
        markers.addMarker(mar);
    }
    map.setCenter(transformPosition(cords[0][1], cords[0][0]), 2);
  }

function  addView() {
    map.setCenter(transformPosition(cords[0][1], cords[0][0]), 2);
  }

function transformPosition(lon, lat) {
    var fromProj = new OpenLayers.Projection("EPSG:4326");
    var toProj = new OpenLayers.Projection("EPSG:900913");
    return new OpenLayers.LonLat(lon, lat).transform(
      fromProj,
      toProj
    );
  }