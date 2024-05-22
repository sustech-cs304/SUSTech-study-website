const ENDPOINT = 'http://10.16.99.171:7861/';

async function ask(query) {
    const data = {
        query: query,
        knowledge_base_name: "samples",
        top_k: 3,
        score_threshold: 1.5,
        stream: false,
        model_name: "chatglm3-6b",
        temperature: 0.01,
        max_tokens: 0,
        prompt_name: "default"
    };

    try {
        const response = await fetch(ENDPOINT + 'chat/knowledge_base_chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        const responseData = await response.json();
        return responseData.answer.replace('\\n', '\n');
    } catch (error) {
        console.error('Error:', error);
        return '抱歉，处理您的请求时出错。';
    }
}

// 使用示例
ask('你好').then(answer => console.log(answer));
