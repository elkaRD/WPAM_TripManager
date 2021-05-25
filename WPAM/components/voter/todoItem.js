import React from 'react'
import { StyleSheet, TouchableOpacity, Text, View } from 'react-native';
import FontAwesome from "react-native-vector-icons/FontAwesome";
import BouncyCheckbox from "react-native-bouncy-checkbox";

export default function TodoItem({ pressHandler, item, checkboxChangeHandler, mapPressHandler, disabled }) {

  const getText = () => {
    //return item.addressName != null ? item.addressName : (item.answer != null ? item.answer : (item.day != null ? item.day : item.nickname));

    let postfix = "";

    if (item.tempAnnotation != null)
      postfix = item.tempAnnotation;

    if (item.addressName != null)
      return item.addressName + postfix;

    if (item.answer != null)
      return item.answer + postfix;

    if (item.day != null)
      return item.day + postfix;

    return item.nickname + postfix;
  }

  return (

    <View style={styles.item}>

      {item?.containsCoords && (
        <>
          <TouchableOpacity onPress={() => mapPressHandler(item.key)} >
            <Text style={{ marginRight: 10 }}>{getText()}</Text>
          </TouchableOpacity>
          <FontAwesome name={"map"} size={21} color={"black"} style={{ flex: 1 }} />

        </>
      )}
      {!item?.containsCoords && (
        <>
          <Text style={{ flex: 1, marginRight: 10 }}>{getText()}</Text>

          {item.host && (
            <>
              <FontAwesome name={"star"} size={21} color={"black"} style={{}} />
            </>
          )}
          {item.curUser && (
            <>
              <FontAwesome name={"user"} size={21} color={"black"} style={{}} />
            </>
          )}

        </>
      )}

      {item.votes > 0 && (<Text style={{ marginRight: 10 }}>{item.votes}</Text>)}

      {pressHandler != null && (
        <View style={{ width: 30 }}>
          <TouchableOpacity onPress={() => pressHandler(item.key)} >
            <FontAwesome name={"trash"} size={21} color={"black"} />
          </TouchableOpacity>
        </View>
      )}

      {checkboxChangeHandler != null && (
        <BouncyCheckbox
          size={25}
          fillColor="red"
          unfillColor="#FFFFFF"
          text=""
          iconStyle={{ borderColor: "red" }}
          isChecked={item?.selected}
          onPress={(isChecked) => {
            checkboxChangeHandler(item.key, isChecked);
          }}
          disabled={disabled}
        />
      )}
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
    alignItems: 'flex-start'
  },

  container: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    alignItems: 'flex-start'
  }
});