

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
        reservationData.time_slot = form['time-slot'].value
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
        body: JSON.stringify(reservationData),
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

// // 当文档加载完成后绑定事件
// document.addEventListener('DOMContentLoaded', function () {
//     // 给下拉箭头绑定点击事件
//     document.querySelector('.dropdown-toggle').addEventListener('click', function() {
//         // 此处的'.dropdown-menu'应该与你的下拉菜单容器的类名一致
//         var dropdownMenu = document.querySelector('.dropdown-menu');
//         // 切换下拉菜单的显示和隐藏
//         dropdownMenu.classList.toggle('show');
//     });
// });

// document.addEventListener('DOMContentLoaded', function () {
//     document.getElementById('reservation-form').addEventListener('submit', function (event) {
//         event.preventDefault(); // 阻止表单默认提交
//         const form = event.target;
//         const date = form.date.value;
//         const timeSlot = form['time-slot'].value;
//         const location = form.location.value;
//         const participants = form.participants.value;
//
//         let participantDetails = '';
//         for (let i = 1; i <= participants; i++) {
//             participantDetails += `姓名 ${i}: ${form['name' + i].value}, 学号 ${i}: ${form['studentId' + i].value}\n`;
//         }
//
//         // 弹出或处理预约信息
//         const confirmationMessage = `预约母类型: ${document.getElementById('content-display').textContent}\n预约日期: ${date}\n预约时间段: ${timeSlot}\n预约地点: ${location}\n预约人数: ${participants}\n${participantDetails}`;
//         alert(confirmationMessage);
//         // 重置表单和隐藏预约细节部分
//         // 重置表单
//         this.reset();
//         // 重置侧边栏的展开内容
//         resetSubcategories();
//         // 隐藏预约细节
//         document.getElementById('reservation-details').style.display = 'none';
//         // 重置内容显示区
//         document.getElementById('content-display').textContent = '选择一个类别以显示内容';
//         // 清除动态生成的姓名和学号输入字段
//         document.getElementById('participant-details').innerHTML = '';
//     });
// });


// document.getElementById('reservation-form').addEventListener('submit', function(event) {
//     event.preventDefault();
//     const formData = new FormData(event.target);
//     const reservationData = Object.fromEntries(formData.entries());
//     // 打印FormData对象和转换后的对象
//     console.log('FormData:', [...formData]);
//     console.log('Reservation Data:', reservationData);
//     // 在发送之前打印出reservationData对象
//     console.log("提交的预约数据：", reservationData);
//
//     fetch('/reservations', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(reservationData),
//     })
//         .then(response => response.json())
//         .then(data => {
//             console.log('Success:', data);
//             // 处理成功响应
//         })
//         .catch((error) => {
//             console.error('Error:', error);
//             // 处理错误响应
//         });
// });

// document.addEventListener('DOMContentLoaded', function () {
//     // 获取下拉菜单元素
//     var dropdownMenu = document.querySelector('.dropdown-menu');
//
//     // 监听鼠标在.nav-user上的悬停事件
//     document.querySelector('.nav-user').addEventListener('mouseenter', function() {
//         // 显示下拉菜单
//         dropdownMenu.style.display = 'flex';
//     });
//
//     // 监听鼠标离开.nav-user区域的事件
//     document.querySelector('.nav-user').addEventListener('mouseleave', function() {
//         // 隐藏下拉菜单
//         dropdownMenu.style.display = 'none';
//     });
// });
