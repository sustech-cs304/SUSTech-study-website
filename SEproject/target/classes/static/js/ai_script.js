document.getElementById('settings-form').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单的默认提交行为

    var temperature = document.getElementById('temperature').value;
    var topP = document.getElementById('top_p').value;
    var customPrompt = document.getElementById('custom-prompt').value;

    // 构建要发送到大模型的数据对象
    var requestData = {
        temperature: temperature,
        topP: topP,
        prompt: customPrompt
    };

    // 你可以在这里添加一个API调用，将requestData对象发送到服务器
    console.log(requestData); // 这里只是一个示例，显示如何收集数据
});

document.getElementById('send-button').addEventListener('click', function() {
    var userInput = document.getElementById('user-input').value;
    if(userInput.trim() !== '') { // 确保用户输入不为空
        displayUserMessage(userInput);
        document.getElementById('user-input').value = ''; // 清空输入框
    }
});

function displayUserMessage(message) {
    var chatMessages = document.getElementById('chat-messages');

    // 创建新的消息元素
    var messageDiv = document.createElement('div');
    messageDiv.classList.add('message', 'user-message');

    // 可以添加用户头像和名称
    var userImage = document.createElement('img');
    userImage.src = '/img/keli.png'; // 替换为用户头像的路径
    userImage.alt = 'User';
    userImage.classList.add('avatar');

    var userName = document.createElement('span');
    userName.classList.add('user-name');
    userName.textContent = '可莉'; // 替换为实际的用户名

    var messageContent = document.createElement('span');
    messageContent.classList.add('message-content');
    messageContent.textContent = message;

    // 组装消息
    messageDiv.appendChild(userImage);
    messageDiv.appendChild(userName);
    messageDiv.appendChild(messageContent);

    // 将消息添加到DOM
    chatMessages.appendChild(messageDiv);

    // 滚动到最新消息
    chatMessages.scrollTop = chatMessages.scrollHeight;
}
