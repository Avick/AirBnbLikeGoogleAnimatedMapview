# AirBnbLikeGoogleAnimatedMapview

Used 

- Google Map API.

Features
-----
* App is shows mapview with a horizontal list.

* used viewpager for horizontal list.

* used google mapview to show the marker on the map.

* make tranisition from one location to another using 
```JAVA
CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(endLatLng)
                        .bearing(0)
                        //.bearing(bearingL + BEARING_OFFSET)
                        //.tilt(90)
                        .zoom(mMap.getCameraPosition().zoom)
                        .build();


mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                ANIMATE_SPEEED_TURN,
                null);
                
```



