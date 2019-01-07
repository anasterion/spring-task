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
        <td>${lottery.lotteryCapacity + ' / ' + lottery.limit}</td>
        <td>${lottery.lotteryStatus}</td>
        <td>${lottery.startDate}</td>
`;

    document.getElementById('table-body').appendChild(tr);
}