var maps_gmaps = function () {

    var handleGeocoding = function () {
        var map_geocoding;
        map_geocoding = new GMaps({
            el: '#map-geocoding',
            lat: -12.093965,
            lng: -77.028333
        });
        $('#geocoding_search').click(function (e) {
            e.preventDefault();
            GMaps.geocode({
                address: $('#address').val().trim(),
                callback: function (results, status) {
                    if (status == 'OK') {
                        var latlng = results[0].geometry.location;
                        map_geocoding.setCenter(latlng.lat(), latlng.lng());
                        map_geocoding.addMarker({
                            lat: latlng.lat(),
                            lng: latlng.lng()
                        });
                    }
                }
            });
        });
    }
    var handleMapBasic = function () {
        var map_basic = new GMaps({
            el: '#map-basic',
            lat: -12.043333,
            lng: -77.052333,
            zoomControl: true,
            zoomControlOpt: {
                style: 'SMALL',
                position: 'TOP_LEFT'
            },
            panControl: false,
            streetViewControl: false,
            mapTypeControl: false,
            overviewMapControl: false
        });
    }
    var handleContextmenu = function () {
        var map_context = new GMaps({
            el: '#map-context',
            lat: -12.003333,
            lng: -77.098333
        });
        map_context.setContextMenu({
            control: 'map',
            options: [{
                title: 'Add marker',
                name: 'add_marker',
                action: function (e) {
                    console.log(e.latLng.lat());
                    console.log(e.latLng.lng());
                    this.addMarker({
                        lat: e.latLng.lat(),
                        lng: e.latLng.lng(),
                        title: 'New marker'
                    });
                    this.hideContextMenu();
                }
            }, {
                title: 'Center here',
                name: 'center_here',
                action: function (e) {
                    this.setCenter(e.latLng.lat(), e.latLng.lng());
                }
            }]
        });
        map_context.setContextMenu({
            control: 'marker',
            options: [{
                title: 'Center here',
                name: 'center_here',
                action: function (e) {
                    this.setCenter(e.latLng.lat(), e.latLng.lng());
                }
            }]
        });
    }
    var handlemapRoutes = function () {
        var map_routes = new GMaps({
            el: '#map-routes',
            lat: -12.043333,
            lng: -77.028333
        });
        map_routes.travelRoute({
            origin: [-12.044012922866312, -77.02470665341184],
            destination: [-12.090814532191756, -77.02271108990476],
            travelMode: 'driving',
            step: function (e) {
                $('#instructions').append('<li>' + e.instructions + '</li>');
                $('#instructions li:eq(' + e.step_number + ')').delay(450 * e.step_number).fadeIn(200, function () {
                    map_routes.drawPolyline({
                        path: e.path,
                        strokeColor: '#065DA1',
                        strokeOpacity: 0.6,
                        strokeWeight: 6
                    });
                });
            }
        });
    }
    var handleMapTravel = function () {
        var map;
        var route;

        $('#forward').attr('disabled', 'disabled');
        $('#back').attr('disabled', 'disabled');
        $('#get_route').click(function (e) {
            e.preventDefault();
            map.getRoutes({
                origin: [map.markers[0].getPosition().lat(), map.markers[0].getPosition().lng()],
                destination: [map.markers[map.markers.length - 1].getPosition().lat(), map.markers[map.markers.length - 1].getPosition().lng()],
                callback: function (e) {
                    route = new GMaps.Route({
                        map: map,
                        route: e[0],
                        strokeColor: '#336699',
                        strokeOpacity: 0.5,
                        strokeWeight: 10
                    });
                    $('#forward').removeAttr('disabled');
                    $('#back').removeAttr('disabled');
                }
            });
            $('#forward').click(function (e) {
                e.preventDefault();
                route.forward();

                if (route.step_count < route.steps_length)
                    $('#steps').append('<li>' + route.steps[route.step_count].instructions + '</li>');
            });
            $('#back').click(function (e) {
                e.preventDefault();
                route.back();

                if (route.step_count >= 0)
                    $('#steps').find('li').last().remove();
            });
        });
        map = new GMaps({
            el: '#map-travel',
            lat: -12.043333,
            lng: -77.028333,
            zoom: 16,
            height: '500px',
            click: function (e) {
                map.addMarker({
                    lat: e.latLng.lat(),
                    lng: e.latLng.lng()
                });
            }
        });
    }
    return {
        init: function () {
            handleGeocoding();
            handleMapBasic();
            handleContextmenu();
            handlemapRoutes();
            handleMapTravel();
        }
    }
}(jQuery);