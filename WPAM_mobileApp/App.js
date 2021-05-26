import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { HomeScreen } from './screens/HomeScreen'
import { ModalCreateTripScreen } from './screens/createTrip/ModalCreateTripScreen'
import { ModalJoinTripScreen } from './screens/ModalJoinTripScreen'
import { ModalTripDetails } from './screens/tripDetails/ModalTripDetails'
import { ModalDisplayOnMap } from './screens/tripDetails/ModalDisplayOnMap'
import { ModalMapPicker } from './screens/createTrip/ModalMapPicker'

const MainStack = createStackNavigator();

export default function App() {
  return (

    <NavigationContainer>
      <MainStack.Navigator initialRouteName="Home">
        <MainStack.Screen name="Home" component={HomeScreen} options={{ title: "Trips Manager" }} />
        <MainStack.Screen name="ModalCreateTrip" component={ModalCreateTripScreen} options={{ title: "Create Trip!" }} />
        <MainStack.Screen name="ModalJoinTrip" component={ModalJoinTripScreen} options={{ title: "Join Trip!" }} />
        <MainStack.Screen name="ModalTripDetails" component={ModalTripDetails} options={{ title: "Trip Details" }} />
        <MainStack.Screen name="ModalDisplayOnMap" component={ModalDisplayOnMap} options={{ title: "All Places" }} />
        <MainStack.Screen name="ModalMapPicker" component={ModalMapPicker} options={{ title: "Select Place on Map" }} />
      </MainStack.Navigator>
    </NavigationContainer>

  );
}

