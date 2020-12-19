function solution(record = ["Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"]) {
    var answer = [];
    const users = {};
    const actionDictionary = {
        "Enter": "came in",
        "Leave": "has left",
    }
    for(const log of record) {
        const userStatus = log.split(" ");
        const userId = userStatus[1];
        const userName = userStatus[2];
        users[userId] = userName;
    }
    for(const log of record) {
        const userStatus = log.split(" ");
        const userAction = userStatus[0];
        const userId = userStatus[1];
        const translatedAction = actionDictionary[userAction];
        if (translatedAction) {
            answer.push(users[userId] + ' ' + translatedAction);
        }
    }
    return answer;
}

console.log(solution()); // [ 'Prodo came in', 'Ryan came in', 'Prodo has left', 'Prodo came in' ]
