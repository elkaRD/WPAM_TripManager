import * as React from 'react';
import { useRef, useState } from 'react';
import Swiper from 'react-native-swiper'
import { Text, View } from 'react-native';
import { StyleSheet } from 'react-native';
import Dialog from "react-native-dialog";
import { NameForm } from './NameForm';
import { DateForm } from './DateForm';
import { PlaceForm } from './PlaceForm';
import { ConfirmForm } from './ConfirmForm';
import CommonDataManager, { urls, deviceId } from '../../Properties';
import Spinner from 'react-native-loading-spinner-overlay';
import { Clipboard } from 'react-native'
import Toast from 'react-native-simple-toast';


export const TitleText = (props) => {
    return (
        <Text style={{ fontSize: 48, color: 'black' }}>
            {props.label}
        </Text>
    );
}


export const ModalCreateTripScreen = ({ navigation }) => {

    const [loading, setLoading] = useState(false);
    const [dialogVisible, setDialogVisible] = useState(false);
    const [tripCode, setTripCode] = useState('');


    const swiper = useRef(null)

    const viewStyle = () => {
        return {
            flex: 1,
            backgroundColor: '#FFFFFF',
        }
    }

    const [tripHostNickname, setTripHostNickname] = useState('');
    const [tripName, setTripName] = useState();
    const [tripDays, setTripDays] = useState([]);
    const [tripPlaces, setTripPlaces] = useState([]);
    const [tripAllowAddingPlaces, setTripAllowAddingPlaces] = useState(false);
    const [tripAllowAddingQuestions, setTripAllowAddingQuestions] = useState(false);
    const [tripAllowInviting, setTripAllowInviting] = useState(false);

    const saveTrip = () => {

        setLoading(true);

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripName: tripName,
            nickname: tripHostNickname,
            days: tripDays,
            places: tripPlaces,
            allowAddingPlaces: tripAllowAddingPlaces,
            allowAddingQuestions: tripAllowAddingQuestions,
            allowInviting: tripAllowInviting,
            prevTripId: CommonDataManager.getInstance().getTripId(),
        };

        fetch(urls.tripCreateTrip, {
            method: 'POST',
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
                    alert(`ERROR CREATING TRIP ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR CREATING TRIP${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    setTripCode(json.tripCode);
                    setDialogVisible(true);
                    console.log("SUCCESS CREATING TRIP");

                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => setLoading(false));
    }

    const onOkClick = () => {
        navigation.navigate('Home')
        setDialogVisible(false);
    }

    const onCopyToClipboardClick = () => {
        Clipboard.setString(tripCode);
        Toast.show('Copied to clipboard!');
    }

    return (
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>

            <Spinner
                visible={loading}
                textContent={'Loading...'}
                textStyle={styles.spinnerTextStyle}
            />

            <Swiper
                ref={swiper}
                horizontal={true}
                loop={false}
                showsPagination={true}
                index={0}
                scrollEnabled={false}
            >
                <View style={viewStyle()}>
                    <NameForm
                        swiper={swiper}
                        setTripName={setTripName}
                        setHost={setTripHostNickname}
                        setLoading={setLoading}
                        tripName={tripName}
                        host={tripHostNickname}
                    ></NameForm>
                </View>
                <View style={viewStyle()}>
                    <DateForm
                        swiper={swiper}
                        setTripDays={setTripDays}
                        setLoading={setLoading}
                    ></DateForm>
                </View>
                <View style={viewStyle()}>
                    <PlaceForm
                        swiper={swiper}
                        setTripPlaces={setTripPlaces}
                        setLoading={setLoading}
                    ></PlaceForm>
                </View>
                <View style={viewStyle()}>
                    <ConfirmForm
                        swiper={swiper}
                        tripName={tripName}
                        tripHostNickname={tripHostNickname}
                        tripDays={tripDays}
                        tripPlaces={tripPlaces}
                        setTripAllowAddingPlaces={setTripAllowAddingPlaces}
                        setTripAllowAddingQuestions={setTripAllowAddingQuestions}
                        setTripAllowInviting={setTripAllowInviting}
                        onCreateClick={saveTrip}
                        setLoading={setLoading}
                    >

                    </ConfirmForm>
                </View>
            </Swiper>

            {
                dialogVisible && (
                    <Dialog.Container visible={dialogVisible} statusBarTranslucent>
                        <Dialog.Title>Created trip!</Dialog.Title>
                        <Dialog.Description>
                            Code: {tripCode}
                        </Dialog.Description>

                        <Dialog.Button label="Copy to clipboard" onPress={onCopyToClipboardClick} />
                        <Dialog.Button label="Ok" onPress={onOkClick} />



                    </Dialog.Container>
                )
            }

        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        margin: 10,
        marginTop: 30,
        padding: 30,
    },
    buttonGPlusStyle: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#dc4e41',
        borderWidth: 0.5,
        borderColor: '#fff',
        height: 40,
        borderRadius: 5,
        margin: 5,
    },
    buttonFacebookStyle: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#485a96',
        borderWidth: 0.5,
        borderColor: '#fff',
        height: 40,
        borderRadius: 5,
        margin: 5,
    },
    buttonImageIconStyle: {
        padding: 10,
        margin: 5,
        height: 25,
        width: 25,
        resizeMode: 'stretch',
    },
    buttonTextStyle: {
        color: '#fff',
        marginBottom: 4,
        marginLeft: 10,
    },
    buttonIconSeparatorStyle: {
        backgroundColor: '#fff',
        width: 1,
        height: 40,
    },
});