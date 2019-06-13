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
    <style type="text/css" rel="stylesheet" >
        button.myClose{
            -webkit-appearance: none;
            padding: 0;
            cursor: pointer;
            background: transparent;
            border: 0;}
    </style>
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
        //获取工序
        function getProcedure(){
            $.ajax({
                url:"/Windchill/servlet/Navigation/procedure",
                data:{"actionName":"get"},
                type:"get",
                dataType:"json",
                success:function (result) {
                    if(!result.success){
                        alert(result.message);
                    }else{
                        $("#gongxu").children("option").remove();
                        $.each(result.data,function (i,n) {
                            var _option=$("<option></option>").text(n.name).prop("value",n.id);
                            $("#gongxu").append(_option);
                        });
                    }
                },
                error:function (a,b,c,d) {
                    alert(c);
                }
            })
        }


        //绑定按钮
        function bindBtns(){
            //"增加模板"按钮
            $("#addTempBtn").click(function(){
                //动态获取所有工序
                getProcedure();
                $("#addModel").modal("show");
            });
            //"选择工序"按钮
            $("#selectProcedureBtn").click(function () {
                var selected=$(this).prev().children("option:selected");
                var _div=$("<div></div>").addClass("form-inline");
                var _input1=$('<input type="text" disabled="disabled" class="form-control " >').val(selected.text());
                var _input2=$('<input type="hidden" name="procedure.id">').val($(this).prev().val());
                var closeBtn='<button id="removeProcedure" type="button" class="myClose">&nbsp;&times;&nbsp;</button>';
                _div.append(_input1).append(_input2).append(closeBtn);
                $("#gongxuContent").append(_div);
            });
            //"删除模板" 按钮
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
                        dataType:"json",
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
            //修改模板按钮
            $("#modifyTempBtn").click(function () {
                var seleLi=$("#modelList").find("li.active");
                if(seleLi.length==1){
                    //带入模板基础信息
                    var _id=seleLi.prop("id");
                    $("#modelForm").find("input[name=id]").val(_id);
                    $("#modelForm").find("input[name=name]").val(seleLi.text());
                    //获取全部工序放到下拉框里面
                    getProcedure();
                    //获取模板下的所有工序
                    $.ajax({
                        url:"/Windchill/servlet/Navigation/procedure",
                        type:"get",
                        dataType:"json",
                        data:{"actionName":"getByTemplate","templateId":_id},
                        success:function (result) {
                            if(result.success){
                                $.each(result.data,function (i,n) {
                                    var _div=$("<div></div>").addClass("form-inline");
                                    var _input1=$('<input type="text" disabled="disabled" class="form-control " >')
                                        .val(n.name);
                                    var _input2=$('<input type="hidden" name="procedure.id">').val(n.id);
                                    var closeBtn='<button id="removeProcedure" type="button" class="myClose">&nbsp;&times;&nbsp;</button>';
                                    _div.append(_input1).append(_input2).append(closeBtn);
                                    $("#gongxuContent").append(_div);
                                });
                                $("#addModel").modal("show");
                            }else{
                                alert(result.message);
                            }
                        },
                        error:function (a, b, c, d) {
                            alert(b);
                        }
                    })

                }else{
                    alert("请选中一条模板");
                }
            });
            //“添加工序按钮”
            $("#addProcedureBtn").click(function () {
                var _input=$(this).next();
                var procedureName=_input.val();
                var _data={"actionName":"add"};
                _data.procedureName=procedureName;
                $.ajax({
                    url:"/Windchill/servlet/Navigation/procedure",
                    data:_data,
                    type:"get",
                    dataType:"json",
                    success:function (result) {
                        if(result.success){
                            getProcedure();
                            _input.val("");
                        }else{
                            alert(result.message);
                        }
                    },
                    error:function (a, b, c, d) {
                        alert(b);
                    }
                });
            });
            //去除模板中的工序的按钮
            $("#gongxuContent").on("click","#removeProcedure",function () {
                $(this).parent("div").remove();
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
            //模态框暗下去的时候
            $("#addModel").on("hidden.bs.modal",function () {
                $("#gongxuContent").html("");
                $("#modelForm").get(0).reset();
                $("#modelForm").find("input[type=hidden]").val("");
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
            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">

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
            <div class="col-xs-9 col-sm-9 col-md-9  col-lg-8">
                <div class="well">
                    <h3>工序特性指标</h3>
                    <div class="btn-group" >
                        <button class="btn btn-info" type="button" id="addCharBtn" >新增</button>
                        <button class="btn btn-info" type="button" id="modifyCharBtn">修改</button>
                        <button class="btn btn-info" type="button" id="deleCharBtn" >删除</button>
                        <button class="btn btn-info" type="button" id="importCharBtn" >导入工艺化结构工序</button>
                    </div>
                    <table class="table" id="procedureList">
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
                                    <input type="hidden" name="id" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="gongxu" class="col-md-3 control-label">选择工序</label>
                                <div class="col-md-7 form-inline">
                                    <select id="gongxu" class="form-control">
                                        <option>数据获取中……</option>
                                    </select>
                                    <button id="selectProcedureBtn" type="button" class="btn btn-info">选择</button>
                                    <span class="pull-right">
                                        <button type="button" id="addProcedureBtn" class=" btn btn-info">添加工序</button>
                                        <input type="text" name="procedureName" class="pull-right form-control" placeholder="请输入工序名称">
                                    </span>
                                </div>
                            </div>
                            <div class="form-group ">
                                <div id="gongxuContent" class="col-md-6 col-md-offset-3">
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
    <div class="hide" id="log"><ul></ul></div>
</body>
</html>
