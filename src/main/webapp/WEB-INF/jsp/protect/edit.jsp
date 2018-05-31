<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 解决 JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS --%>
<%@ page isErrorPage="true" %>

<body>


<%-- 添加一个自定义tags, 将long类型的时间转为固定格式输出 --%>
<%@ taglib uri="/tags" prefix="date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 显示错误信息 有就提示  -->
<c:if test="${allErrors!=null}">
    <c:forEach items="${allErrors}" var="error">
        <font color="red">${error.defaultMessage}</font><br/>
    </c:forEach>
</c:if>
<%-- 获取id 取更新用户信息 --%>
<form action="${pageContext.request.contextPath }/admin/student/${studentCustom.id}"
      method="post">
    <%-- REST POST更新动作 --%>
    <input type="hidden" name="_method" value="PUT"/>
    <fieldset>
        <legend>编辑用户</legend>
        <table width="100%"
               style="table-layout:fixed;word-break:break-all;background:#f2f2f2">
            <tr id="name">
                <td>id</td>
                <td>用户名称</td>
                <td>邮箱</td>
                <td>手机号码</td>
                <td>QQ</td>
                <td>修真类型</td>
                <td>入学时间</td>
                <td>毕业院校</td>
                <td>线上id</td>
                <td>日报连接</td>
                <td>立愿</td>
                <td>优秀学员</td>
                <td>辅导师兄</td>
                <td>是否工作</td>
                <td>头像地址</td>
                <td>个人头衔</td>
                <td>操作</td>
            </tr>

            <tr>
                <td>${studentCustom.id}</td>
                <td><input name="stuName" value="${studentCustom.stuName}"></td>
                <td><input name="stuMail" value="${studentCustom.stuMail}">
                </td>
                <td><input name="stuTelephone" type="number" value="${studentCustom.stuTelephone}">
                </td>
                <td><input name="stuQq" type="number" value="${studentCustom.stuQq}">
                </td>
                <td><input name="stuProfession" value="${studentCustom.stuProfession}">
                </td>
                <td><input name="join_date"
                           value="<date:date value ="${studentCustom.join_date}"/>"
                </td>
                <td><input name="stuSchool" value="${studentCustom.stuSchool}"></td>
                <td><input name="online_id" value="${studentCustom.online_id}">
                </td>
                <td><input name="daily_url" value="${studentCustom.daily_url}">
                </td>
                <td><input name="declaration" value="${studentCustom.declaration}">
                </td>
                <td><input name="isSuper" value="${studentCustom.isSuper}">
                </td>
                <td><input name="counselor" value="${studentCustom.counselor}">
                </td>
                <td><input name="isWork" value="${studentCustom.isWork}">
                </td>
                <td><input name="headurl" value="${studentCustom.headurl}">
                </td>
                <td><input name="stuTitle" value="${studentCustom.stuTitle}">
                </td>
                <td><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
