import React from 'react'
import { StyleSheet, TouchableOpacity, Text, View } from 'react-native';
import FontAwesome from "react-native-vector-icons/FontAwesome";

export default function ListItem({ onPressRemove, onPressLeave, onPressEnter, item }) {
    return (
        <View style={styles.item}>
            {item.accepted && (
                <FontAwesome name={"check"} size={21} color={"green"} />
            )}
            {!item.accepted && (
                <FontAwesome name={"question"} size={21} color={"blue"} />
            )}
            <View style={{ flex: 1 }}>
                <TouchableOpacity onPress={() => onPressEnter(item.tripId)} >
                    <Text style={{ marginRight: 10 }}>{item.tripName + " by " + item.hostNickname}</Text>
                    <Text style={{ marginRight: 10 }}>{item.dateRange}</Text>
                    <Text style={{ marginRight: 10 }}>{item.placeSummary}</Text>
                </TouchableOpacity>
            </View>


            <View style={{ width: 30 }}>

                {item.owner && (
                    <TouchableOpacity onPress={() => onPressRemove(item.tripId)} >
                        <FontAwesome name={"trash"} size={21} color={"black"} />
                    </TouchableOpacity>
                )}
                {!item.owner && (
                    <TouchableOpacity onPress={() => onPressLeave(item.tripId)} >
                        <FontAwesome name={"remove"} size={21} color={"black"} />
                    </TouchableOpacity>
                )}

            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    item: {
        padding: 16,
        marginTop: 16,
        borderColor: '#bbb',
        borderWidth: 1,
        borderStyle: "dashed",
        borderRadius: 1,
        borderRadius: 10,

        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        alignItems: 'flex-start' // if you want to fill rows left to right
    },

    container: {
        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        alignItems: 'flex-start' // if you want to fill rows left to right
    }
});