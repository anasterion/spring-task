function createParticipant() {
    const email = document.getElementById('email').value;
    const age = document.getElementById('age').value;
    const lottery_id = document.getElementById('lottery_id').value;

    fetch('/registration', {
        method: 'POST',
        body: JSON.stringify({
            email: email,
            age: age,
            lotteryId: lottery_id
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.href = "/participant/participantList.html";
        } else {
            alert(response.reason);
        }
    });
}

function loadParticipants() {
    fetch('/get-participant-list', {
        method: 'GET'
    }).then(
        resp => resp.json()
    ).then(participants => {
        for (const participant of participants) {
            addParticipant(participant);
        }
    });
}

function addParticipant(participant) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td>${participant.id}</td>
        <td>${participant.email}</td>
        <td>${participant.age}</td>
        <td>${participant.code}</td>
        <td>${participant.status}</td>
        <td>${participant.lottery ? participant.lottery.title : ""}</td>
        <td>${participant.lottery ? participant.lottery.lotteryStatus : ""}</td>
`;

    document.getElementById('table-body').appendChild(tr);
}