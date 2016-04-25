<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>天気検索画面</title>
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

     <h3>
      天気検索
      <img src="../img/weather2.jpg" class="width-7">
     </h3>
     <form action="/weatherSearchHard/search" method="POST">
       <table class="table table-striped table-bordered table-hover">
        <thead>
          <tr class="info">
            <th>検索項目</th>
            <th>検索内容</th>
          </tr>
        </thead>
        <tbody>
            <tr>
              <td style="vertical-align: middle;">日付</td>
              <td class="text-align-center">
                <input type="text" name="weatherDateFrom" class="form-control calendar pull-left width-45" value="${form.weatherDateFrom}">
                <span>～</span>
                <input type="text" name="weatherDateTo" class="form-control calendar pull-right width-45" value="${form.weatherDateTo}">
              </td>
            </tr>
            <tr>
              <td style="vertical-align: middle;">場所</td>
              <td>
                <select id="selectPlace" name="place" class="form-control" data-place="${form.place}">
                  <option value=""></option>
                  <option value="東京">東京</option>
                  <option value="群馬">群馬</option>
                  <option value="栃木">栃木</option>
                  <option value="茨城">茨城</option>
                  <option value="埼玉">埼玉</option>
                  <option value="千葉">千葉</option>
                  <option value="神奈川">神奈川</option>
                </select>
              </td>
            </tr>
            <tr>
              <td style="vertical-align: middle;">天気</td>
              <td id="checkboxes" data-weather="${form.weather}">
                <label class="checkbox-inline"><input class="checkboxWeather" type="checkbox" name="weather" value="晴れ" >晴れ</label>
                <label class="checkbox-inline"><input class="checkboxWeather" type="checkbox" name="weather" value="曇り" >曇り</label>
                <label class="checkbox-inline"><input class="checkboxWeather" type="checkbox" name="weather" value="雨" >雨</label>
                <label class="checkbox-inline"><input class="checkboxWeather" type="checkbox" name="weather" value="雪" >雪</label>
              </td>
            </tr>
            <tr>
              <td style="vertical-align: middle;">最高気温</td>
              <td class="text-align-center">
                <input type="text" name="maxTemperatureFrom" class="form-control pull-left width-45"  value="${form.maxTemperatureFrom}">
                ～
                <input type="text" name="maxTemperatureTo" class="form-control pull-right width-45" value="${form.maxTemperatureTo}">
              </td>
            </tr>
            <tr>
              <td style="vertical-align: middle;">最低気温</td>
              <td class="text-align-center">
                <input type="text" name="minTemperatureFrom" class="form-control pull-left width-45" value="${form.minTemperatureFrom}">
                ～
                <input type="text" name="minTemperatureTo" class="form-control pull-right width-45" value="${form.minTemperatureTo}">
              </td>
            </tr>
        </tbody>
       </table>
       <div class="text-align-center">
         <button type="submit" class="btn btn-info" ><i class="fa fa-search"></i>  検索</button>
       </div>
     </form>

     <c:choose>
       <c:when test="${!empty weatherList}">
         <h3>
          検索結果：
          <span class="dark-blue"><c:out value="${searchCount}" /></span>件
         </h3>
         <table class="table table-striped table-bordered table-hover">
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
            <c:forEach var="weather" items="${weatherList}">
              <tr>
                <td style="vertical-align: middle;"><c:out value="${weather.weatherDate}" /></td>
                <td style="vertical-align: middle;"><c:out value="${weather.place}" /></td>
                <td>
                  <c:choose>
                    <c:when test="${weather.weather eq '晴れ'}">
                      <img src="../img/Sunny.png"  class="width-25">
                    </c:when>
                    <c:when test="${weather.weather eq '曇り'}">
                      <img src="../img/Cloudy.png"  class="width-25">
                    </c:when>
                    <c:when test="${weather.weather eq '雨'}">
                      <img src="../img/Rainy.png"  class="width-25">
                    </c:when>
                    <c:when test="${weather.weather eq '雪'}">
                      <img src="../img/Snow.png"  class="snow">
                    </c:when>
                    <c:otherwise>
                      <c:out value="${weather.weather}" />
                    </c:otherwise>
                  </c:choose>
                </td>
                <c:choose>
                  <c:when test="${weather.maxTemperature >= 20}">
                    <td class="red" style="vertical-align: middle;">
                      <c:out value="${weather.maxTemperature}" /> ℃
                    </td>
                  </c:when>
                  <c:otherwise>
                    <td style="vertical-align: middle;">
                      <c:out value="${weather.maxTemperature}" /> ℃
                    </td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${weather.minTemperature < 0}">
                    <td class="right-blue" style="vertical-align: middle;">
                      <c:out value="${weather.minTemperature}" /> ℃
                    </td>
                  </c:when>
                  <c:otherwise>
                    <td style="vertical-align: middle;">
                      <c:out value="${weather.minTemperature}" /> ℃
                    </td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </c:forEach>
          </tbody>
         </table>
       </c:when>
       <c:when test="${noResult}">
       <div class="no-result">
         <i class="fa fa-search fa-5x"></i>
         <div class="margin-top-20">
           検索結果がありません。
         </div>
       </div>
       </c:when>
     </c:choose>
    </div>
    <div class="col-lg-2"></div>
  </div>

 </div>
</body>
<script>
$(function() {
    $('.calendar').datepicker();

    // 場所のプルダウンの値を記憶しておく
    var place = $('#selectPlace').data('place');
    $('#selectPlace').val(place);

    // 天気のチェックボックスの値を記憶しておく
    var weatherArray = $('#checkboxes').data('weather').split(',');
    if(weatherArray){
      $('.checkboxWeather').val(weatherArray);
    }
});
</script>
</html>