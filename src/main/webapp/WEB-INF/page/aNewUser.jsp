<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-3.1.1.min.js"></script>
</head>
<body>

    姓名<input id="name" name="name" value="${user.name}">
    <br>
    登录名<input id="loginName" name="loginName" value="${user.loginName}">
    <br>
    密码<input id="password" name="password" value="${user.password}">
    <br>
    <input id="userId" name="userId" value="${user.userId}" hidden><br>
    <button type="submit" id="query" name="query" onclick="addUser()">保存</button>

</body>
</html>
<script type="text/javascript">
    function addUser() {
        $.ajax({
            type: 'POST',
            url: '/user/add',
            dataType: 'json',
            data: {userId:$('#userId').val(),name:$('#name').val(), loginName:$('#loginName').val(), password:$('#password').val()},
            success: function(data){
                if(200 == data.status){
                    alert('保存成功');
                    window.location.href = '/user/list/1?userId=&loginName=';
                }else{
                    alert('保存失败');
                }
            },
            error:function(XMLResponse){
                alert(XMLResponse.status);
            }
        })
    }
</script>



