// 解析URL参数获取帖子ID
const postId = window.location.pathname.split('/').pop();

// 加载帖子详细信息
function loadPostDetails() {
    if (postId) {
        fetch(`/posts/${postId}`)
            .then(response => response.json())
            .then(post => {
                const postDetailContainer = document.getElementById('post-detail');
                // 假设用户名和头像文件名一致，并存储在img目录下
                const avatarPath = `img/User.png`;
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

document.addEventListener('DOMContentLoaded', loadPostDetails);
