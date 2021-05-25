import * as React from 'react';
import { useState } from 'react';
import { Text, View, Button } from 'react-native';
import CommonDataManager, { urls } from '../Properties';
import Input from 'react-native-input-style';
import { Clipboard } from 'react-native'

export const ModalJoinTripScreen = ({ navigation }) => {

    const [tripCode, setTripCode] = useState('');
    const [nickname, setNickname] = useState('');

    const onTripCodeChange = (id, value, isValid) => {
        setTripCode(value);
    }

    const onNicknameChange = (id, value, isValid) => {
        setNickname(value);
    }

    const onJoinClick = () => {

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            code: tripCode,
            nickname: nickname,
        };

        fetch(urls.tripJoinTrip, {
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
                    alert(`ERROR JOINING TRIP ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR JOINING TRIP${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    alert(`Joined trip ${json.tripName} by ${json.host}`);
                    console.log("SUCCESS JOINING TRIP");
                    navigation.goBack();
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => setLoading(false));
    }

    const getCodeFromClipboard = async () => {
        const text = await Clipboard.getString();
        onTripCodeChange(0, text, true);
    };

    return (
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
            {/* <Text style={{ fontSize: 30 }}>This is a modal!</Text> */}

            <Input
                onlyEnglish
                id="tripCode"
                label="Trip code"
                keyboardType="default"
                required
                autoCapitalize="sentences"
                errorText="Should be at least 1 character"
                onInputChange={onTripCodeChange}
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

            {/* <Button onPress={getCodeFromClipboard} title="Paste from clipboard" /> */}
            <Button onPress={onJoinClick} title="Join trip" disabled={tripCode.length == 0 || nickname.length == 0} />
        </View>
    );
}