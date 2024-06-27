import React, { useEffect, useRef } from 'react';
import ReactDOMServer from 'react-dom/server'; // Import ReactDOMServer
import InfoWindow from './InfoWindow';

const { kakao } = window;

const KakaoMap = ({ coordinate, accommodations }) => {
    const mapRef = useRef(null);
    const infoWindowRef = useRef(null);

    useEffect(() => {
        const { latitude, longitude } = coordinate;
        const container = document.getElementById('myMap');
        const options = {
            center: new kakao.maps.LatLng(latitude, longitude),
            level: 3
        };

        mapRef.current = new kakao.maps.Map(container, options);
        let bounds = new kakao.maps.LatLngBounds();

        accommodations.forEach(accommodation => {
            const markerPosition = new kakao.maps.LatLng(accommodation.latitude, accommodation.longitude);
            const marker = new kakao.maps.Marker({
                position: markerPosition
            });
            marker.setMap(mapRef.current);
            bounds.extend(markerPosition);

            // InfoWindow 생성
            const content = ReactDOMServer.renderToString(<InfoWindow accommodation={accommodation} />);

            const infowindow = new kakao.maps.InfoWindow({
                content: content,
                removable: true
            });

            // 마커 클릭 시 InfoWindow를 표시합니다
            kakao.maps.event.addListener(marker, 'click', () => {
                if (infoWindowRef.current) {
                    infoWindowRef.current.close();
                }
                infowindow.open(mapRef.current, marker);
                infoWindowRef.current = infowindow;
            });
        });

        mapRef.current.setBounds(bounds);
    }, [coordinate, accommodations]);

    return (
        <div id='myMap' style={{
            width: '100%',
            height: '100%',
            marginTop: '10px',
        }}></div>
    );
}

export default KakaoMap;
