<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<strong>用户管理系统</strong>
<br>
<button id="add" name="add" value="" onclick="addUserPage()">添加新用户</button>
<br>
<table>
    <tr>
        <td>用户ID</td>
        <td>登录名</td>
        <td>姓名</td>
    </tr>
    <!-- todo 未考虑分页 -->
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.loginName}</td>
            <td>${user.name}</td>
            <td><button onclick="delUser(${user.userId},'${user.name}')">删除</button></td>
            <td><button onclick="modifyUser(${user.userId})">修改</button></td>
        </tr>
    </c:forEach>
</table>
<select id="orderType" name="orderType">
    <option value="1" <c:choose><c:when test="${orderType == 2 || orderType == 3 || orderType == 4}"></c:when><c:otherwise>selected</c:otherwise></c:choose>>用户ID正序</option>
    <option value="2" <c:if test="${orderType == 2}">selected</c:if>>用户ID逆序</option>
    <option value="3" <c:if test="${orderType == 3}">selected</c:if>>登录名正序</option>
    <option value="4" <c:if test="${orderType == 4}">selected</c:if>>登录名逆序</option>
</select>
用户ID<input id="userId" name="userId" value="${userId}">
登录名<input id="loginName" name="loginName" value="${loginName}">
<button id="query" name="query" value="" onclick="queryUser()">查找</button>
</body></html>

<script type="text/javascript">

    function queryUser() {

        var reg = new RegExp("^[0-9]*$");
        var userId = $("#userId").val();
        if(!reg.test(userId)){
            alert("请输入数字!");
        }else{
            var obj = document.getElementById("orderType");
            var index = obj.selectedIndex; // 选中索引
            var value = obj.options[index].value; // 选中值
            var loginName = document.getElementById("loginName").value;
            var url = "/user/list/" + value + "?userId="+userId+"&loginName="+loginName;
            //todo 异步请求，重新生成页面。
            window.location.href = url;
        }
    }

    function modifyUser(userId) {
        window.location.href = '/user/aNewUser.html?userId=' + userId;
    }

    function addUserPage() {
        window.location.href = '/user/aNewUser.html?userId=';
    }

    function delUser(userId, name) {
        var r = confirm("确认删除" + name + "?");
        if (r == true){
            $.ajax({
                type: 'POST',
                url: '/user/delete.json',
                dataType: 'json',
                data: {userId:userId},
                success: function(data){
                    if(200 == data.status){
                        alert('删除成功');
                        var content = '<tr><td>用户ID</td><td>登录名</td><td>姓名</td></tr>';
                        $.each(data.data, function(index, value){
                            content = content + '<tr><td>' + value.userId + '</td><td>' + value.loginName + '</td><td>' + value.name + '</td></tr>';
                        });
                        $('table').html(content);
                    }else{
                        alert('删除失败');
                    }
                },
                error:function(XMLResponse){
                    alert(XMLResponse.status);
                }
            })
        }
    }
    

</script>