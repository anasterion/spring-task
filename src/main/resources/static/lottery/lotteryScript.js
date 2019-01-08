function createLottery() {
    const title = document.getElementById('title').value;
    const limit = document.getElementById('limit').value;

    fetch('/start-registration', {
        method: 'POST',
        body: JSON.stringify({
            title: title,
            limit: limit
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.href = "/lottery/lotteryList.html";
        } else {
            alert(response.reason);
        }
    });
}

function loadLotteries() {
    fetch('/get-lottery-list', {
        method: 'GET'
    }).then(
        resp => resp.json()
    ).then(lotteries => {
        if (lotteries.length === 0) {
            appendNotFoundMessage();
        }
        for (const lottery of lotteries) {
            addLottery(lottery);
        }
    });
}

function addLottery(lottery) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td>${lottery.id}</td>
        <td>${lottery.title}</td>
        <td>${lottery.participantCount ? lottery.participantCount + " / " + lottery.limit : "0 / " + lottery.limit}</td>
        <td>${lottery.lotteryStatus}</td>
        <td>${lottery.startDate}</td>
        <td>${lottery.endDate ? lottery.endDate : "-"}</td>
`;

    document.getElementById('table-body').appendChild(tr);
}

function stopLottery() {
    const id = document.getElementById('id').value;

    fetch('/stop-registration', {
        method: 'POST',
        body: JSON.stringify({
            id: id,
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.reload();
        } else {
            alert(response.reason);
        }
    });
}

function loadEndedLotteries() {
    fetch('/get-lottery-list/ENDED', {
        method: 'GET'
    }).then(
        resp => resp.json()
    ).then(lotteries => {
        for (const lottery of lotteries) {
            addLottery(lottery);
        }
    });
}

function loadInProgressLotteries() {
    fetch('/get-lottery-list/IN%20PROGRESS', {
        method: 'GET'
    }).then(
        resp => resp.json()
    ).then(lotteries => {
        for (const lottery of lotteries) {
            addLottery(lottery);
        }
    });
}

function chooseWinnerLottery() {
    const id = document.getElementById('id').value;

    fetch('/choose-winner', {
        method: 'POST',
        body: JSON.stringify({
            id: id,
        }),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then((resp) => resp.json()
    ).then(response => {
        if (response.status === 'OK') {
            window.location.href = "/participant/winnerList.html";
        } else {
            alert(response.reason);
        }
    });
}

function loadConcludedLotteries() {
    fetch('/stats', {
        method: 'GET'
    }).then(
        resp => resp.json()
    ).then(lotteries => {
        for (const lottery of lotteries) {
            addConcludedLottery(lottery);
        }
    });
}

function addConcludedLottery(lottery) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td>${lottery.id}</td>
        <td>${lottery.title}</td>
        <td>${lottery.lotteryStatus}</td>
        <td>${lottery.startDate}</td>
        <td>${lottery.endDate}</td>
        <td>${lottery.participantCount}</td>
`;

    document.getElementById('table-body').appendChild(tr);
}

function appendNotFoundMessage() {
    const p = document.createElement('p');

    p.innerHTML = `
        No data matching criteria for this list found
`;

    document.getElementById('h2-body').appendChild(p);
}

// No data matching criteria for this list found