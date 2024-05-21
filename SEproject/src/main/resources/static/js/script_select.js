function startQuiz(quizId) {
    console.log('开始回答题库：' + quizId);
    // 此处应有逻辑来加载指定题库的问题
    // 例如，可以跳转到回答页面，并通过URL参数传递题库ID
    console.log(quizId);
    window.location.href = 'quiz?quiz=' + quizId;
}

function viewHistory(quizId) {
    console.log('查看题库回答历史：' + quizId);
    // 此处应有逻辑来加载指定题库的回答历史
    // 例如，可以跳转到历史页面，并通过URL参数传递题库ID
    window.location.href = 'view_history.html?quiz=' + quizId;
}

