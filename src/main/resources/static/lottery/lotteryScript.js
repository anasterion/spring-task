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
    }).then(() => {
        window.location.href = '/lottery/lotteryList.html';
    });
}