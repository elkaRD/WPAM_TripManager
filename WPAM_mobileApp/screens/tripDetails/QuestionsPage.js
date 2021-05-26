import * as React from 'react';
import { useState, useEffect } from 'react';
import { Text, View, Button, FlatList, TouchableOpacity } from 'react-native';
import TodoItem from '../../components/voter/todoItem';
import FontAwesome from "react-native-vector-icons/FontAwesome";
import CommonDataManager, { urls } from '../../Properties';
import Dialog from "react-native-dialog";

export const QuestionsPage = (props) => {

    const [questions, setQuestions] = useState({});
    const [questionIds, setQuestionIds] = useState([]);

    const [curQuestionIter, setCurQuestionIter] = useState(-1);
    const [questionsSize, setQuestionsSize] = useState(-1);

    const prepareData = () => {

        let q = {};
        let ids = [];
        for (const question of props.tripDetails['questions']) {
            q[question.questionId] = question;
            q[question.questionId].answers = q[question.questionId].answers.map(ans => ({ ...ans, key: ans.answerId }));
            ids.push(question.questionId);
        }

        setQuestions(q);
        setQuestionsSize(props.tripDetails['questions'].length);
        setQuestionIds(ids);
    }

    useEffect(() => {
        prepareData();
        setCurQuestionIter(0);
    }, []);

    useEffect(() => {
        prepareData();
    }, [props]);


    const pressHandlerTodo = (answerId, isChecked) => {

        props.setLoading(true);

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: props.tripDetails.general.tripId,
            questionId: questionIds[curQuestionIter],
            answerId: answerId,
            selected: isChecked,
        };

        fetch(urls.questionSelectAnswer, {
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
                    alert(`ERROR SELECTING ANSWER ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR SELECTING ANSWER ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    // alert(`Selected answer`);
                    console.log("SUCCESS SELECTING ANSWER");
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => props.refreshData());
    }

    const [questionDialogVisible, setQuestionDialogVisible] = useState(false);
    const [answerDialogVisible, setAnswerDialogVisible] = useState(false);
    const [newQuestion, setNewQuestion] = useState('');
    const [newAnswer, setNewAnswer] = useState('');

    const onAddAnswerClick = () => {
        setAnswerDialogVisible(true);
    }

    const onAddQuestionClick = () => {
        setQuestionDialogVisible(true);
    }

    const onSubmitAnswerClick = () => {

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: props.tripDetails.general.tripId,
            questionId: questionIds[curQuestionIter],
            answer: newAnswer,
        };

        fetch(urls.questionAddAnswer, {
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
                    alert(`ERROR CREATING QUESTION ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR CREATING QUESTION ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    // alert(`Selected answer`);
                    console.log("SUCCESS CREATING QUESTION");
                    onCancelClick();
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => props.refreshData());
    }

    const onSubmitQuestionClick = () => {

        const data = {
            deviceId: CommonDataManager.getInstance().getDeviceId(),
            tripId: props.tripDetails.general.tripId,
            question: newQuestion,
            answers: [],
        };

        fetch(urls.questionAddQuestion, {
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
                    alert(`ERROR CREATING QUESTION ${json.errorCode} (${json.errorDesc})`);
                    console.log(`ERROR CREATING QUESTION ${json.errorCode} (${json.errorDesc})`);
                }
                else {
                    // alert(`Selected answer`);
                    console.log("SUCCESS CREATING QUESTION");
                    onCancelClick();
                }
            })
            .catch((error) => { alert(error); console.log(error) })
            .finally(() => props.refreshData());
    }

    const onCancelClick = () => {
        setAnswerDialogVisible(false);
        setQuestionDialogVisible(false);
    }

    const onNextQuestionClick = () => {
        setCurQuestionIter((curQuestionIter + 1) % questionsSize);
    }

    const onPrevQuestionClick = () => {
        setCurQuestionIter((curQuestionIter - 1 + questionsSize) % questionsSize);
    }

    return (
        <View style={{ flex: 1 }}>

            { questionDialogVisible && (
                <Dialog.Container visible={questionDialogVisible} statusBarTranslucent>
                    <Dialog.Title>Add question</Dialog.Title>
                    <Dialog.Description>
                        Enter question
                </Dialog.Description>
                    <Dialog.Input onChangeText={questionText => setNewQuestion(questionText)} style={{ backgroundColor: 'gainsboro' }}></Dialog.Input>
                    <Dialog.Button label="Cancel" onPress={onCancelClick} />
                    <Dialog.Button label="Add" onPress={onSubmitQuestionClick} />
                </Dialog.Container>
            )}

            { answerDialogVisible && (
                <Dialog.Container visible={answerDialogVisible} statusBarTranslucent>
                    <Dialog.Title>Add answer</Dialog.Title>
                    <Dialog.Description>
                        Enter answer
                </Dialog.Description>
                    <Dialog.Input onChangeText={answerText => setNewAnswer(answerText)} style={{ backgroundColor: 'gainsboro' }}></Dialog.Input>
                    <Dialog.Button label="Cancel" onPress={onCancelClick} />
                    <Dialog.Button label="Add" onPress={onSubmitAnswerClick} />
                </Dialog.Container>
            )}

            <View style={{ width: '40%', height: 50 }} >
                <Button onPress={onAddQuestionClick} title="ADD QUESTION" disabled={!props.tripDetails.allowAddingQuestions && !props.tripDetails.general.owner} />
            </View>

            { questionsSize <= 0 && (
                <Text>No questions yet</Text>
            )}

            { questionsSize > 0 && (

                <>
                    <Text>[{curQuestionIter + 1}/{questionsSize}] {questions[questionIds[curQuestionIter]].question}</Text>

                    <FlatList
                        data={questions[questionIds[curQuestionIter]].answers}
                        keyExtractor={(item) => item.answerId}
                        renderItem={({ item }) => (
                            <TodoItem item={item} checkboxChangeHandler={pressHandlerTodo} />
                        )}
                    />

                    <View style={{ flexDirection: 'row', paddingBottom: 10, justifyContent: 'space-between' }}>
                        <View style={{ width: '20%', height: 50 }} >
                            <TouchableOpacity onPress={onPrevQuestionClick} >
                                <FontAwesome name={"arrow-left"} size={45} color={"black"} />
                            </TouchableOpacity>
                        </View>
                        {(props.tripDetails.allowAddingQuestions || props.tripDetails.general.owner) && (
                            <View style={{ width: '40%', height: 50 }} >
                                <Button onPress={onAddAnswerClick} title="ADD ANSWER" />
                            </View>
                        )}
                        <View style={{ width: '20%', height: 50 }} >
                            <TouchableOpacity onPress={onNextQuestionClick} >
                                <FontAwesome name={"arrow-right"} size={45} color={"black"} />
                            </TouchableOpacity>
                        </View>
                    </View>

                    <View styles={{
                        flex: 1,
                        flexDirection: 'column',
                    }}>


                    </View>

                </>
            )}

        </View>);
}

