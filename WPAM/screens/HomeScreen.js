import React, { useState, useEffect } from 'react';
import { StyleSheet, View, FlatList, Alert, TouchableWithoutFeedback, Keyboard, Text } from 'react-native';
import { NavigationContainer, useNavigation } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import ActionButton from 'react-native-action-button';
import Icon from 'react-native-vector-icons/Ionicons';
import { Ionicons } from '@expo/vector-icons';
import { TripList } from './tripList/TripList'
import CommonDataManager from '../Properties';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const HomeScreen = () => {

    const navigation = useNavigation();

    const [initializing, setInitializing] = useState(true);

    useEffect(() => {
        console.disableYellowBox = true;
        getDeviceId();
    }, []);

    const makeId = (length) => {
        var result = [];
        var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var charactersLength = characters.length;
        for (var i = 0; i < length; i++) {
            result.push(characters.charAt(Math.floor(Math.random() *
                charactersLength)));
        }
        return result.join('');
    }

    const storeDeviceId = async (value) => {
        try {
            await AsyncStorage.setItem('deviceId', value)
        } catch (e) {
            // saving error
        }
        setInitializing(false);
    }


    const getDeviceId = async () => {
        try {
            const value = await AsyncStorage.getItem('deviceId')
            if (value !== null) {
                CommonDataManager.getInstance().setDeviceId(value);
                setInitializing(false);
            } else {
                let deviceId = makeId(200);
                storeDeviceId(deviceId);
                CommonDataManager.getInstance().setDeviceId(deviceId);
            }

        } catch (e) {
            // error reading value
        }
    }

    function ActionButtonMenu() {



        return (
            <ActionButton buttonColor="rgba(231,76,60,1)">
                <ActionButton.Item buttonColor='#9b59b6' title="Create new trip" onPress={() => {
                    CommonDataManager.getInstance().setTripId(null);
                    navigation.navigate('ModalCreateTrip');
                }}>
                    <Icon name="md-create" style={styles.actionButtonIcon} />
                </ActionButton.Item>
                <ActionButton.Item buttonColor='#3498db' title="Join trip" onPress={() => { navigation.navigate('ModalJoinTrip') }}>
                    <Icon name="md-search" style={styles.actionButtonIcon} />
                </ActionButton.Item>
            </ActionButton>
        );
    }


    const onSelectTrip = (tripId) => {
        let commonData = CommonDataManager.getInstance();
        commonData.setTripId(tripId);

        navigation.navigate('ModalTripDetails')
    }

    function AddTripScreen() {
        return (
            <View style={styles.container}>
                <Text>Add trip screen</Text>

                <ActionButtonMenu />
            </View>
        );
    }


    const myTripsScreen = () => {
        return (
            <View style={styles.container}>

                <TripList onlyMyTrips={true} onSelectTrip={onSelectTrip} />

                <ActionButtonMenu />
            </View>
        );
    }

    const allTripsScreen = () => {
        return (
            <View style={styles.container}>

                <TripList onlyMyTrips={false} onSelectTrip={onSelectTrip} />

                <ActionButtonMenu />
            </View>
        );
    }

    const HomeStackScreen = () => {
        return (
            <HomeStack.Navigator initialRouteName="Home">
                <HomeStack.Screen name="Home" component={HomeScreen} />
                <HomeStack.Screen name="AddTrip" component={AddTripScreen} />
                <HomeStack.Screen name="MyModal" component={ModalScreen} />
                <HomeStack.Screen name="TripDetails" component={ModalTripDetails} />
            </HomeStack.Navigator>
        );
    }

    const Tab = createBottomTabNavigator();

    const styles = StyleSheet.create({
        container: {
            flex: 1,
            backgroundColor: '#F5FCFF'
        },
        welcome: {
            fontSize: 20,
            textAlign: 'center',
            margin: 10
        },
        actionButtonIcon: {
            fontSize: 20,
            height: 22,
            color: 'white',
        }
    });


    return (

        <>
            {!initializing && (

                <Tab.Navigator
                    screenOptions={({ route }) => ({
                        tabBarIcon: ({ focused, color, size }) => {
                            let iconName;
                            if (route.name === 'My trips') {
                                iconName = focused
                                    ? 'star'
                                    : 'star-outline';
                            } else if (route.name === 'All trips') {
                                iconName = focused
                                    ? 'ios-list-outline'
                                    : 'ios-list';
                            }

                            return <Ionicons name={iconName} size={size} color={color} />;
                        },
                    })}
                    tabBarOptions={{
                        activeTintColor: 'tomato',
                        inactiveTintColor: 'gray',
                    }}
                >
                    <Tab.Screen name="My trips" component={myTripsScreen} />
                    <Tab.Screen name="All trips" component={allTripsScreen} />
                </Tab.Navigator>
            )
            }
        </>
    );
}