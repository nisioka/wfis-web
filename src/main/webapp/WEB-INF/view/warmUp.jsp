<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ウォーミングアップ画面</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/weather.css" rel="stylesheet">
<script src="js/jquery-2.2.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>

<body>
<jsp:include page="header.jsp" />

<div class="container">
  <div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
      <h1>画面に何か値を出してみよう！！</h1>
      <h3>自己紹介</h3>
      <table class="table table-bordered table-hover">
        <thead>
          <tr class="info">
            <th>質問</th>
            <th>答え</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>名前</td>
            <td><c:out value="${name}" /></td>
          </tr>
          <tr>
            <td>年齢</td>
            <td><c:out value="${age}" /></td>
          </tr>
          <tr>
            <td>趣味</td>
            <td><c:out value="${hobby}" /></td>
          </tr>
          <tr>
            <td>特技</td>
            <td><c:out value="${skill}" /></td>
          </tr>
        </tbody>
       </table>
    </div>
    <div class="col-lg-2"></div>
   </div>
  </div>
</body>
</html>