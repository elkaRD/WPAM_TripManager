import * as React from 'react';
import { Text, Button } from 'react-native';
import Input from 'react-native-input-style';


export const NameForm = (props) => {

    const onNicknameChange = (id, value, isValid) => {
        props.setHost(value);
    }

    const onTripNameChange = (id, value, isValid) => {
        props.setTripName(value);
    }

    return (
        <>
            <Text style={{ fontSize: 32, color: 'black' }}>
                Set basic info
                </Text>

            <Input
                onlyEnglish
                id="tripname"
                label="Trip Name"
                keyboardType="default"
                required
                errorText="Should be at least 1 character"
                autoCapitalize="sentences"

                onInputChange={onTripNameChange}
                initialValue=""
                outlined
                borderColor="blue"

            />

            <Input
                onlyEnglish
                id="nickname"
                label="Your Nickname"
                keyboardType="default"
                required
                autoCapitalize="sentences"
                errorText="Should be at least 1 character"
                onInputChange={onNicknameChange}
                initialValue=""
                outlined
                borderColor="blue"
            />



            <Button onPress={() => {
                props.swiper.current.scrollBy(1, true);
            }
            } title="NEXT" disabled={props.host.length == 0 || props.tripName.length == 0} />
        </>
    );
}