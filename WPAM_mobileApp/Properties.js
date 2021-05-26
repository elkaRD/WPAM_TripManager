
const appAddr = 'http://192.168.1.14:8080';

const trip = appAddr + '/trip';
const place = appAddr + '/place';
const question = appAddr + '/question';
const date = appAddr + '/date';

export const urls = {
    tripCreateTrip: trip + '/createTrip',
    tripJoinTrip: trip + '/joinTrip',
    tripDeleteTrip: trip + '/deleteTrip',
    tripLeaveTrip: trip + '/leaveTrip',
    tripAcceptTrip: trip + '/acceptTrip',
    tripGetTripList: trip + '/getTripList',
    tripGetTripDetails: trip + '/getTripDetails',

    placeAddPlace: place + '/addPlace',
    placeSubmitPlace: place + '/submitPlace',
    placeSelectPlace: place + '/selectPlace',

    questionAddQuestion: question + '/addQuestion',
    questionAddAnswer: question + '/addAnswer',
    questionSubmitAnswer: question + '/submitAnswer',
    questionSelectAnswer: question + '/selectAnswer',

    dateSubmitDates: date + '/submitDates',
}

export const deviceId = 'abc';
export const weatherApiKey = 'API_KEY';

import AsyncStorage from '@react-native-async-storage/async-storage';


const makeId = (length) => {
    var result = [];
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
        result.push(characters.charAt(Math.floor(Math.random() *
            charactersLength)));
    }
    return result.join('');
}

const saveDeviceId = async (value) => {
    try {
        await AsyncStorage.setItem('deviceId', value)

        console.log("GENERATED DEVICE ID", value);
    } catch (e) {
        // saving error
    }
}

const getDeviceId = async () => {
    try {
        console.log("BEFFFFORE");
        const value = await AsyncStorage.getItem('deviceId')
        if (value !== null) {
            // value previously stored
            deviceId = value;

            console.log("READ DEVICE ID", deviceId);
        } else {
            deviceId = makeId(200);
            console.log("Trying to save new deviceId", deviceId);
            saveDeviceId(deviceId);
        }
    } catch (e) {
        // error reading value
    }
}

const storeData = async (value) => {
    try {
        await AsyncStorage.setItem('@storage_Key', value)
    } catch (e) {
        // saving error
    }
}


const getData = async () => {
    try {
        const value = await AsyncStorage.getItem('@storage_Key')
        if (value !== null) {
            // value previously stored
        }
    } catch (e) {
        // error reading value
    }
}


export default class CommonDataManager {

    static myInstance = null;

    _tripId = null;
    _placeName = "";
    _placeLon = "";
    _placeLat = "";
    _allPlaces = [];

    _deviceId = "error";

    static getInstance() {
        if (CommonDataManager.myInstance == null) {
            CommonDataManager.myInstance = new CommonDataManager();

            console.log("CONSTRUCTOR");

            getDeviceId();

            // try {
            //     const value = AsyncStorage.getItem('deviceId')
            //     if (value !== null) {
            //         // value previously stored
            //         _deviceId = value;

            //         console.log("READ DEVICE ID", _deviceId);
            //     } else {
            //         _deviceId = makeId(200);
            //         console.log("GENERATED DEVICE ID", _deviceId);
            //         try {
            //             AsyncStorage.setItem('deviceId', _deviceId)
            //         } catch (e) {
            //             // saving error
            //         }
            //     }
            // } catch (e) {
            //     // error reading value
            // }
        }

        return this.myInstance;
    }

    getTripId() {
        return this._tripId;
    }

    setTripId(id) {
        this._tripId = id;
    }

    getPlaceName() {
        return this._placeName;
    }

    setPlaceName(name) {
        this._placeName = name;
    }

    getPlaceLon() {
        return this._placeLon;
    }

    setPlaceLon(lon) {
        this._placeLon = lon;
    }

    getPlaceLat() {
        return this._placeLat;
    }

    setPlaceLat(lat) {
        this._placeLat = lat;
    }

    getAllPlaces() {
        return this._allPlaces;
    }

    setAllPlaces(places) {
        this._allPlaces = places;
    }

    getDeviceId() {
        return this._deviceId;
    }

    setDeviceId(deviceId) {
        this._deviceId = deviceId;
    }
}