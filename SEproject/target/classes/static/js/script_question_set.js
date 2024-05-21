document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const quizId = params.get('quiz');  // 从 URL 获取 quiz 参数
    console.log(quizId);
    console.log("hahahaah");

    if (quizId) {
        document.getElementById('quizIdText').innerText = quizId;  // 显示当前题库ID

        // 构建请求 URL，这里假设您的服务器有一个 API 或静态文件来提供数据
        const url = `/data/${quizId}.json`;

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
    console.log("hahahaah");

    document.getElementById('quizForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const answers = {};

        formData.forEach((value, key) => {
            if (!answers[key]) {
                answers[key] = [];
            }
            answers[key].push(value);
        });

        fetch('/save-answers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(answers) // 将答案对象转换为 JSON 字符串
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log("success ！！！");
                // 使用返回的 URL 进行重定向
                if (data.url) {
                    console.log("success");
                    window.location.href = data.url;
                } else {
                    // 在出错时重定向到错误页面
                    window.location.href = '/error-page';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                // 在出错时重定向到错误页面
                window.location.href = '/error-page';
            });



        // fetch('/save-answers', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json'
        //     },
        //     body: JSON.stringify(answers)
        // })
        //     .then(response => response.json())
        //     .then(data => {
        //         console.log('Answers saved:', data);
        //         alert('答案已保存');
        //     })
        //     .catch(error => console.error('Error saving answers:', error));
    });
});

function displayQuestions(questions) {
    const container = document.getElementById('quizForm');
    if (!container) {
        console.error('Quiz form container not found!');
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
            const optionLabel = document.createElement('label');
            optionLabel.classList.add('option-label');

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = `question_${index}`; // 可以根据需要调整name属性
            checkbox.value = option;
            checkbox.id = `question_${index}_option_${optionIndex}`;

            const optionText = document.createTextNode(option);

            optionLabel.appendChild(checkbox);
            optionLabel.appendChild(optionText);
            optionsList.appendChild(optionLabel);
        });
        questionElem.appendChild(optionsList);

        container.appendChild(questionElem);
    });
}
