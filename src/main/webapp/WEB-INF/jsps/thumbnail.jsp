<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件缩略图-结果</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css" type="text/css" />
</head>
<body>
<div align="center">

<!--
<h1>上传图片</h1>
<form method="post" action="/file/thumbnail" enctype="multipart/form-data">
<input type="file" name="image"/>
<input type="submit"/>
</form>
-->
    <h4>图片信息</h4>
    <hr/>
    <table width="100%">
        <tr>
            <td with="50%" align="center"><img src="${pageContext.request.contextPath}${imageUrl}" width="500"></td>
            <td with="50%" align="center"><img src="${pageContext.request.contextPath}${thumImageUrl}" ></td>
        </tr>

    </table>
    <hr/>
        <a href="${pageContext.request.contextPath}">返回</a>
    <hr/>
</div>
</body>
</html>