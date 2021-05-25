import * as React from 'react';
import { useState, useEffect } from 'react';
import { View, FlatList, Text } from 'react-native';
import { StyleSheet } from 'react-native';
import TripItem from './TripItem';
import CommonDataManager, { urls } from '../../Properties';
import Spinner from 'react-native-loading-spinner-overlay';


export const TripList = (props) => {

    const [loading, setLoading] = useState(false);

    const isMyTrip = (element, index, array) => {
        return (element.owner);
    }

    const [trips, setTrips] = useState([]);

    const removeItemFromTripList = (tripId) => {
        setTrips(prevTrips => {
            return prevTrips.filter(todo => todo.tripId != tripId);
        });
    }

    const pressHandlerRemove = (tripId) => {
        removeItemFromTripList(tripId);

        setLoading(true);

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: tripId,
            explanation: 'NOT IMPLEMENTED',
        };

        fetch(urls.tripDeleteTrip, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...data
            })
        })
            .then((response) => response.json())
            .then((json) => {
                if (json.errorCode != 0) {
                    alert(`ERROR DELETING TRIP ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR DELETING TRIP ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    alert(`Deleted trip`);
                    console.log("SUCCESS DELETEING TRIP");
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => {
                setLoading(false);
                downloadTrips();
            });
    };

    const pressHandlerLeave = (tripId) => {
        removeItemFromTripList(tripId);

        setLoading(true);

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: tripId,
        };

        fetch(urls.tripLeaveTrip, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...data
            })
        })
            .then((response) => response.json())
            .then((json) => {
                if (json.errorCode != 0) {
                    alert(`ERROR LEAVING TRIP ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR LEAVING TRIP ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    alert(`Leaved trip`);
                    console.log("SUCCESS LEAVING TRIP");
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => {
                setLoading(false);
                downloadTrips();
            });

    };

    const pressHandlerEnter = (tripId) => {
        props.onSelectTrip(tripId);
    };

    const downloadTrips = () => {

        setLoading(true);

        fetch(`${urls.tripGetTripList}?deviceId=${CommonDataManager.getInstance().getDeviceId()}`)
            .then((response) => response.json())
            .then((json) => {
                console.log(`Got trips: `, json.trips);

                if (json.errorCode != 0) {
                    alert(`ERROR GETTING TRIPS ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR GETTING TRIPS ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    console.log("SUCCESS GETTING TRIPS");
                    if (props.onlyMyTrips) {
                        json.trips = json.trips.filter(isMyTrip);
                    }
                    setTrips(json.trips);
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => setLoading(false));
    }

    useEffect(() => {
        downloadTrips();
    }, []);

    return (

        <>
            <View style={styles.container}>

                <Spinner
                    visible={loading}
                    textContent={'Loading...'}
                    textStyle={styles.spinnerTextStyle}
                />

                {trips.length == 0 && (
                    <View >
                        <Text style={{
                            textAlign: 'center', // <-- the magic
                            fontWeight: 'bold',
                            fontSize: 18,
                            marginTop: 0,

                        }}>No trips</Text>
                    </View>
                )}

                <View style={styles.content}>
                    <View style={styles.list}>
                        <FlatList
                            keyExtractor={(item) => item.tripId}
                            data={trips}
                            renderItem={({ item }) => (
                                <TripItem item={item} onPressRemove={pressHandlerRemove} onPressLeave={pressHandlerLeave} onPressEnter={pressHandlerEnter} />
                            )}
                        />
                    </View>
                </View>

            </View>
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

