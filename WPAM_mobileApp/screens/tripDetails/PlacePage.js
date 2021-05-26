import * as React from 'react';
import { useState, useEffect, useReducer } from 'react';
import { Text, View, Button, FlatList } from 'react-native';
import TodoItem from '../../components/voter/todoItem';
import CommonDataManager, { urls, weatherApiKey } from '../../Properties';
import Dialog from "react-native-dialog";
import { useNavigation } from '@react-navigation/native';
import { ModalMapPicker } from '../createTrip/ModalMapPicker';

export const PlacePage = (props) => {

  const navigation = useNavigation();

  const [dialogVisible, setDialogVisible] = useState(false);
  const [newPlaceName, setNewPlaceName] = useState("");

  const [places, setPlaces] = useState([]);

  const [temperatures, setTemperatures] = useState({});


  const prepareData = () => {
    let downloadedPlaces = []
    let iter = 0;

    let begDay = null;
    let endDay = null;
    if (props.tripDetails.days != null) {
      begDay = props.tripDetails.days[0];
      endDay = props.tripDetails.days[props.tripDetails.days.length - 1];
    }

    let placesWithWeather = []

    if (props.tripDetails['places'] != null) {
      for (const place of props.tripDetails['places']) {
        downloadedPlaces.push({ ...place, key: place['placeId'], percent: place['votes'] * 100.0 / props.tripDetails['placeAllVotes'] });
        iter += 1;

        if (place.containsCoords && props.tripDetails.days != null) {
          let lon = place.lon;
          let lat = place.lat;
          let placeId = place.placeId;
          placesWithWeather.push({ lon: lon, lat: lat, placeId: placeId })
          console.log("DEBUG PLACE", place);
        }
      }
    }

    console.log("PLACES FORMAT", downloadedPlaces);

    setPlaces(downloadedPlaces);

    for (const placeIndex in placesWithWeather) {
      const place = placesWithWeather[placeIndex];
      updateWeather(place.lat, place.lon, place.placeId);
    }
  }

  const [, forceUpdate] = useReducer(x => x + 1, 0);

  const updateWeather = (lat, lon, placeId) => {

    if (placeId in temperatures)
      return;

    fetch(`http://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${weatherApiKey}`)
      .then((response) => response.json())
      .then((json) => {
        console.log("WEATHER RESPONSE", json);

        try {
          let tempMin = Math.round(parseFloat(json?.main?.temp_min) - 273.15).toString();
          let tempMax = Math.round(parseFloat(json?.main?.temp_max) - 273.15).toString();

          console.log(`TEMP RANGE ${tempMin}   ${tempMax}`);

          let tempAnnotation = null;
          if (tempMin === tempMax) {
            tempAnnotation = " (" + tempMin + " " + String.fromCharCode(176) + "C)";
          }
          else {
            tempAnnotation = " (" + tempMin + " - " + tempMax + " " + String.fromCharCode(176) + "C)";
          }

          let tempTemp = temperatures;
          tempTemp[placeId] = tempAnnotation;
          setTemperatures(tempTemp);
          forceUpdate();
        }
        catch (error) {
          console.log("error parsing temperatures", error);
        }
      })
      .catch((error) => {
        alert("ERROR GETTING WEATHER", error);
        console.log("ERROR GETTING WEATHER ", error)
      })
      .finally(() => {
      });


  }

  useEffect(() => {
    prepareData();
  }, []);

  useEffect(() => {
    prepareData();
  }, [props]);

  const pressHandlerTodo = (placeId, isChecked) => {

    if (props.tripDetails.general.accepted)
      return;

    console.log('CHECKBOX TOOGLED', placeId, isChecked);

    props.setLoading(true);

    const data = {
      deviceId: CommonDataManager.getInstance().getDeviceId(),
      tripId: props.tripDetails.general.tripId,
      placeId: placeId,
      selected: isChecked,
    };

    fetch(urls.placeSelectPlace, {
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
          alert(`ERROR SELECTING PLACE ${json.errorCode} (${json.errorDesc})`);
          console.log(`ERROR SELECTING PLACE ${json.errorCode} (${json.errorDesc})`);
        }
        else {
          // alert(`Selected place`);
          console.log("SUCCESS SELECTING PLACE");
        }
      })
      .catch((error) => { alert(error); console.log(error) })
      .finally(() => props.refreshData());
  }

  const displayMapHandler = (placeId) => {

    console.log("DISPLAY ON MAP", placeId);

    for (const place of places) {
      if (place.placeId === placeId) {
        console.log("FOUND", place);
        CommonDataManager.getInstance().setPlaceName(place.addressName);
        CommonDataManager.getInstance().setPlaceLon(place.lon);
        CommonDataManager.getInstance().setPlaceLat(place.lat);
        break;
      }
    }

    CommonDataManager.getInstance().setAllPlaces(places);

    navigation.navigate('ModalDisplayOnMap');
  }

  const onAddPlaceTextClick = () => {
    setDialogVisible(true);
  }

  const onAddPlaceMapClick = () => {
    setDialogVisible(false);
    setPickerMode(true);
    props.setHideUi(true);
  }

  const submitPlaceName = () => {
    if (newPlaceName.length > 1) {

      props.setLoading(true);

      const data = {
        deviceId: CommonDataManager.getInstance().getDeviceId(),
        tripId: props.tripDetails.general.tripId,
        name: newPlaceName,
        containsCoords: false,
      };

      fetch(urls.placeAddPlace, {
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
            alert(`ERROR ADDING ANSWER ${json.errorCode} (${json.errorDesc})`);
            console.log(`ERROR ADDING ANSWER ${json.errorCode} (${json.errorDesc})`);
          }
          else {
            // alert(`Added answer`);
            console.log("SUCCESS ADDING ANSWER");
            setDialogVisible(false);
          }
        })
        .catch((error) => { alert(error); console.log(error) })
        .finally(() => {
          props.refreshData();
          setDialogVisible(false);
        });
    } else {
      Alert.alert('OOPS', 'Todo must be over 1 character long', [
        { text: 'Understood', onPress: () => console.log('alert closed') }
      ]);
    }
  };

  const [pickerMode, setPickerMode] = useState(false);

  const onAddPlaceMapCancelClick = () => {
    setPickerMode(false);
    props.setHideUi(false);
  }

  const onAddPlaceMapConfirmClick = (lon, lat) => {

    console.log("COORDS FROM MAP PICKER", lon, lat);

    const data = {
      deviceId: CommonDataManager.getInstance().getDeviceId(),
      tripId: props.tripDetails.general.tripId,
      name: newPlaceName,
      containsCoords: true,
      coordLon: lon.toString(),
      coordLat: lat.toString(),
    };

    fetch(urls.placeAddPlace, {
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
          alert(`ERROR ADDING ANSWER ${json.errorCode} (${json.errorDesc})`);
          console.log(`ERROR ADDING ANSWER ${json.errorCode} (${json.errorDesc})`);
        }
        else {
          // alert(`Added answer`);
          console.log("SUCCESS ADDING ANSWER");
          setDialogVisible(false);
        }
      })
      .catch((error) => { alert(error); console.log(error) })
      .finally(() => {
        props.refreshData();
        setDialogVisible(false);
      });

    setDialogVisible(false);


    setPickerMode(false);
    props.setHideUi(false);
  }

  const getItems = () => {
    let items = places;

    for (let itemIndex in items) {

      if (items[itemIndex].placeId.toString() in temperatures) {
        items[itemIndex].tempAnnotation = temperatures[items[itemIndex].placeId.toString()]
      }
    }

    return items;
  }

  const onCancelClick = () => {
    setDialogVisible(false);
  }

  return (
    <View style={{ flex: 1 }}>

      {!pickerMode && (
        <>
          {
            dialogVisible && (
              <Dialog.Container visible={dialogVisible} statusBarTranslucent>
                <Dialog.Title>Add destination to poll</Dialog.Title>
                <Dialog.Description>
                  Enter destination name and optionally pick location on the map
                </Dialog.Description>

                <Dialog.Input onChangeText={placeName => setNewPlaceName(placeName)} style={{ backgroundColor: 'gainsboro' }}></Dialog.Input>
                <Dialog.Button label="Cancel" onPress={onCancelClick} />
                <Dialog.Button label="Pick on map" onPress={onAddPlaceMapClick} />
                <Dialog.Button label="Add" onPress={submitPlaceName} />

              </Dialog.Container>
            )
          }

          {getItems().length == 0 && (
            <View >
              <Text style={{
                textAlign: 'center', // <-- the magic
                fontWeight: 'bold',
                fontSize: 18,
                marginTop: 0,

              }}>No places</Text>
            </View>
          )}

          <FlatList
            data={getItems()}
            renderItem={({ item }) => (
              <TodoItem
                item={item}
                checkboxChangeHandler={pressHandlerTodo}
                mapPressHandler={displayMapHandler}
                disabled={props.tripDetails.general.accepted}
              />
            )}
          />

          <Button
            onPress={onAddPlaceTextClick}
            title="ADD PLACE"
            disabled={(!props.tripDetails.allowAddingPlaces && !props.tripDetails.general.owner) || props.tripDetails.general.accepted}
          />

        </>
      )}

      {pickerMode && (
        <>
          <ModalMapPicker placeName={newPlaceName} onCancelClick={onAddPlaceMapCancelClick} onSaveClick={onAddPlaceMapConfirmClick} />
        </>
      )}

    </View>);
}
