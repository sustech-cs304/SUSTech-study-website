document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const quizId = params.get('id');  // 从 URL 获取 quizId 参数
    console.log(quizId);
    console.log("hahahaah");

    if (quizId) {
        document.getElementById('quizIdText').innerText = quizId;  // 显示当前题库ID

        // 构建请求 URL，这里假设本地 JSON 文件的命名规则为 quiz_<quizId>.json
        const url = `/data/quiz${quizId}.json`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                displayQuestions(data);
            })
            .catch(error => {
                console.error('Failed to fetch questions:', error);
                document.getElementById('questions-container').innerText = 'Failed to load questions.';
            });
    } else {
        console.error('Quiz ID is missing from the URL');
        document.getElementById('questions-container').innerText = 'Quiz ID is missing.';
    }

    document.getElementById('quizForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        let answersString = '';

        const selectedAnswers = document.querySelectorAll('input[type="radio"]:checked');
        selectedAnswers.forEach(answer => {
            const trimmedValue = answer.value.substring(2);
            answersString += trimmedValue;
        });

        console.log('Submitting answers:', answersString);  // 添加日志
        alert('Submitting answers: ' + answersString);  // 使用 alert 显示答案字符串

        const url = `/save-answers?answers=${encodeURIComponent(answersString)}&quizId=${encodeURIComponent(quizId)}`;
        console.log('Request URL:', url);  // 添加日志

        fetch(url, {
            method: 'POST'
        })
            .then(response => {
                console.log('Response status:', response.status);  // 添加日志
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Received response:', data);  // 添加日志
                if (data.url) {
                    window.location.href = data.url;
                } else {
                    window.location.href = '/error-page';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                window.location.href = '/error-page';
            });
    });
});

function displayQuestions(questions) {
    const container = document.getElementById('questions-container');
    if (!container) {
        console.error('Questions container not found!');
        return;
    }

    container.innerHTML = ''; // 清空容器现有内容

    questions.forEach((item, index) => {
        const questionElem = document.createElement('div');
        questionElem.classList.add('question');

        const questionTitle = document.createElement('h3');
        questionTitle.innerText = `问题 ${index + 1}: ${item.question}`;
        questionElem.appendChild(questionTitle);

        const optionsList = document.createElement('ul');
        item.options.forEach((option, optionIndex) => {
            const optionItem = document.createElement('li');

            const optionLabel = document.createElement('label');
            optionLabel.classList.add('option-label');

            const radioButton = document.createElement('input');
            radioButton.type = 'radio';
            radioButton.name = `question_${index}`; // 使用相同的name属性，以实现单选效果
            radioButton.value = option;
            radioButton.id = `question_${index}_option_${optionIndex}`;

            const optionText = document.createTextNode(option);

            optionLabel.appendChild(radioButton);
            optionLabel.appendChild(optionText);
            optionItem.appendChild(optionLabel);
            optionsList.appendChild(optionItem);
        });
        questionElem.appendChild(optionsList);

        container.appendChild(questionElem);
    });
}
