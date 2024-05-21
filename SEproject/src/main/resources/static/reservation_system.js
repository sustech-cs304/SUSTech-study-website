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

        document.getElementById('image-display').style.display = 'none';
         // 确保默认状态为隐藏

    });
}

function createConfirmationMessage(data) {
    // 将预约数据转换成消息字符串
    return `预约日期: ${data.date}\n预约时间段: ${data.time_slot}\n预约地点: ${data.location}\n预约人数: ${data.participants}`;
}

function submitReservation(reservationData) {
    const firstNameInput = document.querySelector('input[name="name1"]');
    const firstStudentIdInput = document.querySelector('input[name="studentId1"]');
    const roomType = document.getElementById('room_type').value; // 获取隐藏字段的值

    fetch('/reservations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            date: reservationData.date,
            time_slot: reservationData.time_slot,
            location: reservationData.location,
            participants: Number(reservationData.participants), // 确保是整数
            firstName: firstNameInput ? firstNameInput.value : '', // 添加第一位预约者的姓名
            firstStudentId: firstStudentIdInput ? firstStudentIdInput.value : '', // 添加第一位预约者的学号
            roomType: roomType // 包括房间类型
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
function updateLocationOptions(roomType) {
    var locationSelect = document.getElementById('location');
    locationSelect.innerHTML = ''; // 清空现有选项

    if (roomType.includes('讨论室')) {
        var locations = ['三教', '一教', '琳恩', '一丹', '涵泳'];
    } else if (roomType.includes('会议室')) {
        var locations = ['商学院', '理学院', '工学院'];
    } else {
        return; // 如果不是讨论室或会议室，不更新地点选项
    }

    // 添加提示选项
    locationSelect.add(new Option('请选择地点', '', false, true));

    // 根据房间类型添加新的地点选项
    locations.forEach(function(location) {
        var option = new Option(location, location);
        locationSelect.add(option);
    });

    locationSelect.disabled = false; // 确保地点选择可用
}
function showContent(subtype) {
    // 更新内容区域的文本
    var contentArea = document.getElementById('content-display');
    contentArea.textContent = "您正在预约：" + subtype; // 更丰富的描述

    var imageUrl = "";
    var imageElement = document.getElementById('category-image');
    var imageDisplay = document.getElementById('image-display');
    imageElement.style.display = 'none'; // 确保默认状态为隐藏
    imageDisplay.style.display = 'none';
    // 根据不同的子类别设置不同的图片路径
    switch(subtype) {
        case '1-2人讨论室':
            imageUrl = '/img.png';
            break;
        case '3-7人讨论室':
            imageUrl = '/img1.jpg';
            break;
        case '7-人讨论室':
            imageUrl = '/img2.jpg';
            break;
        case '小型会议室':
            imageUrl = '/img3.jpg';
            break;
        case '中型会议室':
            imageUrl = '/img4.jpg';
            break;
        case '大型会议室':
            imageUrl = '/img5.png';
            break;
        default:
            imageUrl = ''; // 默认不显示图片
            imageElement.style.display = 'none';
            imageDisplay.style.display = 'none';
            return;
    }

    if (imageUrl) {
        imageElement.src = imageUrl;
        imageElement.style.display = 'block';
        imageDisplay.style.display = 'flex';
    } else {
        imageElement.src = '';  // 清除之前的图片路径
        imageElement.style.display = 'none';  // 确保图片不显示
        imageDisplay.style.display = 'none';
    }

    document.getElementById('room_type').value = subtype;
    // 显示预约细节表单
    var reservationDetails = document.getElementById('reservation-details');
    reservationDetails.style.display = 'block';

    // 根据选择的房间类型更新地点选项
    updateLocationOptions(subtype);
}

function showReservedSpaces() {
    // 隐藏所有不需要的元素
    document.getElementById('content-display').style.display = 'none';
    document.getElementById('reservation-details').style.display = 'none';
    document.getElementById('image-display').style.display = 'none';

    // 清空并显示表格区域
    const tableArea = document.getElementById('content-display'); // 使用相同的内容显示区域
    tableArea.innerHTML = ''; // 清空现有内容
    tableArea.style.display = 'block'; // 确保区域可见

    fetch('/reservations/all')
        .then(response => response.json())
        .then(data => {
            const table = document.createElement('table');
            table.className = 'reservation-table'; // 确保使用了样式类
            table.innerHTML = `
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>日期</th>
                        <th>时间段</th>
                        <th>地点</th>
                        <th>预约者姓名</th>
                        <th>学号</th>
                        <th>人数</th>
                        <th>房间类型</th>
                    </tr>
                </thead>
                <tbody>
                    ${data.map(item => `
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.date}</td>
                            <td>${item.time_slot}</td>
                            <td>${item.location}</td>
                            <td>${item.firstName}</td>
                            <td>${item.firstStudentId}</td>
                            <td>${item.participants}</td>
                            <td>${item.roomType}</td>
                        </tr>
                    `).join('')}
                </tbody>
            `;
            tableArea.appendChild(table);
        })
        .catch(error => {
            console.error('Error:', error);
            tableArea.textContent = '无法加载数据。';
        });
}


