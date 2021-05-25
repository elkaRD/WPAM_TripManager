import * as React from 'react';
import { useState } from 'react';
import MapView from 'react-native-maps';
import { StyleSheet, View, Dimensions } from 'react-native';
import CommonDataManager from '../../Properties';

export const ModalDisplayOnMap = (props) => {

    const [region, setRegion] = useState({
        latitude: parseFloat(CommonDataManager.getInstance().getPlaceLat()),
        longitude: parseFloat(CommonDataManager.getInstance().getPlaceLon()),
        latitudeDelta: 0.1,
        longitudeDelta: 0.1
    });

    const [selectedPlace, setSelectedPlace] = useState({
        "latlng": {
            latitude: parseFloat(CommonDataManager.getInstance().getPlaceLat()),
            longitude: parseFloat(CommonDataManager.getInstance().getPlaceLon())
        },
        "title": CommonDataManager.getInstance().getPlaceName()
    });

    return (
        <>
            <View style={styles.container}>

                <MapView style={styles.map} region={region}
                    onPress={(e) => {
                        console.log("MARKERS", selectedPlace);
                    }}>

                    {selectedPlace != null && (<MapView.Marker coordinate={selectedPlace.latlng} title={selectedPlace.title} />)}
                    {
                        CommonDataManager.getInstance().getAllPlaces().filter(p => p.containsCoords).map((place, i) => (
                            <MapView.Marker key={i} coordinate={{ latitude: parseFloat(place.lat), longitude: parseFloat(place.lon) }} title={place.addressName} />
                        ))
                    }

                </MapView>
            </View>
        </>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    map: {
        width: Dimensions.get('window').width,
        height: Dimensions.get('window').height,
    },
});