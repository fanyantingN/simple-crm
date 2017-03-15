<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html><body>
<strong>用户管理系统</strong>
<br>
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
        </tr>
    </c:forEach>
</table>
<select id="orderType" name="orderType">
    <option value="1" <c:choose><c:when test="${orderType == 2 || orderType == 3 || orderType == 4}"></c:when><c:otherwise>selected</c:otherwise></c:choose>>用户ID正序</option>
    <option value="2" <c:if test="${orderType == 2}">selected</c:if>>用户ID逆序</option>
    <option value="3" <c:if test="${orderType == 3}">selected</c:if>>登录名正序</option>
    <option value="4" <c:if test="${orderType == 4}">selected</c:if>>登录名逆序</option>
</select>
用户名<input id="userId" name="userId" value="${userId}">
登录名<input id="loginName" name="loginName" value="${loginName}">
<button id="query" name="query" value="" onclick="queryUser()">查找</button>
</body></html>

<script>
    var Ajax={
        get: function (url,fn){
            var obj=new XMLHttpRequest();  // XMLHttpRequest对象用于在后台与服务器交换数据
            obj.open('GET',url,true);
            obj.onreadystatechange=function(){
                if (obj.readyState == 4 && obj.status == 200 || obj.status == 304) { // readyState==4说明请求已完成
                    fn.call(this, obj.responseText);  //从服务器获得数据
                }
            };
            obj.send(null);
        },
        post: function (url, data, fn) {
            var obj = new XMLHttpRequest();
            obj.open("POST", url, true);
            obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // 发送信息至服务器时内容编码类型
            obj.onreadystatechange = function () {
                if (obj.readyState == 4 && (obj.status == 200 || obj.status == 304)) {  // 304未修改
                    fn.call(this, obj.responseText);
                }
            };
            obj.send(data);
        }
    }


    function queryUser() {
        var obj = document.getElementById("orderType");
        var index = obj.selectedIndex; // 选中索引
        var value = obj.options[index].value; // 选中值
        var userId = document.getElementById("userId").value;
        var loginName = document.getElementById("loginName").value;
        var url = "/user/list/" + value + "?userId="+userId+"&loginName="+loginName;
        //todo 异步请求，重新生成页面。
        //var list = Ajax.get(url);
        window.location.href = url;
    }

    

</script>