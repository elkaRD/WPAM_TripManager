import * as React from 'react';
import { useRef, useState, useEffect } from 'react';
import MapView from 'react-native-maps';
import { StyleSheet, Text, View, Dimensions, Button, TextInput } from 'react-native';
// import Geocoder from 'react-native-geocoding';
// import Geocoder from 'react-native-geocoder';
import CommonDataManager from '../../Properties';


export const ModalMapPicker = (props) => {

    const [pressedLon, setPressedLon] = useState("");
    const [pressedLat, setPressedLat] = useState("");

    const [region, setRegion] = useState({
        latitude: 52.1009056750823,
        longitude: 21.1012272143364,
        latitudeDelta: 0.6,
        longitudeDelta: 0.6
    });

    const [markers, setMarkers] = useState([]);

    const [selectedPlace, setSelectedPlace] = useState(null);

    const goBack = () => {

        CommonDataManager.getInstance().setPlaceLat(pressedLat);
        CommonDataManager.getInstance().setPlaceLon(pressedLon);
    }

    return (
        <>

            <View style={styles.container}>

                <MapView style={styles.map} region={region}
                    onPress={(e) => {

                        setRegion({ ...region, latitude: e.nativeEvent.coordinate.latitude, longitude: e.nativeEvent.coordinate.longitude });
                        setMarkers({ markers: [markers, { latlng: e.nativeEvent.coordinate }] });
                        setSelectedPlace({ latlng: e.nativeEvent.coordinate });

                        console.log("MAP ON PRESS", e.nativeEvent);

                        setPressedLat(e.nativeEvent.coordinate.latitude.toString());
                        setPressedLon(e.nativeEvent.coordinate.longitude.toString());
                    }}>

                    {selectedPlace != null && (<MapView.Marker coordinate={selectedPlace.latlng} />)}

                </MapView>
            </View>

            <Text style={{ fontSize: 32, color: 'black' }}>
                {props.placeName}
            </Text>

            <Button onPress={() => {
                props.onCancelClick();
            }
            } title="Cancel" />
            <Button onPress={() => {
                goBack();
                props.onSaveClick(pressedLon, pressedLat);
            }
            } title="Confirm" />
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