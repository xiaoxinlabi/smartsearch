<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>文件预览</title>

  <script>
    var pdfPath='<%=request.getAttribute("pdfPath")%>';
  </script>

</head>
<body>

<div id="pdf" style="width:100%;height: 800px"></div>

<script src="./assets/js/jquery.min.js"></script>
<script src="./utils/js/pdfobject.js"></script>
<script src="./js/preview.js"></script>

</body>
</html>