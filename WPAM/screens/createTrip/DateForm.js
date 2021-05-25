import * as React from 'react';
import { useState } from 'react';
import { Text, View, Button, TouchableOpacity } from 'react-native';
import { CalendarList } from 'react-native-calendars';

export const DateForm = (props) => {

    const viewStyle = () => {
        return {
            flex: 1,
            backgroundColor: '#FFFFFF',
        }
    }

    const [pickedDates, setPickedDates] = useState({});
    const [dateStrings, setDateStrings] = useState(new Set());

    const onPickDay = (day) => {

        const shortDate = day.dateString[0] + day.dateString[1] + day.dateString[2] + day.dateString[3] + day.dateString[5] + day.dateString[6] + day.dateString[8] + day.dateString[9];

        if (pickedDates[day.dateString] == null) {
            setPickedDates({ ...pickedDates, [day.dateString]: { startingDay: true, endingDay: true, color: '#70d7c7' } })

            dateStrings.add(shortDate);
        }
        else {
            let pd = { ...pickedDates };
            delete pd[day.dateString];
            setPickedDates(pd);

            dateStrings.delete(shortDate);
        }

        props.setTripDays(Array.from(dateStrings));

    }

    return (
        <>
            <View style={viewStyle(), { height: 70, alignItems: 'center', justifyContent: 'center' }}>

                <Text style={{ fontSize: 32, color: 'black' }}>
                    Pick available days
            </Text>

            </View>

            <View style={viewStyle()}>

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

            </View>

            <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                <Button onPress={() => {
                    props.swiper.current.scrollBy(-1, true);
                }
                } title="PREVIOUS" />
                <Button onPress={() => {
                    props.swiper.current.scrollBy(1, true);
                }
                } title="NEXT" />
            </View>

            <View style={{
                height: 50
            }} />
        </>
    )
}