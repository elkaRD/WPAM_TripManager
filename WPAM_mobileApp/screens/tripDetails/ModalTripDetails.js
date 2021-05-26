import * as React from 'react';
import { useRef, useState, useEffect } from 'react';
import { Text, View, Button, Dimensions } from 'react-native';
import SegmentedControl from "rn-segmented-control";
import CommonDataManager from '../../Properties';
import { urls } from '../../Properties';
import Spinner from 'react-native-loading-spinner-overlay';
import { StyleSheet } from 'react-native';
import { DatePage } from './DatePage';
import { PlacePage } from './PlacePage';
import { QuestionsPage } from './QuestionsPage';
import { UserPage } from './UserPage';
import Dialog from "react-native-dialog";
import FontAwesome from "react-native-vector-icons/FontAwesome";
import * as Calendar from "expo-calendar"
import * as Permissions from "expo-permissions"
import * as AddCalendarEvent from 'react-native-add-calendar-event';
import { Clipboard } from 'react-native'
import Toast from 'react-native-simple-toast';

export const ModalTripDetails = ({ navigation }) => {

    const [loading, setLoading] = useState(true);
    const [initialLoading, setInitialLoading] = useState(true);
    const [hideUi, setHideUi] = useState(false);
    const [acceptDialogVisible, setAcceptDialogVisible] = useState(false);

    const [tripDetails, setTripDetails] = useState({});

    const [tabIndex, setTabIndex] = React.useState(1);

    const [granted, setGranted] = useState(false)

    useEffect(() => {
        openCalendarRequest() // Ask for Premission to access phone calendar
    }, [])

    useEffect(() => {
        downloadTripDetails();
    }, []);

    const downloadTripDetails = () => {
        let commonData = CommonDataManager.getInstance();
        console.log("DISPLAYING DETAILS FOR TRIP WITH ID ", commonData.getTripId());

        fetch(`${urls.tripGetTripDetails}?deviceId=${CommonDataManager.getInstance().getDeviceId()}&tripId=${commonData.getTripId()}`)
            .then((response) => response.json())
            .then((json) => {
                if (json.errorCode != 0) {
                    alert(`ERROR GETTING TRIP DETAILS ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR GETTING TRIP DETAILS ${json.errorCode} (${json.errorDesc})`);
                    navigation.goBack();
                }
                else {
                    console.log("SUCCESS GETTING TRIP DETAILS");
                    setTripDetails(json);
                }
            })
            .catch((error) => { alert("ERROR GETTING TRIP DETAILS", error); console.log("ERROR GETTING TRIP DETAILS ", error) })
            .finally(() => {
                setLoading(false);
                setInitialLoading(false);
            });
    }

    const handleTabsChange = (index) => {
        setTabIndex(index);
    };

    const onAcceptClick = () => {
        setAcceptDialogVisible(true);
    }

    const onAcceptConfirmClick = () => {
        setAcceptDialogVisible(false);

        setLoading(true);

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: tripDetails.general.tripId,
            days: tripDetails.availability,
        };

        fetch(urls.tripAcceptTrip, {
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
                    alert(`ERROR ACCEPTING TRIP ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR ACCEPTING TRIP ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    // alert(`Selected answer`);
                    console.log("SUCCESS ACCEPTING TRIP");
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => {
                downloadTripDetails();
            });

    }

    const onAcceptCancelClick = () => {
        setAcceptDialogVisible(false);
    }

    const onAddToCalendarClick = () => {

        const begDay = tripDetails.days[0];
        const endDay = tripDetails.days[tripDetails.days.length - 1];
        const startDate = begDay.substring(0, 4) + "-" + begDay.substring(4, 6) + "-" + begDay.substring(6, 8) + "T00:00:00.000Z";
        const endDate = endDay.substring(0, 4) + "-" + endDay.substring(4, 6) + "-" + endDay.substring(6, 8) + "T00:00:00.000Z";

        const eventConfig = {
            title: tripDetails.general.tripName,
            startDate: startDate,
            endDate: endDate,
            allDay: true,
            notes: 'by ' + tripDetails.general.hostNickname,
            location: tripDetails.general.placeSummary,
            navigationBarIOS: {
                tintColor: 'orange',
                backgroundColor: 'green',
                titleColor: 'blue',
            },
        };

        AddCalendarEvent.presentEventCreatingDialog(eventConfig)
            .then((eventInfo) => {
                // alert('eventInfo -> ' + JSON.stringify(eventInfo));
                console.log("ADD CALENDAR EVENT", JSON.stringify(eventInfo));
            })
            .catch((error) => {
                alert('Error -> ' + error);
                console.log("ERROR ADDING CALENDAR EVENT", error);
            });
    }

    const openCalendarRequest = async () => {
        const { status } = await Permissions.askAsync(Permissions.CALENDAR)
        if (status === "granted") {
            setGranted(true)
        }
    }

    const onGetCodeClick = () => {
        Clipboard.setString(tripDetails.tripCode);
        Toast.show('Copied to clipboard!');
    }

    return (
        <View style={{ flex: 1 }}>

            <Spinner
                visible={loading}
                textContent={'Loading...'}
                textStyle={styles.spinnerTextStyle}
            />

            {!initialLoading && (
                <>
                    {!hideUi && (
                        <>
                            <View style={{ flexDirection: 'row', paddingBottom: 10 }}>
                                {tripDetails.general.accepted && (
                                    <FontAwesome name={"check"} size={40} color={"green"} />
                                )}
                                {!tripDetails.general.accepted && (
                                    <FontAwesome name={"question"} size={40} color={"blue"} />
                                )}

                                <View>
                                    <Text style={{ fontSize: 34, color: 'black' }}>
                                        {tripDetails?.general?.tripName}
                                    </Text>
                                    {/* <Text style={{ fontSize: 28, color: 'black' }}>
                                        {"by " + tripDetails?.general?.hostNickname}
                                    </Text> */}
                                </View>
                            </View>
                            <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                                {(tripDetails.general.owner) && (
                                    <View style={{ width: '40%', height: 35 }} >
                                        <Button onPress={onAcceptClick} title="ACCEPT" disabled={tripDetails.general.accepted} />
                                    </View>
                                )}

                                {tripDetails.general.accepted && tripDetails.days.length > 0 && (
                                    <View style={{ width: '40%', height: 35 }} >
                                        <Button onPress={onAddToCalendarClick} title="ADD TO CALENDAR" />
                                    </View>
                                )}
                            </View>

                            <SegmentedControl
                                onChange={handleTabsChange}
                                currentIndex={tabIndex}
                                tabs={["Date", "Place", "Questions", "Participants"]}
                                segmentedControlBackgroundColor="#86c4fD"
                                activeSegmentBackgroundColor="#0482f7"
                                activeTextColor="white"
                                textColor="black"
                                paddingVertical={10}
                                width={Dimensions.get("screen").width}
                                containerStyle={{
                                    marginVertical: 20,
                                }}
                                textStyle={{
                                    fontWeight: "300",
                                    fontSize: 14,

                                }}
                            />

                        </>
                    )}

                    {!initialLoading && (
                        <>
                            {tabIndex == 0 && (<DatePage tripDetails={tripDetails} setLoading={setLoading} refreshData={downloadTripDetails} />)}
                            {tabIndex == 1 && (<PlacePage tripDetails={tripDetails} setLoading={setLoading} setHideUi={setHideUi} refreshData={downloadTripDetails} />)}
                            {tabIndex == 2 && (<QuestionsPage tripDetails={tripDetails} setLoading={setLoading} refreshData={downloadTripDetails} />)}
                            {tabIndex == 3 && (<UserPage tripDetails={tripDetails} setLoading={setLoading} refreshData={downloadTripDetails} onGetCodeClick={onGetCodeClick} />)}
                        </>
                    )}

                </>

            )}

            { acceptDialogVisible && (
                <Dialog.Container visible={acceptDialogVisible} statusBarTranslucent>
                    <Dialog.Title>Accept trip?</Dialog.Title>
                    <Dialog.Description>
                        Accept?
                                </Dialog.Description>

                    <Dialog.Button label="Cancel" onPress={onAcceptCancelClick} />
                    <Dialog.Button label="Accept" onPress={onAcceptConfirmClick} />

                </Dialog.Container>
            )}

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