

// 当文档加载完成后绑定事件
document.addEventListener('DOMContentLoaded', function () {
    // 绑定下拉菜单显示/隐藏事件
    bindDropdownToggle();

    // 绑定表单提交事件
    bindFormSubmit();
});

function bindDropdownToggle() {
    var dropdownMenu = document.querySelector('.dropdown-menu');
    document.querySelector('.dropdown-toggle').addEventListener('click', function() {
        dropdownMenu.classList.toggle('show');
    });

    document.querySelector('.nav-user').addEventListener('mouseenter', function() {
        dropdownMenu.style.display = 'flex';
    });

    document.querySelector('.nav-user').addEventListener('mouseleave', function() {
        dropdownMenu.style.display = 'none';
    });
}

function bindFormSubmit() {
    var form = document.getElementById('reservation-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(form);
        let reservationData = Object.fromEntries(formData.entries());
        // reservationData.time_slot = form['time-slot'].value
        // 打印出表单数据，用于调试
        console.log('提交的预约数据:', reservationData);

        // 将预约数据转换为字符串，显示在弹窗中
        const confirmationMessage = createConfirmationMessage(reservationData);
        alert(confirmationMessage);

        // 在此处添加其他与提交相关的代码...
        submitReservation(reservationData);
        // 重置子分类显示和侧边栏状态
        resetSubcategories();

        // 重置表单
        event.target.reset();

        // 重置内容显示区
        document.getElementById('content-display').textContent = '选择一个类别以显示内容';

        // 清除动态生成的姓名和学号输入字段
        document.getElementById('participant-details').innerHTML = '';
    });
}

function createConfirmationMessage(data) {
    // 将预约数据转换成消息字符串
    return `预约日期: ${data.date}\n预约时间段: ${data.time_slot}\n预约地点: ${data.location}\n预约人数: ${data.participants}`;
}

function submitReservation(reservationData) {
    fetch('/reservations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            date: reservationData.date,
            time_slot: reservationData.time_slot,
            location: reservationData.location,
            participants: Number(reservationData.participants) // 确保是整数
        }),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}


function toggleSubcategories(subcatId) {
    var subcategories = document.getElementById(subcatId);
    var isDisplayed = subcategories.style.display === 'block';
    // 先隐藏所有子类型
    var allSubcats = document.getElementsByClassName('subcategories');
    for (var i = 0; i < allSubcats.length; i++) {
        allSubcats[i].style.display = 'none';
    }
    // 然后显示或隐藏被点击的类别的子类型
    subcategories.style.display = isDisplayed ? 'none' : 'block';
}


function resetSubcategories() {
    // 获取所有子分类容器
    var subcats = document.querySelectorAll('.subcategories');
    for (var i = 0; i < subcats.length; i++) {
        // 隐藏所有子分类
        subcats[i].style.display = 'none';
    }
    // 移除所有侧边栏激活状态
    var links = document.querySelectorAll('.sidebar .tab-link');
    for (var i = 0; i < links.length; i++) {
        links[i].classList.remove('active');
    }
}

function addParticipantDetails(numberOfParticipants) {
    const participantDetailsContainer = document.getElementById('participant-details');
    participantDetailsContainer.innerHTML = ''; // Clear previous inputs

    // 如果输入无效或人数为0，直接返回
    if (!numberOfParticipants || numberOfParticipants <= 0) {
        return;
    }

    for (let i = 1; i <= numberOfParticipants; i++) {
        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.name = 'name' + i;
        nameInput.placeholder = '姓名 ' + i;
        nameInput.required = true;

        const studentIdInput = document.createElement('input');
        studentIdInput.type = 'text';
        studentIdInput.name = 'studentId' + i;
        studentIdInput.placeholder = '学号 ' + i;
        studentIdInput.required = true;

        participantDetailsContainer.appendChild(nameInput);
        participantDetailsContainer.appendChild(studentIdInput);
    }
}

function showContent(subtype) {
    // 更新内容区域的文本
    var contentArea = document.getElementById('content-display');
    contentArea.textContent = "您正在预约：" + subtype; // 更丰富的描述

    // 显示预约细节表单
    var reservationDetails = document.getElementById('reservation-details');
    reservationDetails.style.display = 'block';

}


