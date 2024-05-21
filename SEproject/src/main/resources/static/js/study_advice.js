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


document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM fully loaded and parsed'); // Debug: 确认DOM加载完成
    loadPosts();

    document.body.addEventListener('click', function(event) {
        console.log("点击了！！！！！！！！")
        if (event.target.id === 'search-btn') {
            console.log("点击了搜索按钮");
            const searchValue = document.getElementById('search-post').value;
            const filterCategory = document.getElementById('filter-tags').value;
            const filterTime = document.getElementById('filter-time').value;

            loadFilteredPosts(searchValue, filterCategory, filterTime);
        }
    });
});



function loadFilteredPosts(title, category, sortBy) {
    // 构建查询参数
    const params = new URLSearchParams();
    if (title) params.append('title', title);
    if (category !== 'all') params.append('category', category);
    params.append('sortBy', sortBy);

    // 发起请求到后端搜索API
    fetch(`/posts/search?${params.toString()}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(posts => {
            const postsContainer = document.getElementById('post-container');
            postsContainer.innerHTML = ''; // 清空当前帖子列表

            // 添加帖子到页面
            posts.forEach(post => {
                const postElement = document.createElement('div');
                postElement.className = 'post';
                postElement.innerHTML = `
                  <div class="title">${post.title}</div>
                  <div class="category">${post.category}</div>
                  <div class="author">${post.author}</div>
                  <div class="date">${new Date(post.publishTime).toLocaleString()}</div>
                `;
                // 绑定点击事件
                postElement.onclick = function() {
                    window.location.href = `/posts/${post.id}?id=${post.id}`;
                };
                postsContainer.appendChild(postElement);
            });
        })
        .catch(error => {
            console.error('Error loading filtered posts:', error);
            document.getElementById('post-container').textContent = '加载帖子失败: ' + error;
        });
}
