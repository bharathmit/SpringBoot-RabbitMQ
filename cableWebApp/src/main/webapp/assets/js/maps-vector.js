var maps_vector = function () {
    var handleBasic = function () {
        $('#focus-single').click(function () {
            $('#world-map-gdp').vectorMap('set', 'focus', {region: 'AU', animate: true});
        });
        $('#focus-multiple').click(function () {
            $('#world-map-gdp').vectorMap('set', 'focus', {regions: ['AU', 'JP'], animate: true});
        });
        $('#focus-coords').click(function () {
            $('#world-map-gdp').vectorMap('set', 'focus', {scale: 7, lat: 35, lng: 33, animate: true});
        });
        $('#focus-init').click(function () {
            $('#world-map-gdp').vectorMap('set', 'focus', {scale: 1, x: 0.5, y: 0.5, animate: true});
        });
        $('#world-map-gdp').vectorMap({
            map: 'world_mill_en',
            backgroundColor: "#f3f3f9",
            series: {
                regions: [{
                    values: gdpData,
                    scale: ['#C8EEFF', '#0071A4'],
                    normalizeFunction: 'polynomial'
                }]
            },
            regionStyle: {
                hover: {
                    fill: '#968962',
                    "fill-opacity": 0.8,
                    cursor: 'pointer'
                },
                selected: {
                    fill: 'yellow'
                }
            }
            ,
            onRegionTipShow: function (e, el, code) {
                el.html(el.html() + ' (GDP - ' + gdpData[code] + ')');
            }
        });
    }
    var handleReverseProjection = function () {
        var map,
            markerIndex = 0,
            markersCoords = {};

        map = new jvm.Map({
            map: 'us_lcc_en',
            backgroundColor: "#f3f3f9",
            markerStyle: {
                initial: {
                    fill: 'red'
                }
            },
            regionStyle: {
                initial: {
                    fill: 'rgb(74, 159, 197)'

                },
                hover: {
                    fill: '#968962',
                    "fill-opacity": 0.8,
                    cursor: 'pointer'
                }
            },
            container: $('#map-reverse'),
            onMarkerTipShow: function (e, label, code) {
                map.tip.text(markersCoords[code].lat.toFixed(2) + ', ' + markersCoords[code].lng.toFixed(2));
            },
            onMarkerClick: function (e, code) {
                map.removeMarkers([code]);
                map.tip.hide();
            }
        });

        map.container.click(function (e) {
            var latLng = map.pointToLatLng(e.offsetX, e.offsetY),
                targetCls = $(e.target).attr('class');

            if (latLng && (!targetCls || (targetCls && $(e.target).attr('class').indexOf('jvectormap-marker') === -1))) {
                markersCoords[markerIndex] = latLng;
                map.addMarker(markerIndex, {latLng: [latLng.lat, latLng.lng]});
                markerIndex += 1;
            }
        });
    }
    var handleMarkers = function () {
        $('#world-map-markers').vectorMap({
            map: 'world_mill_en',
            scaleColors: ['#C8EEFF', '#0071A4'],
            normalizeFunction: 'polynomial',
            hoverOpacity: 0.7,
            hoverColor: false,
            markerStyle: {
                initial: {
                    fill: '#F8E23B',
                    stroke: '#383f47'
                }
            },
            regionStyle: {
                initial: {
                    fill: 'rgb(74, 159, 197)'

                },
                hover: {
                    fill: '#968962',
                    "fill-opacity": 0.8,
                    cursor: 'pointer'
                }
            },
            backgroundColor: '#f3f3f9',
            markers: [
                {latLng: [41.90, 12.45], name: 'Vatican City'},
                {latLng: [43.73, 7.41], name: 'Monaco'},
                {latLng: [-0.52, 166.93], name: 'Nauru'},
                {latLng: [-8.51, 179.21], name: 'Tuvalu'},
                {latLng: [43.93, 12.46], name: 'San Marino'},
                {latLng: [47.14, 9.52], name: 'Liechtenstein'},
                {latLng: [7.11, 171.06], name: 'Marshall Islands'},
                {latLng: [17.3, -62.73], name: 'Saint Kitts and Nevis'},
                {latLng: [3.2, 73.22], name: 'Maldives'},
                {latLng: [35.88, 14.5], name: 'Malta'},
                {latLng: [12.05, -61.75], name: 'Grenada'},
                {latLng: [13.16, -61.23], name: 'Saint Vincent and the Grenadines'},
                {latLng: [13.16, -59.55], name: 'Barbados'},
                {latLng: [17.11, -61.85], name: 'Antigua and Barbuda'},
                {latLng: [-4.61, 55.45], name: 'Seychelles'},
                {latLng: [7.35, 134.46], name: 'Palau'},
                {latLng: [42.5, 1.51], name: 'Andorra'},
                {latLng: [14.01, -60.98], name: 'Saint Lucia'},
                {latLng: [6.91, 158.18], name: 'Federated States of Micronesia'},
                {latLng: [1.3, 103.8], name: 'Singapore'},
                {latLng: [1.46, 173.03], name: 'Kiribati'},
                {latLng: [-21.13, -175.2], name: 'Tonga'},
                {latLng: [15.3, -61.38], name: 'Dominica'},
                {latLng: [-20.2, 57.5], name: 'Mauritius'},
                {latLng: [26.02, 50.55], name: 'Bahrain'},
                {latLng: [0.33, 6.73], name: 'São Tomé and Príncipe'}
            ]
        });
    }
    return {
        init: function () {
            handleBasic();
            handleReverseProjection();
            handleMarkers();
        }
    }

}(jQuery);