import * as React from 'react';
import { Text, View, Button } from 'react-native';
import BouncyCheckbox from "react-native-bouncy-checkbox";

export const ConfirmForm = (props) => {

    return (
        <>
            <View style={{ height: 70, alignItems: 'center', justifyContent: 'center' }}>
                <Text style={{ fontSize: 32, color: 'black' }}>
                    Check trip details
                </Text>
            </View>

            <Text style={{ fontSize: 28, color: 'black' }}>
                Trip name: {props.tripName}
            </Text>
            <Text style={{ fontSize: 28, color: 'black' }}>
                Nickname: {props.tripHostNickname}
            </Text>

            <View style={{ height: 10 }} />
            <Text style={{ fontSize: 24, color: 'black' }}>
                Selected days: {props.tripDays.length}
            </Text>
            <Text style={{ fontSize: 24, color: 'black' }}>
                Selected places: {props.tripPlaces.length}
            </Text>

            <View style={{ height: 10 }} />
            <BouncyCheckbox
                size={25}
                fillColor="red"
                unfillColor="#FFFFFF"
                text="Allow Adding Places"
                iconStyle={{ borderColor: "red" }}
                onPress={(isChecked) => { props.setTripAllowAddingPlaces(isChecked) }}
            />
            <View style={{ height: 10 }} />
            <BouncyCheckbox
                size={25}
                fillColor="red"
                unfillColor="#FFFFFF"
                text="Allow Adding Questions"
                iconStyle={{ borderColor: "red" }}
                onPress={(isChecked) => { props.setTripAllowAddingQuestions(isChecked) }}
            />
            <View style={{ height: 10 }} />
            <BouncyCheckbox
                size={25}
                fillColor="red"
                unfillColor="#FFFFFF"
                text="Allow Inviting By Others"
                iconStyle={{ borderColor: "red" }}
                onPress={(isChecked) => { props.setTripAllowInviting(isChecked) }}
            />

            <View style={{ height: 10 }} />

            <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                <Button onPress={() => {
                    props.swiper.current.scrollBy(-1, true);
                }
                } title="PREVIOUS" />
                <Button onPress={() => {
                    props.onCreateClick();
                }
                } title="Create trip" />
            </View>

        </>
    )
}