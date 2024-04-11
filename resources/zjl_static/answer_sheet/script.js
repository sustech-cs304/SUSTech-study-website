document.addEventListener('DOMContentLoaded', function() {
    const quizForm = document.getElementById('quizForm');
    
    fetch('question_set/questions.json')
        .then(response => response.json())
        .then(questions => {
            questions.forEach((question, index) => {
                const questionDiv = document.createElement('div');
                questionDiv.className = 'question';
                
                const questionP = document.createElement('p');
                questionP.textContent = `问题 ${index + 1}: ${question.question}`;
                questionDiv.appendChild(questionP);
                
                question.options.forEach(option => {
                    const optionLabel = document.createElement('label');
                    const optionInput = document.createElement('input');
                    optionInput.type = 'checkbox';
                    optionInput.name = `question${index + 1}`;
                    optionInput.value = option;
                    optionLabel.appendChild(optionInput);
                    optionLabel.append(` ${option}`);
                    questionDiv.appendChild(optionLabel);
                });
                
                quizForm.insertBefore(questionDiv, quizForm.lastElementChild);
            });
        })
        .catch(error => {
            console.error('Could not load questions:', error);
        });

    quizForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(quizForm);
        // 你可以根据需要处理表单数据，例如验证答案或发送到服务器
        // 下面是将表单数据转换为对象的示例
        const answers = {};
        for (let [key, value] of formData.entries()) {
            if (answers[key]) {
                answers[key].push(value);
            } else {
                answers[key] = [value];
            }
        }
        console.log('Form data submitted:', answers);
        // 请在这里添加提交表单数据的代码
    });
});
