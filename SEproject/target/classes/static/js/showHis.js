// script.js
function getQuizHistory(id, username) {
    // 更新URL
    console.log("Fetching quiz history...");
    const newUrl = `/quiz-history?id=${id}`;
    history.pushState(null, '', newUrl);

    // 发送请求获取数据
    fetch(`/api/quiz-history?id=${id}&username=${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // 更新页面内容
            const historyDiv = document.getElementById('history');
            historyDiv.innerHTML = '';
            data.forEach(history => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'history-item';

                const idP = document.createElement('p');
                idP.textContent = `ID: ${history.id}`;
                itemDiv.appendChild(idP);

                const userP = document.createElement('p');
                userP.textContent = `User: ${history.username}`;
                itemDiv.appendChild(userP);

                const scoreP = document.createElement('p');
                scoreP.textContent = `Score: ${history.score}`;
                itemDiv.appendChild(scoreP);

                historyDiv.appendChild(itemDiv);
            });
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}
