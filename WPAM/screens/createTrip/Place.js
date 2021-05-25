import * as React from 'react';
import { Text, View } from 'react-native';
import { FontAwesome } from '@expo/vector-icons';


export const Place = (props) => {

    return (

        <View style={[{

            flexDirection: "row"
        }]}>

            <View style={{ flex: 3, backgroundColor: "green" }} >
                <Text style={{
                    fontSize: 20, color: 'black',
                    marginRight: 5,
                    marginLeft: 5,
                    marginTop: 10,
                    paddingTop: 20,
                    paddingBottom: 20,
                    backgroundColor: '#68a0cf',
                    borderRadius: 10,
                    borderWidth: 1,
                    borderColor: '#fff'
                }}>
                    {props.addressName}
                </Text>
            </View>
            <View style={{ flex: 1, backgroundColor: "red" }} >
                <FontAwesome.Button name="facebook" backgroundColor="#3b5998" onPress={props.onDeletePlaceClick(props.key)}>
                </FontAwesome.Button>

            </View>

        </View >
    );
}