// 解析URL参数获取帖子ID
const postId = window.location.pathname.split('/').pop();

// 加载帖子详细信息
function loadPostDetails() {
    if (postId) {
        fetch(`/posts/id=${postId}`)
            .then(response => response.json())
            .then(post => {
                const postDetailContainer = document.getElementById('post-detail');
                // 假设用户名和头像文件名一致，并存储在img目录下
                const avatarPath = `/img/User.png`;
                const sidebar = document.querySelector('.sidebar');
                sidebar.querySelector('.username').textContent = post.author;
                sidebar.querySelector('.user-avatar').src = avatarPath;
                postDetailContainer.innerHTML = `
                    <h1>${post.title}</h1>
                    <p>分类: ${post.category}</p>
                    <p>发布时间: ${new Date(post.publishTime).toLocaleString()}</p>
                    <p>作者: ${post.author}</p>
                    <article>${post.content}</article>
                `;
            })
            .catch(error => {
                console.error('Error loading post details:', error);
                // 将错误信息显示在网页上
                document.getElementById('post-detail').textContent = '加载帖子详情出错: ' + error.toString();
            });
    } else {
        console.error('Post ID is missing in the URL');
    }
}

// 加载评论
function loadComments() {
    fetch(`/posts/${postId}/comments`)
        .then(response => response.json())
        .then(comments => {
            const commentsList = document.getElementById('comments-list');
            commentsList.innerHTML = ''; // 清空评论列表
            comments.forEach(comment => {
                const commentElement = document.createElement('div');
                commentElement.className = 'comment';
                commentElement.innerHTML = `
                <p class="comment-author">${comment.author}</p>
                <p class="comment-content">${comment.content}</p>
                <p class="comment-date">${new Date(comment.publishTime).toLocaleString()}</p>
                <div class="comment-actions">
                    <button class="like-button" data-comment-id="${comment.id}">赞 (${comment.likes})</button>
                    <button class="dislike-button" data-comment-id="${comment.id}">踩 (${comment.dislikes})</button>
                </div>
            `;
                commentsList.appendChild(commentElement);
            });
        })
        .catch(error => {
            console.error('Error loading comments:', error);
        });
}


// document.addEventListener('DOMContentLoaded', function() {
//     loadPostDetails();
//     loadComments();
//
//     document.body.addEventListener('click', function(event) {
//         console.log("正在点击拉！");
//         if (event.target.id === 'submit-comment') {
//             console.log("点击提交评论按钮！♪(^∇^*)")
//             const commentContent = document.getElementById('comment-content').value;
//             if (commentContent) {
//                 const commentData = {
//                     content: commentContent,
//                     author: "keli", // 暂定作者为 "keli"
//                     publishTime: new Date().toISOString() // 获取当前时间
//                 };
//                 fetch(`/posts/${postId}/comments`, {
//                     method: 'POST',
//                     headers: {
//                         'Content-Type': 'application/json'
//                     },
//                     body: JSON.stringify(commentData)
//                 })
//                     .then(response => {
//                         if (response.ok) {
//                             loadComments(); // 重新加载评论
//                             document.getElementById('comment-content').value = ''; // 清空输入框
//                         } else {
//                             throw new Error('Failed to submit comment');
//                         }
//                     })
//                     .catch(error => {
//                         console.error('Error submitting comment:', error);
//                     });
//             }
//         }
//         else if (event.target.classList.contains('like-button')) {
//             // 处理点赞按钮
//             const commentId = event.target.dataset.commentId;
//             fetch(`/comments/${commentId}/like`, {
//                 method: 'POST'
//             })
//                 .then(response => {
//                     if (response.ok) {
//                         loadComments(); // 重新加载评论
//                     } else {
//                         throw new Error('Failed to like comment');
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error liking comment:', error);
//                 });
//         } else if (event.target.classList.contains('dislike-button')) {
//             // 处理点踩按钮
//             const commentId = event.target.dataset.commentId;
//             fetch(`/comments/${commentId}/dislike`, {
//                 method: 'POST'
//             })
//                 .then(response => {
//                     if (response.ok) {
//                         loadComments(); // 重新加载评论
//                     } else {
//                         throw new Error('Failed to dislike comment');
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error disliking comment:', error);
//                 });
//         }
//     });
// });

document.addEventListener('DOMContentLoaded', function() {
    loadPostDetails();
    loadComments();

    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'submit-comment') {
            const commentContent = document.getElementById('comment-content').value;
            if (commentContent) {
                const commentData = {
                    content: commentContent,
                    author: "keli", // 暂定作者为 "keli"
                    publishTime: new Date().toISOString() // 获取当前时间
                };
                fetch(`/posts/${postId}/comments`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(commentData)
                })
                    .then(response => response.json())
                    .then(newComment => {
                        // 动态插入新评论
                        const commentsList = document.getElementById('comments-list');
                        const commentElement = document.createElement('div');
                        commentElement.className = 'comment';
                        commentElement.innerHTML = `
                        <img src="/img/User.png" alt="用户头像" class="comment-avatar">
                        <div class="comment-content">
                            <p class="comment-username">${newComment.author}</p>
                            <p class="comment-text">${newComment.content}</p>
                            <p class="comment-date">${new Date(newComment.publishTime).toLocaleString()}</p>
                            <div class="comment-actions">
                                <button class="like-button" data-comment-id="${newComment.id}">赞 (${newComment.likes})</button>
                                <button class="dislike-button" data-comment-id="${newComment.id}">踩 (${newComment.dislikes})</button>
                            </div>
                        </div>
                    `;
                        commentsList.appendChild(commentElement); // 添加新评论到评论列表的末尾
                        document.getElementById('comment-content').value = ''; // 清空输入框
                    })
                    .catch(error => {
                        console.error('Error submitting comment:', error);
                    });
            }
        } else if (event.target.classList.contains('like-button')) {
            // 处理点赞按钮
            const commentId = event.target.dataset.commentId;
            fetch(`/comments/${commentId}/like`, {
                method: 'POST'
            })
                .then(response => {
                    if (response.ok) {
                        loadComments(); // 重新加载评论
                    } else {
                        throw new Error('Failed to like comment');
                    }
                })
                .catch(error => {
                    console.error('Error liking comment:', error);
                });
        } else if (event.target.classList.contains('dislike-button')) {
            // 处理点踩按钮
            const commentId = event.target.dataset.commentId;
            fetch(`/comments/${commentId}/dislike`, {
                method: 'POST'
            })
                .then(response => {
                    if (response.ok) {
                        loadComments(); // 重新加载评论
                    } else {
                        throw new Error('Failed to dislike comment');
                    }
                })
                .catch(error => {
                    console.error('Error disliking comment:', error);
                });
        }
    });
});



