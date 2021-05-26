import * as React from 'react';
import { Text, View, Button, FlatList } from 'react-native';
import TodoItem from '../../components/voter/todoItem';
import { useNavigation } from '@react-navigation/native';

export const TitleText = (props) => {
    return (
        <Text style={{ fontSize: 48, color: 'black' }}>
            {props.label}
        </Text>
    );
}

export const UserPage = (props) => {

    const navigation = useNavigation();

    return (
        <View style={{ flex: 1 }}>
            < View style={{ flex: 1 }}>

                <>
                    <FlatList
                        data={props.tripDetails.nicknames}
                        keyExtractor={(item) => item.id}
                        renderItem={({ item }) => (
                            <TodoItem item={item} />
                        )}
                    />
                </>


            </View>

            <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                <Button onPress={props.onGetCodeClick} title="Get invite code" disabled={!props.tripDetails.general.owner && !props.tripDetails.allowInviting} />
                <Button onPress={() => { navigation.navigate('ModalCreateTrip') }} title="Create new trip" />
            </View>

        </View >
    );
}