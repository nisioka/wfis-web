<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CSV登録画面</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/jquery-ui.min.css" rel="stylesheet">
<link href="css/weather.css" rel="stylesheet">
<script src="js/jquery-2.2.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/datepicker-ja.js"></script>
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

      <h3>CSV読み込み</h3>
      <form action="/csvRead" method="POST">
        <div class="input-group">
          <input type="text" name="filePath" class="form-control" value="${filePath}" placeholder="ファイルパスを入力してください">
          <span class="input-group-btn"><button type="submit" class="btn btn-info" >読み込み</button></span>
        </div>
      </form>

      <c:if test="${!empty csvReadList}">
        <h3 class="margin-top-40">登録件数：
          <span class="right-blue"><c:out value="${rowCount}"/></span>件
        </h3>
        <table class="table table-bordered">
          <thead>
            <tr class="info">
              <th width="20%">日付</th>
              <th width="20%">場所</th>
              <th width="20%">天気</th>
              <th width="20%">最高気温</th>
              <th width="20%">最低気温</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style="vertical-align: middle;"><c:out value="${csvReadList[0].WEATHER_DATE}" /></td>
              <td style="vertical-align: middle;"><c:out value="${csvReadList[0].PLACE}" /></td>
              <td style="vertical-align: middle;"><c:out value="${csvReadList[0].WEATHER}" /></td>
              <td style="vertical-align: middle;"><c:out value="${csvReadList[0].MAX_TEMPERATURE}" /></td>
              <td style="vertical-align: middle;"><c:out value="${csvReadList[0].MIN_TEMPERATURE}" /></td>
            </tr>
            <tr>
              <td class="middle text-align-center">：<br>：</td>
              <td class="middle text-align-center">：<br>：</td>
              <td class="middle text-align-center">：<br>：</td>
              <td class="middle text-align-center">：<br>：</td>
              <td class="middle text-align-center">：<br>：</td>
            </tr>
          </tbody>
         </table>

        <div class="text-align-center middle">
          <form action="/csvRegister" method="POST">
            <button type="submit" class="btn btn-info" >登録</button>
            <input type="hidden" name="filePath" class="form-control" value="${filePath}">
            <input type="hidden" name="csvDataList" class="form-control" value="${csvData}">
          </form>
        </div>
       </c:if>
    </div>
    <div class="col-lg-2"></div>
  </div>
 </div>

</body>
</html>