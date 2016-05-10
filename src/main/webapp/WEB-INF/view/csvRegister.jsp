<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CSV登録画面</title>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/font-awesome.min.css" rel="stylesheet">
<link href="../css/jquery-ui.min.css" rel="stylesheet">
<link href="../css/weather.css" rel="stylesheet">
<script src="../js/jquery-2.2.2.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery-ui.min.js"></script>
<script src="../js/datepicker-ja.js"></script>
</head>

<body>
<jsp:include page="header.jsp" />

<div class="container">
  <div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
      <c:if test="${!empty errorList}">
         <ul class="error">
           <c:forEach var="error" items="${errorList}">
             <li><c:out value="${error}" /></li>
           </c:forEach>
         </ul>
      </c:if>

      <h3>CSVデータ登録</h3>
      <form action="/csvRegister/insert" method="POST">
        <div class="input-group">
          <input type="text" name="filePath" class="form-control" value="${filePath}" placeholder="ファイルパスを入力してください">
          <span class="input-group-btn"><button type="submit" class="btn btn-info" >登録</button></span>
        </div>
      </form>
    </div>
    <div class="col-lg-2"></div>
  </div>
 </div>

</body>
</html>