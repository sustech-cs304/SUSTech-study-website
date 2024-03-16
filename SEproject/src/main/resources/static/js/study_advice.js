$(document).ready(function() {
    // 当点击提问按钮时处理事件
    $('#ask-button').click(function() {
        var question = $('#question-input').val();
        // 调用你的AI模型API
        $.ajax({
            url: '你的API端点',
            type: 'POST',
            data: JSON.stringify({ question: question }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(response) {
                // 将AI模型的回复显示在页面上
                $('#response-container').text(response.answer);
            }
        });
    });
});
