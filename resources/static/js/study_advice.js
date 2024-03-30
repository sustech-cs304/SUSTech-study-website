function loadPosts() {
    console.log('Loading posts...'); // Debug: 确认函数被调用
    fetch('/posts')
        .then(response => {
            console.log('Got response:', response); // Debug: 查看响应信息
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(posts => {
            console.log('Loaded posts:', posts); // Debug: 查看加载的帖子数据
            const postsContainer = document.getElementById('post-container');
            // 清空当前的帖子列表
            postsContainer.innerHTML = '';

            // 为每个帖子创建HTML元素，并添加到页面中
            posts.forEach(post => {
                const postElement = document.createElement('div');
                postElement.className = 'post';
                postElement.innerHTML = `
                  <div class="title">${post.title}</div>
                  <div class="category">${post.category}</div>
                  <div class="author">${post.author}</div>
                  <div class="date">${new Date(post.publishTime).toLocaleString()}</div>
                `;
                postElement.onclick = function() {
                    window.location.href = `/posts/${post.id}?id=${post.id}`;
                };
                postsContainer.appendChild(postElement);
            });
        })
        .catch(error => {
            console.error('Error loading posts:', error); // Debug: 查看错误信息
        });
}


// 页面加载完成时，调用loadPosts函数
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded and parsed'); // Debug: 确认DOM加载完成
    loadPosts();
});
// 为每个帖子创建HTML元素，并添加到页面中
