<%--
  Created by IntelliJ IDEA.
  User: Fxiao
  Date: 2019/6/4
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>基础管理</title>
    <link rel="stylesheet" href="../static/bootstrap.css">
    <script type="text/javascript" src="../static/jquery.min.js"></script>
    <script type="text/javascript" src="../static/bootstrap.js"></script>
    <script type="text/javascript" >
        $(function(){
            //获取数据
            getModelList();
            //绑定按钮
            bindBtns();
            //绑定事件
            bindEvent();
        })
        //绑定按钮
        function bindBtns(){
            $("#addTempBtn").click(function(){
                $("#addModel").modal("show");
            });
            $("#deleTempBtn").click(function(){
                var deleLi=$("#modelList").find("li.active");
                if(deleLi.length==1){
                    var _id=deleLi.prop("id");
                    var _data={"actionName":"delete"};
                    _data.id=_id;
                    $.ajax({
                        url:"/Windchill/servlet/Navigation/template",
                        data:_data,
                        type:"get",
                        success:function (result) {
                            if(!result.success){
                                alert(result.message);
                            }
                            getModelList();
                        },
                        error:function (a,b,c,d) {
                            alert("发生错误，删除失败");
                        }
                    })
                }else{
                    alert("请选中一条模板");
                }
            });
            $("#modifyTempBtn").click(function () {
                var deleLi=$("#modelList").find("li.active");
                if(deleLi.length==1){
                    var _id=deleLi.prop("id");
                    $("#modelForm").find("input[name=id]").val(_id);
                    $("#modelForm").find("input[name=name]").val(deleLi.text());
                    $("#addModel").modal("show");

                }else{
                    alert("请选中一条模板");
                }
            });
            $("#addProcedureBtn").click(function () {
                var procedureName=$(this).next().val();
                alert("正在开发中");
                /*$.ajax({
                    url:"",
                    type:"post",
                    data:{"name",procedureName}
                })*/
            });
        }
        //获取全部模板
        function getModelList(){
            $("#modelList").html("");
            $.ajax({
                url:"/Windchill/servlet/Navigation/template",
                data:{"actionName":"get"},
                type:"get",
                dataType:"json",
                success:function(result){
                    if(result.success){
                        $.each(result.data,function(i,n){
                            var listItem=$("<li></li>").addClass("list-group-item").prop("id",n.id).text(n.name);
                            $("#modelList").append(listItem);
                        });
                    }else{
                        addLog("获取模板请求成功，但是未请求到数据。"+result.message);
                    }

                },
                error:function (a,b,c,d) {
                    alert("获取数据失败:"+b);
                }
            });
        }
        //表单提交
        function formSubmit(){
            $.ajax({
                url:"/Windchill/servlet/Navigation/template?actionName=post",
                data:$("#modelForm").serialize(),
                type:"post",
                success:function (result) {
                    if(!result.success){
                        addLog(result.message);
                    }
                    $("#addModel").modal("hide");
                    getModelList();
                },
                error:function (a,b,c,d) {
                    alert("保存失败，"+b);
                    $("#addModel").modal("hide");
                }
            })
        }
        //为模板列表绑定事件
        function bindEvent() {
            $("#modelList").on("click","li",function () {
                $(this).prevAll().add($(this).nextAll()).removeClass("active");
                $(this).addClass("active");
            });
            $("#addModel").on("hidden.bs.modal",function () {
                $("#modelForm").get(0).reset();
                $("#modelForm").find("input[type=hidden]").val("0");
            });
        }
        function addLog(message){
            var _li=$("<li></li>").text(message);
            $("#log").children("ul").append(_li);
        }
    </script>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">

                <div class="well">
                    <div class="btn-group" >
                        <button class="btn btn-info" type="button" id="addTempBtn" >新增</button>
                        <button class="btn btn-info" type="button" id="modifyTempBtn">修改</button>
                        <button class="btn btn-info" type="button" id="deleTempBtn" >删除</button>
                    </div>

                    <ul id="modelList" class="list-group" style="margin-top:15px;">

                    </ul>
                </div>
            </div>
            <div class="col-md-10">
                <div class="well">
                    <h3>工序特性指标</h3>
                    <div class="btn-group" >
                        <button class="btn btn-info" type="button" id="addCharBtn" >新增</button>
                        <button class="btn btn-info" type="button" id="modifyCharBtn">修改</button>
                        <button class="btn btn-info" type="button" id="deleCharBtn" >删除</button>
                        <button class="btn btn-info" type="button" id="importCharBtn" >导入工艺化结构工序</button>
                    </div>
                    <table class="table">
                        <tr><th>序号</th><th>工序名称</th><th>工序检验特性</th><th>检验特性数量</th><th>严酷度加权系数</th></tr>
                        <tr>
                            <td>1</td>
                            <td>工序123</td>
                            <td>外购件</td>
                            <td>10</td>
                            <td>1</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>工序123</td>
                            <td>外购件</td>
                            <td><input class="form-control" type="number" ></td>
                            <td><input class="form-control" type="number" ></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="modal fade" id="addModel" >
            <div class="modal-dialog modal-lg" >
                <div class="modal-content ">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            增加模板
                        </h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" id="modelForm">
                            <div class="form-group">
                                <label  class="col-md-3 control-label">模板名</label>
                                <div class="col-md-7">
                                    <input class="form-control" type="text" placeholder="模板名" name="name" >
                                    <input type="hidden" name="id" value="0">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="gongxu" class="col-md-3 control-label">选择工序</label>
                                <div class="col-md-7 form-inline">
                                    <select id="gongxu" class="form-control">
                                        <option>请选择</option>
                                        <option>工序1</option>
                                        <option>工序2</option>
                                    </select>
                                    <button type="button" class="btn btn-info">选择</button>
                                    <span class="pull-right">
                                        <button type="button" id="addProcedureBtn" class=" btn btn-info">添加工序</button>
                                        <input type="text" class="pull-right form-control" placeholder="请输入工序名称">
                                    </span>
                                </div>
                            </div>
                            <div class="form-group ">
                                <div class="col-md-6 col-md-offset-3">
                                    <input type="text" disabled="disabled" class="form-control " value="模板1">
                                    <input type="text" disabled="disabled" class="form-control" value="模板2">
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button onclick="formSubmit()" type="button" class="btn btn-primary">
                            确认
                        </button>
                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!--.modal-->

    </div>
    <div id="log"><ul></ul></div>
</body>
</html>
