import * as React from 'react';
import { useState } from 'react';
import { View, Button, FlatList, Alert, Text } from 'react-native';
import { StyleSheet } from 'react-native';
import Dialog from "react-native-dialog";
import Header from '../../components/voter/header';
import TodoItem from '../../components/voter/todoItem';
import { useNavigation } from '@react-navigation/native';
import CommonDataManager from '../../Properties';
import { ModalMapPicker } from './ModalMapPicker';

export const PlaceForm = (props) => {

    const navigation = useNavigation();

    const [pickerMode, setPickerMode] = useState(false);
    const [dialogVisible, setDialogVisible] = useState(false);

    const onAddPlaceClick = () => {
        setDialogVisible(true);
    }

    const [newPlaceName, setNewPlaceName] = useState("");

    const onPickOnMapClick = () => {
        setDialogVisible(false);

        CommonDataManager.getInstance().setPlaceLat(null);
        CommonDataManager.getInstance().setPlaceLon(null);

        setPickerMode(true);
    };


    const [places, setPlaces] = useState([
    ]);


    const pressHandlerTodo = (key) => {
        var newPlaces = prevPlaces => {
            return prevPlaces.filter(todo => todo.key != key);
        };
        setPlaces(newPlaces);

        props.setTripPlaces(newPlaces);
    };

    const submitPlaceName = () => {
        if (newPlaceName.length > 1) {
            var newPlaces = prevPlaces => {
                return [
                    { addressName: newPlaceName, key: Math.random().toString(), containsCoords: false },
                    ...prevPlaces
                ];
            };
            setPlaces(newPlaces);
            setDialogVisible(false);

            props.setTripPlaces(newPlaces);
        } else {
            Alert.alert('OOPS', 'Todo must be over 1 character long', [
                { text: 'Understood', onPress: () => console.log('alert closed') }
            ]);
        }
    };

    const pressCreateTrip = () => {

    }

    const onAddPlaceMapCancelClick = () => {
        setPickerMode(false);
    }

    const onAddPlaceMapConfirmClick = (lon, lat) => {

        var newPlaces = prevPlaces => {
            return [
                { addressName: newPlaceName, key: Math.random().toString(), containsCoords: true, lon: lon, lat: lat },
                ...prevPlaces
            ];
        };

        setPlaces(newPlaces);
        setDialogVisible(false);

        props.setTripPlaces(newPlaces);


        setPickerMode(false);
    }

    const displayMapHandler = (placeId) => {

        for (const place of places) {
            if (place.placeId === placeId) {
                CommonDataManager.getInstance().setPlaceName(place.addressName);
                CommonDataManager.getInstance().setPlaceLon(place.lon);
                CommonDataManager.getInstance().setPlaceLat(place.lat);
                break;
            }
        }

        CommonDataManager.getInstance().setAllPlaces(places);

        navigation.navigate('ModalDisplayOnMap');
    }

    const onCancelClick = () => {
        setDialogVisible(false);
    }

    return (

        <>
            {!pickerMode && (
                <>

                    <View style={styles.container}>
                        <Header />
                        <View style={styles.content}>

                            <View style={styles.list}>

                                {places.length == 0 && (
                                    <View >
                                        <Text style={{
                                            textAlign: 'center', // <-- the magic
                                            fontWeight: 'bold',
                                            fontSize: 18,
                                            marginTop: 0,

                                        }}>No places</Text>
                                    </View>
                                )}

                                <FlatList
                                    data={places}
                                    renderItem={({ item }) => (
                                        <TodoItem item={item} pressHandler={pressHandlerTodo} mapPressHandler={displayMapHandler} />
                                    )}
                                />
                            </View>
                        </View>

                    </View>

                    <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                        <Button onPress={() => {
                            props.swiper.current.scrollBy(-1, true);
                        }
                        } title="PREVIOUS" />
                        <Button style={{

                        }} onPress={onAddPlaceClick} title="Add place" />
                        <Button onPress={() => {
                            props.swiper.current.scrollBy(1, true);
                        }
                        } title="NEXT" />
                    </View>

                    <View style={{ flexDirection: 'row', flexWrap: 'wrap', alignItems: 'flex-start', height: 60 }}></View>

                    {
                        dialogVisible && (
                            <Dialog.Container visible={dialogVisible} statusBarTranslucent>
                                <Dialog.Title>Add destination to poll</Dialog.Title>
                                <Dialog.Description>
                                    Enter destination name and optionally pick location on the map
                                </Dialog.Description>

                                <Dialog.Input onChangeText={placeName => setNewPlaceName(placeName)} style={{ backgroundColor: 'gainsboro' }}></Dialog.Input>
                                <Dialog.Button label="Cancel" onPress={onCancelClick} />
                                <Dialog.Button label="Pick on map" onPress={onPickOnMapClick} />
                                <Dialog.Button label="Add" onPress={submitPlaceName} />

                            </Dialog.Container>
                        )
                    }

                </>
            )}
            {pickerMode && (
                <ModalMapPicker placeName={newPlaceName} onCancelClick={onAddPlaceMapCancelClick} onSaveClick={onAddPlaceMapConfirmClick} />
            )}
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 20,
        paddingHorizontal: 10,
        paddingBottom: 50,
        // backgroundColor: 'yellow',
    },
    item: {
        flex: 1,
        marginHorizontal: 4,
        marginTop: 18,
        padding: 18,
        backgroundColor: 'pink',
        fontSize: 20,
    },
});