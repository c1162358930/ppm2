<%--
  Created by IntelliJ IDEA.
  User: Fxiao
  Date: 2019/6/15
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ppm基础管理</title>
    <link rel="stylesheet" type="text/css" href="../static/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="../static/dataTables.bootstrap.min.css" />
    <script type="text/javascript" src="../static/jquery.min.js"></script>
    <script type="text/javascript" src="../static/bootstrap.js"></script>
    <script type="text/javascript" src="../static/bootstrap-treeview.min.js"></script>
    <script type="text/javascript" src="../static/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../static/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/content/content.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="text-center">产品检验内容配置管理</h3>
            <hr />
        </div>
    </div>
    <div class="row">
        <div class=" col-lg-2">
            <h3>产品组成</h3>
            <div class="btn-group">
                <button id="addModelBtn" class="btn btn-info">增加</button>
                <button id="modifyModelBtn" class="btn btn-info">修改</button>
                <button id="deleteModelBtn" class="btn btn-info">删除</button>
            </div>
            <div id="tree">

            </div>
        </div>
        <div class="col-lg-10">
            <h3 class="text-center">检验内容</h3>
            <div class="btn-group operation">
                <button id="addProductBtn" class="btn btn-info">增加</button>
                <button id="modifyProductBtn" class="btn btn-info">修改</button>
                <button id="deleteProductBtn" class="btn btn-info">删除</button>
            </div>

            <table id="myform" class="table">

            </table>
        </div>
    </div>
</div>
<div id="log"></div>

</body>
</html>

