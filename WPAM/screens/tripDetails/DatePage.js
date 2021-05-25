import * as React from 'react';
import { useState, useEffect } from 'react';
import { Text, View, Button, FlatList } from 'react-native';
import { CalendarList } from 'react-native-calendars';
import CommonDataManager, { urls } from '../../Properties';
import TodoItem from '../../components/voter/todoItem';

export const TitleText = (props) => {
    return (
        <Text style={{ fontSize: 48, color: 'black' }}>
            {props.label}
        </Text>
    );
}

export const DatePage = (props) => {

    const [votesMode, setVotesMode] = useState(false);

    const selected = '#70d7c7';
    const available = '#d77070';
    const accepted = '#70d7c7';

    const [pickedDates, setPickedDates] = useState({
        '2021-05-03': { dots: [{ key: 'vacation', color: 'red', selectedDotColor: 'blue' }], startingDay: true, endingDay: true, color: available }
    })

    const formatDate = (date) => {
        return date[0] + date[1] + date[2] + date[3] + '-' + date[4] + date[5] + '-' + date[6] + date[7];
    }

    const formatToBackendDate = (date) => {
        return date[0] + date[1] + date[2] + date[3] + date[5] + date[6] + date[8] + date[9];
    }

    const prepareData = () => {
        let availableDays = {}
        for (const day of props.tripDetails.days) {
            availableDays[formatDate(day)] = { startingDay: true, endingDay: true, color: props.tripDetails.general.accepted ? accepted : available }
        }
        for (const day of props.tripDetails.availability) {
            availableDays[formatDate(day)]['color'] = selected;
        }
        setPickedDates(availableDays);
    }

    useEffect(() => {
        prepareData();
    }, []);

    useEffect(() => {
        prepareData();
    }, [props]);

    const onPickDay = (day) => {

        if (props.tripDetails.general.accepted)
            return;

        console.log('selected day', day)

        if (pickedDates[day.dateString] == null) {
            // console.log('selected day not in pickedDates')
        }
        else {

            let newColor = available;
            if (pickedDates[day.dateString].color === available) {
                newColor = selected;
            }
            onSubmitClick({ ...pickedDates, [day.dateString]: { startingDay: true, endingDay: true, color: newColor } });

            setPickedDates({ ...pickedDates, [day.dateString]: { startingDay: true, endingDay: true, color: newColor } });
        }
    }

    const onSubmitClick = (toSubmit) => {

        props.setLoading(true);

        let selectedDates = [];
        for (const [key, value] of Object.entries(toSubmit)) {
            if (value['color'] == selected) {
                selectedDates.push(formatToBackendDate(key));
            }
        }

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: props.tripDetails.general.tripId,
            selectedDates: selectedDates,
        };

        fetch(urls.dateSubmitDates, {
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
                    alert(`ERROR SUBMITING DATES ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR SUBMITTING DATES ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    // alert(`Submitted dates`);
                    console.log("SUCCESS SUBMITTING DATES");
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => props.refreshData());
    }

    const onViewVotesClick = () => {
        setVotesMode(!votesMode);
    }

    return (
        <View style={{ flex: 1 }}>
            < View style={{ flex: 1 }}>

                {!votesMode && (

                    <CalendarList
                        pastScrollRange={1}
                        futureScrollRange={48}
                        scrollEnabled={true}

                        markingType={'period'}
                        markedDates={
                            pickedDates
                        }

                        onDayPress={(day) => { onPickDay(day) }}

                    />

                )}

                {votesMode && (
                    <>
                        <FlatList
                            data={props.tripDetails.dateVotes}
                            keyExtractor={(item) => item.day}
                            renderItem={({ item }) => (
                                <TodoItem item={item} />
                            )}
                        />
                    </>
                )}

            </View>

            <Button onPress={onViewVotesClick} title={votesMode ? "Change your votes" : "View votes"} />
        </View >
    );
}