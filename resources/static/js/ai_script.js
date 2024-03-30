const ENDPOINT = 'http://10.16.99.171:7861/';
var conversationHistory = [];
var promptAdded = false; // 标志变量，用于跟踪是否已添加自定义提示的对话历史



async function ask(query, top_k, temperature, stream) {
    const data = {
        query: query,
        knowledge_base_name: "samples",
        top_k: top_k,
        history: conversationHistory, // 将 history 数组添加到请求数据中
        score_threshold: 1.5,
        stream: stream,
        model_name: "chatglm3-6b",
        temperature: temperature,
        max_tokens: 0,
        prompt_name: "default"
    };

    const response = await fetch(ENDPOINT + 'chat/knowledge_base_chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    const reader = response.body.getReader();
    let decoder = new TextDecoder();
    let previousChunk = '';
    try {
        while (true) {
            const { value, done } = await reader.read();
            if (done) break;

            let chunk = decoder.decode(value, { stream: true });
            chunk = previousChunk + chunk;
            let pattern = /\{"answer": "([^"]+)"\}/g;
            let result;
            let lastIndex = 0;

            while ((result = pattern.exec(chunk)) !== null) {
                lastIndex = pattern.lastIndex; // Update last index of the pattern
                displayBotMessage(result[1]);
            }

            previousChunk = chunk.slice(lastIndex); // Save the remaining chunk

            // Check for the end of the response indicated by the "docs" key
            if (chunk.includes('"docs":')) {
                // 将用户的问题添加到 history 中
                conversationHistory.push({
                    role: "user",
                    content: query
                });

                // 将机器人的回答添加到 history 中
                if (currentBotMessageContent) {
                    conversationHistory.push({
                        role: "assistant",
                        content: currentBotMessageContent.innerHTML.replace(/<br>/g, '\n').trim() // 将 HTML 的换行标签转换回换行符
                    });
                }

                // 限制 history 的长度
                if (conversationHistory.length > 10) { // 每轮对话包括用户和助手两条记录，所以最多存储 10 条记录
                    conversationHistory = conversationHistory.slice(-10);
                }

                break;
            }
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function addPrompt(prompt, promptAdded) {
    if(promptAdded){
        // 加一组防止bug
        conversationHistory.push({
            role: "user",
            content: prompt
        });

        conversationHistory.push({
            role: "assistant",
            content: "我明白了，我会严格遵守指令"
        });
    }else{
        // 加一组防止bug
        conversationHistory.push({
            role: "user",
            content: "你是谁"
        });

        conversationHistory.push({
            role: "assistant",
            content: "我是南科大专属ai，主人！！！"
        });
    }
}

document.getElementById('send-button').addEventListener('click', async function() {
    var userInput = document.getElementById('user-input').value;
    if (userInput.trim() !== '') {
        handleUserQuestion(userInput);
        document.getElementById('user-input').value = '';
        var temperature = parseFloat(document.getElementById('temperature').value);
        var top_k = parseInt(document.getElementById('top_k').value);
        var prompt = document.getElementById('custom-prompt').value;

        // 如果 prompt 不为空且尚未添加到对话历史中，则添加一组对应的 history
        addPrompt(prompt,promptAdded);
        if(!promptAdded){
            promptAdded = true;
        }

        // 注意：根据你的实际API和需求可能需要修改这里的请求细节
        await ask(userInput, top_k, temperature, true);
    }
});

document.getElementById('user-input').addEventListener('keydown', async function(event) {
    if (event.key === 'Enter' && !event.shiftKey) {
        // 当按下 Enter 键且没有按下 Shift 键时发送消息
        event.preventDefault(); // 防止默认的换行行为
        var userInput = this.value;
        if (userInput.trim() !== '') {
            handleUserQuestion(userInput);
            document.getElementById('user-input').value = '';
            var temperature = parseFloat(document.getElementById('temperature').value);
            var top_k = parseInt(document.getElementById('top_k').value);
            var prompt = document.getElementById('custom-prompt').value;

            addPrompt(prompt,promptAdded);
            if(!promptAdded){
                promptAdded = true;
            }

            await ask(userInput, top_k, temperature, true);
        }
    } else if (event.key === 'Enter' && event.shiftKey) {
        // 当按下 Shift + Enter 键时允许换行
        console.log('Shift+Enter pressed, allowing line break');
    }
});

function displayUserMessage(message) {
    var chatMessages = document.getElementById('chat-messages');

    var messageDiv = document.createElement('div');
    messageDiv.classList.add('message', 'user-message');

    var userImage = document.createElement('img');
    userImage.src = '/img/User.png';
    userImage.alt = 'User';
    userImage.classList.add('avatar');

    var userName = document.createElement('span');
    userName.classList.add('user-name');
    userName.textContent = '你';

    var messageContent = document.createElement('span');
    messageContent.classList.add('message-content');
    messageContent.textContent = message;

    messageDiv.appendChild(userImage);
    messageDiv.appendChild(userName);
    messageDiv.appendChild(messageContent);
    chatMessages.appendChild(messageDiv);

    chatMessages.scrollTop = chatMessages.scrollHeight;
}

var currentBotMessageContent = null;

function createBotMessage() {
    var chatMessages = document.getElementById('chat-messages');

    var messageDiv = document.createElement('div');
    messageDiv.classList.add('message', 'bot-message');

    var botImage = document.createElement('img');
    botImage.src = '/img/Bot.png';
    botImage.alt = 'Bot';
    botImage.classList.add('avatar');

    var botName = document.createElement('span');
    botName.classList.add('bot-name');
    botName.textContent = 'sustech助手';

    currentBotMessageContent = document.createElement('span');
    currentBotMessageContent.classList.add('message-content');

    messageDiv.appendChild(botImage);
    messageDiv.appendChild(botName);
    messageDiv.appendChild(currentBotMessageContent);
    chatMessages.appendChild(messageDiv);

    chatMessages.scrollTop = chatMessages.scrollHeight;
}

function displayBotMessage(message) {
    if (currentBotMessageContent) {
        // 移除中文字符之间的空格
        const cleanedMessage = message.replace(/\s+/g, '');
        // 将换行符替换为HTML的换行标签
        const finalMessage = cleanedMessage.replace(/\n/g, '<br>');
        currentBotMessageContent.innerHTML += finalMessage;
        var chatMessages = document.getElementById('chat-messages');
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
}




// 当用户发送提问时调用此函数
function handleUserQuestion(question) {
    displayUserMessage(question);
    currentBotMessageContent = null; // Reset the current bot message
    createBotMessage();
}

// 快捷键

