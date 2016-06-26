<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>天気統計画面</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link href="/css/jquery-ui.min.css" rel="stylesheet">
<link href="/css/weather.css" rel="stylesheet">
<script src="/js/jquery-2.2.2.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/datepicker-ja.js"></script>
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
      天気統計
      <img src="/img/weather.jpg" class="width-7">
     </h3>
     <form action="/weatherStatistics/analysis" method="POST">
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
                <input id="mmdd" type="text" name="weatherDate" class="form-control calendar" value="${form.weatherDate}">
              </td>
            </tr>
            <tr>
              <td style="vertical-align: middle;">場所</td>
              <td>
                <select id="selectPlace" name="place" class="form-control">
                  <option value=""></option>
                  <option value="東京" <c:out value="${form.place=='東京' ? 'selected' : ''}"/>>東京</option>
                  <option value="群馬" <c:out value="${form.place=='群馬' ? 'selected' : ''}"/>>群馬</option>
                  <option value="栃木" <c:out value="${form.place=='栃木' ? 'selected' : ''}"/>>栃木</option>
                  <option value="茨城" <c:out value="${form.place=='茨城' ? 'selected' : ''}"/>>茨城</option>
                  <option value="埼玉" <c:out value="${form.place=='埼玉' ? 'selected' : ''}"/>>埼玉</option>
                  <option value="千葉" <c:out value="${form.place=='千葉' ? 'selected' : ''}"/>>千葉</option>
                  <option value="神奈川" <c:out value="${form.place=='神奈川' ? 'selected' : ''}"/>>神奈川</option>
                </select>
              </td>
            </tr>
        </tbody>
       </table>
       <div class="text-align-center">
         <button type="submit" class="btn btn-info" ><i class="fa fa-bar-chart" ></i>  分析</button>
       </div>
     </form>

     <c:choose>
       <c:when test="${!empty weatherStatisticsDto}">
         <h3>
          過去の統計結果
         </h3>
         <table class="table table-bordered table-hover">
          <thead>
            <tr class="info">
              <th width="20%">日付</th>
              <th width="20%">場所</th>
              <th width="20%">天気</th>
              <th width="20%">平均最高気温</th>
              <th width="20%">平均最低気温</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style="vertical-align: middle;"><c:out value="${weatherStatisticsDto.weatherDate}" /></td>
              <td style="vertical-align: middle;"><c:out value="${weatherStatisticsDto.place}" /></td>
              <td style="vertical-align: middle;">
                <c:if test="${weatherStatisticsDto.sunnyPercent ne 0}">
                  <div><img src="/img/Sunny.png"  class="width-25"> (<c:out value="${weatherStatisticsDto.sunnyPercent}" /> %)</div>
                </c:if>
                <c:if test="${weatherStatisticsDto.cloudyPercent ne 0}">
                  <div><img src="/img/Cloudy.png"  class="width-25"> (<c:out value="${weatherStatisticsDto.cloudyPercent}" /> %)</div>
                </c:if>
                <c:if test="${weatherStatisticsDto.rainyPercent ne 0}">
                  <div><img src="/img/Rainy.png"  class="width-25"> (<c:out value="${weatherStatisticsDto.rainyPercent}" /> %)</div>
                </c:if>
                <c:if test="${weatherStatisticsDto.snowPercent ne 0}">
                  <div><img src="/img/Snow.png"  class="snow"> (<c:out value="${weatherStatisticsDto.snowPercent}" /> %)</div>
                </c:if>
              </td>
              <td style="vertical-align: middle;"><c:out value="${weatherStatisticsDto.maxTemperatureAve}" /> ℃</td>
              <td style="vertical-align: middle;"><c:out value="${weatherStatisticsDto.minTemperatureAve}" /> ℃</td>
            </tr>
          </tbody>
         </table>
       </c:when>
     </c:choose>
    </div>
    <div class="col-lg-2"></div>
  </div>

 </div>
</body>

<script>
$(function() {
    // datepicker 年を表示しないため
    $('.calendar').datepicker({
      dateFormat : 'mm/dd',
      yearSuffix : '',
      dayNamesShort: [ "","","","","","","" ],
      dayNamesMin: [ "","","","","","","" ]
    });
    $('#mmdd').on('click', function(){
      $('.ui-datepicker-year').text('');
    })
    $('.ui-corner-all').on('click', function(){
      $('.ui-datepicker-year').text('');
    })
});
</script>
</html>